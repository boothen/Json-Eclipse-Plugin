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
import java.util.List;

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

import com.boothen.jsonedit.editor.Activator;
import com.boothen.jsonedit.editor.JsonTextEditor;
import com.boothen.jsonedit.folding.JsonFoldingPositionsBuilder;
import com.boothen.jsonedit.model.AntlrAdapter;
import com.boothen.jsonedit.model.AntlrAdapter.ParseResult;

public class JsonReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {

    private JsonTextEditor textEditor;

    private IDocument fDocument;

    @Override
    public void reconcile(IRegion partition) {
        initialReconcile();
    }

    @Override
    public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
        initialReconcile();
    }

    @Override
    public void setDocument(IDocument document) {
        this.fDocument = document;

    }

    @Override
    public void initialReconcile() {
        try {
//            fDocument.removePositionCategory(JsonEntryBuilder.JSON_ELEMENTS);
//            fDocument.addPositionCategory(JsonEntryBuilder.JSON_ELEMENTS);
            scan(0, fDocument.getLength());
        } catch (Exception e) {
            StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage()));
        }
    }

    @Override
    public void setProgressMonitor(IProgressMonitor monitor) {

    }

    private void scan(int offset, int length) throws IOException {
        final ParseResult result = AntlrAdapter.convert(fDocument);

//        fDocument.addPosition(JsonEntryBuilder.JSON_ELEMENTS, new TypedPosition(jsonPartitionScanner.getTokenOffset(), jsonPartitionScanner.getTokenLength(), (String) nextToken.getData()));

        final List<Position> fPositions = new JsonFoldingPositionsBuilder(result.getTree()).buildFoldingPositions();

        if (textEditor != null) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    textEditor.updateFoldingStructure(fPositions);
                    textEditor.updateContentOutlinePage(result.getTree());
                }

            });
        }
    }

    public void setTextEditor(JsonTextEditor textEditor) {
        this.textEditor = textEditor;
    }
}
