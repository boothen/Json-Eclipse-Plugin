/*******************************************************************************
 * Copyright 2014 Boothen Technology Ltd.
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://eclipse.org/org/documents/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.boothen.jsonedit.editor.model;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.statushandlers.StatusManager;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.editor.Activator;
import com.boothen.jsonedit.editor.JsonTextEditor;
import com.boothen.jsonedit.folding.JsonFoldingPositionsBuilder;
import com.boothen.jsonedit.model.AntlrAdapter;
import com.boothen.jsonedit.model.ParseProblem;
import com.boothen.jsonedit.model.ParseResult;
import com.boothen.jsonedit.problems.DuplicateKeyFinder;
import com.boothen.jsonedit.problems.StringProblemFinder;
import com.boothen.jsonedit.text.PositionVisitor;

/**
 * Re-compute the JSON syntax tree and update the JSON text editor based on the results.
 * This includes folding structure, markers and the syntax error list.
 */
public class JsonReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {

    private JsonTextEditor textEditor;

    private IDocument fDocument;

    private IProgressMonitor monitor;

    private JsonFoldingPositionsBuilder foldingPositionsBuilder = new JsonFoldingPositionsBuilder();

    private ParseTreeComparator treeComparator = new ParseTreeComparator();

    /**
     * @param textEditor the JSON text editor to work on
     */
    public JsonReconcilingStrategy(JsonTextEditor textEditor) {
        this.textEditor = textEditor;
    }

    @Override
    public void setDocument(IDocument document) {
        this.fDocument = document;
    }

    @Override
    public void setProgressMonitor(IProgressMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void initialReconcile() {
        reconcileSafely(0, fDocument.getLength());
    }

    @Override
    public void reconcile(IRegion partition) {
        reconcileSafely(partition.getOffset(), partition.getLength());
    }

    @Override
    public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
        reconcileSafely(subRegion.getOffset(), subRegion.getLength());
    }

    private void reconcileSafely(int offset, int length) {
        try {
            monitor.beginTask("Updating syntax tree", IProgressMonitor.UNKNOWN);
            reconcile(offset, length);
        } catch (Exception e) {
            String message = e.toString();
            Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, e);
            StatusManager.getManager().handle(status);
        } finally {
            monitor.done();
        }
    }

    private void reconcile(int offset, int length) throws IOException {
        Reader reader = new StringReader(fDocument.get());
        final ParseResult result = AntlrAdapter.convert(reader);
        final JsonContext syntaxTree = result.getTree();
        final List<ParseProblem> problems = new ArrayList<>();
        problems.addAll(result.getLexerErrors());
        problems.addAll(result.getParserErrors());

        final LinkedHashMap<ParseTree, Position> positions = syntaxTree.accept(new PositionVisitor());

        problems.addAll(addCustomProblems(syntaxTree));

        final Map<ParseTree, ParseTree> oldToNew = treeComparator.update(syntaxTree, positions);

        final List<Position> foldPositions = foldingPositionsBuilder.getFoldingPositions(syntaxTree);

        if (textEditor != null) {
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    textEditor.updateDocumentPositions(positions.values());
                    textEditor.updateFoldingStructure(foldPositions);
                    textEditor.updateSyntaxTree(syntaxTree, oldToNew, positions);
                    textEditor.updateProblemMarkers(problems);
                }
            });
        }
    }

    private Collection<ParseProblem> addCustomProblems(JsonContext syntaxTree) {
        Collection<ParseProblem> problems = new ArrayList<>();

        // add duplicate keys
        syntaxTree.accept(new DuplicateKeyFinder(problems));

        // add unneeded escaping warnings
        syntaxTree.accept(new StringProblemFinder(problems));

        return problems;
    }

}
