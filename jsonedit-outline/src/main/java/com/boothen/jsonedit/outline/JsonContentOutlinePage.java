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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.model.JsonContextTokenFinder;
import com.boothen.jsonedit.model.ParseTreeInfo;
import com.boothen.jsonedit.model.Segment;

/**
 * Manages the outline view of the JSON editor.
 */
public class JsonContentOutlinePage extends ContentOutlinePage {

    private final IPreferenceStore preferenceStore = JsonCorePlugin.getDefault().getPreferenceStore();

    private final ITextEditor fTextEditor;
    private final ISelectionListener textListener = new MyTextListener();
    private final IDoubleClickListener treeListener = new MyTreeListener();
    private final Container<ParseTree> root = new Container<>();
    private final JsonContentProvider provider = new JsonContentProvider();
    private boolean textHasChanged;
    private IPropertyChangeListener prefStoreListener;

    private Map<ParseTree, Position> positions = Collections.emptyMap();

    /**
     * @param editor the linked text editor
     */
    public JsonContentOutlinePage(ITextEditor editor) {
        fTextEditor = editor;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        JsonLabelProvider labelProvider = new JsonLabelProvider(preferenceStore);

        final TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(provider);
        // wrap in DSCLP to forward the styled text to the tree viewer
        viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(labelProvider));
        viewer.setInput(root);
        viewer.addDoubleClickListener(treeListener);

        fTextEditor.getSite().getPage().addPostSelectionListener(textListener);


        prefStoreListener = new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                // the color settings could have changed -> repaint
                viewer.refresh();
            }
        };

        preferenceStore.addPropertyChangeListener(prefStoreListener);
    }

    /**
     * Remove the listener associated with the outline.
     */
    @Override
    public void dispose() {
        preferenceStore.removePropertyChangeListener(prefStoreListener);
        fTextEditor.getSite().getPage().removePostSelectionListener(textListener);
        super.dispose();
    }

    /**
     * Sets the input of the outline page.
     * The mapping from tree elements to document positions is required, because the positions are modified
     * shortly after typing while the syntax tree update is delayed.
     *
     * @param input the input of this outline page
     * @param oldToNew the mapping from old to new tree elements
     * @param positions maps tree elements to document positions
     */
    public void setInput(JsonContext input, Map<ParseTree, ParseTree> oldToNew, Map<ParseTree, Position> positions) {
        this.positions = positions;
        root.setContent(input);
        update(oldToNew);
    }

    /**
     * Updates the outline page.
     * @param map
     */
    private void update(Map<ParseTree, ParseTree> map) {
        TreeViewer viewer = getTreeViewer();

        // In theory, it should be possible to derive the parent,
        // but this is not trivial and needs to be reliable
        provider.refreshParents(root.getContent());

        if (viewer != null) {
            Tree control = viewer.getTree();
            if (control != null && !control.isDisposed()) {

                Object[] oldExpanded = viewer.getExpandedElements();
                Object[] newExpanded = convertExpandedElements(oldExpanded, map);

                // getStructuredSelection() is not yet available in indigo (3.7)
                IStructuredSelection viewerSelection = (IStructuredSelection) viewer.getSelection();
                Object oldSelected = viewerSelection.getFirstElement();
                Object newSelected = map.get(oldSelected);

                control.setRedraw(false);

                textHasChanged = true;
                viewer.refresh();
                viewer.setExpandedElements(newExpanded);

                if (newSelected != null) {
                    viewer.reveal(newSelected);
                    viewer.setSelection(new TreeSelection(new TreePath(new Object[] { newSelected })));
                }
                textHasChanged = false;

                control.setRedraw(true);
            }
        }
    }

    private static Object[] convertExpandedElements(Object[] oldExpanded, Map<ParseTree, ParseTree> map) {
        List<ParseTree> newExpanded = new ArrayList<>();
        for (Object obj : oldExpanded) {
            ParseTree newObj = map.get(obj);
            if (newObj != null) {
                newExpanded.add(newObj);
            }
        }
        return newExpanded.toArray();
    }

    /**
     * Selects the text in the editor associated with the item selected in the
     * outline view tree.
     */
    private class MyTreeListener implements IDoubleClickListener {

        @Override
        public void doubleClick(DoubleClickEvent event) {
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

            ParseTree json = root.getContent();
            if (json == null) {
                return;
            }

            if (selection instanceof ITextSelection) {
                ITextSelection textSelection = (ITextSelection) selection;
                int start = textSelection.getOffset();
                int length = textSelection.getLength();

                JsonContextTokenFinder finder = new JsonContextTokenFinder(start, start + length, positions);
                ParseTree element = root.getContent().accept(finder);
                // similar code exists in QuickOutlinePopup
                while (element != null && !provider.isKnown(element)) {
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
