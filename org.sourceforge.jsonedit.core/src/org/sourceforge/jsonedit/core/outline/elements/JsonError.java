/**
 * 
 */
package org.sourceforge.jsonedit.core.outline.elements;

import java.util.List;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

/**
 * @author garner_m
 *
 */
public class JsonError extends JsonElement {
	
	private static final String iconPath = "/icons/JsonError.gif";
	
	private String message;
	/**
	 * @param parent
	 */
	public JsonError(JsonParent parent, String message) {
		super(parent);
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see org.sourceforge.jsonedit.core.core.outline.elements.JsonElement#getChildren()
	 */
	@Override
	public List<JsonElement> getChildren() {
		return NO_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see org.sourceforge.jsonedit.core.core.outline.elements.JsonElement#getForegroundColor()
	 */
	@Override
	public String getForegroundColor() {
		return "RED";
	}

	/* (non-Javadoc)
	 * @see org.sourceforge.jsonedit.core.core.outline.elements.JsonElement#getImage()
	 */
	@Override
	public Image getImage() {
		return this.createMyImage(iconPath);
	}

	/* (non-Javadoc)
	 * @see org.sourceforge.jsonedit.core.core.outline.elements.JsonElement#getStyledString()
	 */
	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString();
		StyledString.Styler style1 = StyledString.createColorRegistryStyler("RED", "WHITE");
		styledString.append(message, style1);
		return styledString;
	}

	/* (non-Javadoc)
	 * @see org.sourceforge.jsonedit.core.core.outline.elements.JsonElement#removeFromParent()
	 */
	@Override
	public void removeFromParent() {
		((JsonObject) getParent()).removeEntry(this);
	}

}
