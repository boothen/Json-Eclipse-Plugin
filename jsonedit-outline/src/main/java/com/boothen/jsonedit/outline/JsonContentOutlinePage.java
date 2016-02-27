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
/**
 *
 */
package com.boothen.jsonedit.outline;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.model.ParseTreeInfo;
import com.boothen.jsonedit.model.Segment;

/**
 * JsonContentOutlinePage manages the outline view of the Json.
 *
 * @author Matt Garner
 *
 */
public class JsonContentOutlinePage extends ContentOutlinePage {

    private JsonContext fInput;
    private TreeFlattener treeFlattener;
    private ITextEditor fTextEditor;
    private ISelectionListener textListener = new MyTextListener();
    private ISelectionChangedListener treeListener = new MyTreeListener();
    private Set<Object> treeElements = new HashSet<>();
    private boolean textHasChanged;

    public JsonContentOutlinePage(ITextEditor editor) {
        fTextEditor = editor;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        TreeViewer viewer = getTreeViewer();
        JsonContentProvider provider = new JsonContentProvider();
        viewer.setContentProvider(provider);
        viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new JsonLabelProvider()));
        viewer.setInput(fInput);

        treeFlattener = new TreeFlattener(provider);

        // TODO: is it equal to fTextEditor.getSite().getPage() ?
        getSite().getPage().addPostSelectionListener(textListener);
        addSelectionChangedListener(treeListener);
    }

    /**
     * Remove the listener associated with the outline.
     */
    @Override
    public void dispose() {
        getSite().getPage().removePostSelectionListener(textListener);
        removeSelectionChangedListener(treeListener);
        super.dispose();
    }

    /**
     * Sets the input of the outline page
     *
     * @param input the input of this outline page
     */
    public void setInput(JsonContext input) {
        fInput = input;
        update();
    }

    /**
     * Updates the outline page.
     */
    public void update() {
        TreeViewer viewer = getTreeViewer();

        if (viewer != null) {
            Control control = viewer.getControl();
            if (control != null && !control.isDisposed()) {
                control.setRedraw(false);
                viewer.setInput(fInput);
                treeElements.clear();
                List<Object> elements = treeFlattener.flatten(fInput);
                treeElements.addAll(elements);
                viewer.expandToLevel(2);
                control.setRedraw(true);
            }
        }
    }

    /**
     * Selects the text in the editor associated with the item selected in the
     * outline view tree.
     */
    private class MyTreeListener implements ISelectionChangedListener {

        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            if (textHasChanged) {
                // this method is called from MyTextListener#selectionChanged
                // avoid getting caught in an infinite loop triggering text/tree selection
                return;
            }

            ISelection selection = event.getSelection();
            if (selection.isEmpty())
                fTextEditor.resetHighlightRange();
            else {
                ParseTree treeNode = (ParseTree) ((IStructuredSelection) selection).getFirstElement();

                try {
                    Segment segment = ParseTreeInfo.getSegment(treeNode);
                    if (segment != null) {
                        fTextEditor.selectAndReveal(segment.getStart(), segment.getLength());
                    }
                } catch (IllegalArgumentException x) {
                    fTextEditor.resetHighlightRange();
                }
            }
        }

    }

    /**
     * Moves the outline view to show the element where the cursor in the
     * text editor is placed.
     */
    private class MyTextListener implements ISelectionListener {

        @Override
        public void selectionChanged(IWorkbenchPart part, ISelection selection) {

            if (fInput == null) {
                return;
            }

            // TODO: avoid getting caught in an infinite loop
            // (text selection change <-> tree selection change)

            if (selection instanceof ITextSelection) {
                ITextSelection textSelection = (ITextSelection) selection;
                int start = textSelection.getOffset();
                int length = textSelection.getLength();

                ParseTree element = fInput.accept(new JsonContextTokenFinder(start, start + length));
                while (element != null && !treeElements.contains(element)) {
                    element = element.getParent();
                }
                if (element != null) {
                    textHasChanged = true;
                    getTreeViewer().reveal(element);
                    getTreeViewer().setSelection(new TreeSelection(new TreePath(new Object[] { element })));
                    textHasChanged = false;
                }
            }

        }
    }
}
