/**
 *
 */
package com.boothen.jsonedit.core.outline.elements;

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

	@Override
	public List<JsonElement> getChildren() {
		return NO_CHILDREN;
	}

	@Override
	public String getForegroundColor() {
		return "RED";
	}

	@Override
	public Image getImage() {
		return this.createMyImage(iconPath);
	}

	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString();
		StyledString.Styler style1 = StyledString.createColorRegistryStyler("RED", "WHITE");
		styledString.append(message, style1);
		return styledString;
	}

	@Override
	public void removeFromParent() {
		((JsonObject) getParent()).removeEntry(this);
	}

}
