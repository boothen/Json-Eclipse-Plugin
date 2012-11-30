/**
 *
 */
package com.boothen.jsonedit.outline;


import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.outline.node.JsonTreeNode;

/**
 * JsonContentOutlinePage manages the outline view of the Json.
 *
 * @author Matt Garner
 *
 */
public class JsonContentOutlinePage extends ContentOutlinePage implements ISelectionListener {

	protected Object fInput;
	protected IDocumentProvider fDocumentProvider;
	protected ITextEditor fTextEditor;
	protected JsonContentProvider fContentProvider;

	public JsonContentOutlinePage(IDocumentProvider provider, ITextEditor editor) {
		super();
		fDocumentProvider= provider;
		fTextEditor= editor;
		fContentProvider = new JsonContentProvider(fDocumentProvider);
	}

	/* (non-Javadoc)
	 * Method declared on ContentOutlinePage
	 */
	@Override
	public void createControl(Composite parent) {

		super.createControl(parent);

		TreeViewer viewer= getTreeViewer();
		viewer.setContentProvider(fContentProvider);
		DelegatingStyledCellLabelProvider delegatingStyledCellLabelProvider = new DelegatingStyledCellLabelProvider(new JsonLabelProvider());
		viewer.setLabelProvider(delegatingStyledCellLabelProvider);
		getSite().getPage().addPostSelectionListener(this);

		if (fInput != null) {
			viewer.setInput(fInput);
			fContentProvider.setInput(fInput);

		}
	}

	/**
	 * Remove the listener associated with the outline.
	 *
	 */
	@Override
	public void dispose() {

		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}

	/**
	 * Selects the text in the editor associated with the item selected in the
	 * outline view tree.
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {

		ISelection selection= event.getSelection();

		if (selection.isEmpty())
			fTextEditor.resetHighlightRange();
		else {
			JsonTreeNode jsonTreeNode = (JsonTreeNode) ((IStructuredSelection) selection).getFirstElement();
			if (jsonTreeNode.isTextSelection()) {
				jsonTreeNode.setTextSelection(false);
				return;
			}

			int start= jsonTreeNode.getStart();
			int length= jsonTreeNode.getLength();
			try {
				fTextEditor.selectAndReveal(start, length);
			} catch (IllegalArgumentException x) {
				fTextEditor.resetHighlightRange();
			}
		}

	}

	/**
	 * Sets the input of the outline page
	 *
	 * @param input the input of this outline page
	 */
	public void setInput(Object input) {
		fInput = input;
		fContentProvider.setInput(fInput);
		update();
	}

	/**
	 * Updates the outline page.
	 */
	public void update() {
		TreeViewer viewer= getTreeViewer();

		if (viewer != null) {
			Control control= viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(fInput);
				control.setRedraw(true);
			}
		}
	}

	/**
	 * Moves the outline view to show the element where the cursor in the
	 * text editor is placed.
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			int start = textSelection.getOffset();
			int length = textSelection.getLength();

			JsonTreeNode element = fContentProvider.findNearestElement(start, length);
			if (element != null) {
				element.setTextSelection(true);
				getTreeViewer().reveal(element);
				TreeSelection treeSelection = new TreeSelection(new TreePath(new Object[]{element}));
				getTreeViewer().setSelection(treeSelection);
			}
		}

	}

	public void setJsonNodes(List<JsonNode> jsonNodes) {
		fContentProvider.setJsonNodes(jsonNodes);
		if (fContentProvider.rootObject == null) {
			update();
		} else {
			TreeViewer viewer= getTreeViewer();

			if (viewer != null) {
				viewer.refresh();
			}
		}
	}
}
