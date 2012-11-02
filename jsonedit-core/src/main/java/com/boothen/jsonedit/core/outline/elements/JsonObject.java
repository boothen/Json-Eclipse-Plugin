package com.boothen.jsonedit.core.outline.elements;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class JsonObject extends JsonElement implements JsonParent {

	private String key;

	private static final String iconPath = "/icons/JsonObject.gif";

	private List<JsonElement> values = new LinkedList<JsonElement>();

	public JsonObject(JsonParent parent) {
		super(parent);

	}

	public JsonObject(JsonParent parent, String key) {
		super(parent);
		this.key = key;
	}

	@Override
	public List<JsonElement> getChildren() {
		return values;
	}

	@Override
	public void removeFromParent() {
		if (getParent() != null) {
			getParent().removeEntry(this);
		}
	}

	public void removeEntry(JsonElement jsonElement) {
		this.values.remove(jsonElement);
	}

	public boolean hasChildren() {
		return !values.isEmpty();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<JsonElement> getValues() {
		return values;
	}

	public void setValues(List<JsonElement> values) {
		this.values = values;
	}

	public void addChild(JsonElement jsonElement) {
		values.add(jsonElement);
	}

	@Override
	public String toString() {
		return key;
	}

	@Override
	public Image getImage() {
		return this.createMyImage(iconPath);
	}

	@Override
	public String getForegroundColor() {
		return null;
	}

	@Override
	public StyledString getStyledString() {
		StyledString styledString = new StyledString();
		StyledString.Styler style1 = StyledString.createColorRegistryStyler("GREEN", "WHITE");
		styledString.append(key, style1);
		return styledString;
	}
}
