/**
 * 
 */
package org.sourceforge.jsonedit.core.outline;

import java.util.List;


import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.sourceforge.jsonedit.core.model.jsonnode.JsonNode;
import org.sourceforge.jsonedit.core.outline.node.JsonTreeNode;

/**
 * JsonContentProvider provides the Tree Structure for the outline view.
 * 
 * @author Matt Garner
 *
 */
public class JsonContentProvider implements ITreeContentProvider {
	
	protected IDocumentProvider fDocumentProvider;
	protected Object fInput;
	protected JsonTreeNode rootObject;
	public final static String JSON_ELEMENTS = "__json_elements"; //$NON-NLS-1$
	protected IPositionUpdater fPositionUpdater= new DefaultPositionUpdater(JSON_ELEMENTS);
	protected List<JsonNode> jsonNodes;
	
	protected void parse(IDocument document) {
		rootObject = new JsonModelOutlineParser().mergeNodes(rootObject, jsonNodes);
	}
	
	public JsonContentProvider(IDocumentProvider documentProvider) {
		super();
		fDocumentProvider = documentProvider;
	}
	
	public void setInput(Object input) {
		this.fInput = input;
	}
	
	public void setJsonNodes(List<JsonNode> jsonNodes) {
		this.jsonNodes = jsonNodes;
		rootObject = new JsonModelOutlineParser().mergeNodes(rootObject, jsonNodes);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement == fInput) {
			return (rootObject != null) ? new Object[]{ rootObject } : new Object[0];
		}
		if (parentElement instanceof JsonTreeNode) {
			return ((JsonTreeNode) parentElement).getChildren().toArray();
		}
		return new Object[0];
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		if (element == rootObject)
			return fInput;
		if (element instanceof JsonTreeNode) {
			return ((JsonTreeNode) element).getParent();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		if (element == fInput) {
			return (rootObject != null) ? true : false;
		}
		if (element instanceof JsonTreeNode) {
			return ((JsonTreeNode) element).hasChildren();
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement == fInput) {
			return (rootObject != null) ? new Object[]{ rootObject } : new Object[0];
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		if (rootObject != null) {
			rootObject = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
		if (oldInput != null) {
			IDocument document= fDocumentProvider.getDocument(oldInput);
			if (document != null) {
				try {
					document.removePositionCategory(JSON_ELEMENTS);
				} catch (BadPositionCategoryException x) {
				}
				document.removePositionUpdater(fPositionUpdater);
			}
		}
		
		rootObject = null;
		
		if (newInput != null) {
			IDocument document = fDocumentProvider.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(JSON_ELEMENTS);
				document.addPositionUpdater(fPositionUpdater);

				parse(document);
			}
		}
	}
	
	/**
	 * Finds the element in the tree that is closest to the required text
	 * position.
	 * 
	 * @param start
	 * @param length
	 * @return
	 */
	public JsonTreeNode findNearestElement(int start, int length) {
		
		if (rootObject == null) {
			return null;
		}
		
		return findNearestElement((JsonTreeNode)rootObject, start, length);
	}
	
	/**
	 * Recursive search to find the nearest element in the tree.
	 * 
	 * @param parent
	 * @param start
	 * @param length
	 * @return
	 */
	private JsonTreeNode findNearestElement(JsonTreeNode parent, int start, int length) {
		
		JsonTreeNode previous = null;
		boolean found = false;
		
		if (parent.getChildren().size() == 0) {
			return parent;
		}
		
		for (JsonTreeNode jsonTreeNode : parent.getChildren()) {
			
			if (start < jsonTreeNode.getStart()) {
				found = true;
				if (previous != null) {
					previous = findNearestElement(previous, start, length);
				} else {
					previous = parent;
				}
				break;
			}
			previous = jsonTreeNode;
		}
		
		if(!found) {
			previous = findNearestElement(previous, start, length);
		}
		
		return previous;
	}
}
