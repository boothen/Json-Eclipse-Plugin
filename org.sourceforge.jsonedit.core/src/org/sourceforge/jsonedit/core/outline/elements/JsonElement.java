package org.sourceforge.jsonedit.core.outline.elements;

import java.util.Collections;
import java.util.List;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.sourceforge.jsonedit.core.JsonEditorPlugin;
import org.sourceforge.jsonedit.core.outline.JsonContentProvider;

public abstract class JsonElement {
	
	static List<JsonElement> NO_CHILDREN = Collections.emptyList();
	
	private JsonParent parent;
	
	private boolean textSelection;
	
	private Position position;
	
	public JsonElement(JsonParent parent) {
		this.parent = parent;
	}
	
	public JsonParent getParent() {
		return parent;
	}
	
	public abstract List<JsonElement> getChildren();
	
	public abstract void removeFromParent();
	
	public abstract Image getImage();
	
	protected Image createMyImage(String urlPath) {
		ImageDescriptor imgDescriptor = null;
		imgDescriptor = JsonEditorPlugin.imageDescriptorFromPlugin(JsonEditorPlugin.getDefault().getBundle().getSymbolicName(), urlPath);
		return imgDescriptor.createImage();
	}
	
	@Deprecated
	public abstract String getForegroundColor();
	
	public abstract StyledString getStyledString();

	public int getStart() {
		if (position != null && !position.isDeleted) {
			return position.getOffset();
		}
		return -1;
	}

	public void setStart(int start, IDocument doc) throws BadLocationException, BadPositionCategoryException {
		position = new Position(start);
		doc.addPosition(JsonContentProvider.JSON_ELEMENTS, position);
	}

	public int getLength() {
		if (position != null && !position.isDeleted) {
			return position.getLength();
		}
		return -1;
	}

	public void setLength(int length) {
		position.setLength(length);
	}
	
	public void setPosition(int start, int length, IDocument doc) throws BadLocationException, BadPositionCategoryException {
		position = new Position(start, length);
		doc.addPosition(JsonContentProvider.JSON_ELEMENTS, position);
	}
	
	/**
	 * Returns true if the JsonElement was selected from a text event.
	 * @return
	 */
	public boolean isTextSelection() {
		return textSelection;
	}
	
	/**
	 * Set to true if the JsonElement is about to fire a notification event.
	 * @param textSelection
	 */
	public void setTextSelection(boolean textSelection) {
		this.textSelection = textSelection;
	}
	
}
