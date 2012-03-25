package org.sourceforge.jsonedit.core.outline.elements;


import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class JsonArray extends JsonElement implements JsonParent {
	
	private static final String iconPath = "/icons/JsonArray.gif";
	
	private String key;
	private List<JsonElement> jsonElements = new LinkedList<JsonElement>();
	
	public JsonArray(JsonParent parent) {
		super(parent);
		
	}
	
	public JsonArray(JsonParent parent, String key) {
		super(parent);
		this.key = key;
	}

	@Override
	public List<JsonElement> getChildren() {
		return jsonElements;
	}

	@Override
	public void removeFromParent() {
		getParent().removeEntry(this);
		
	}
	
	public boolean hasChildren() {
		return !jsonElements.isEmpty();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<JsonElement> getJsonElements() {
		return jsonElements;
	}

	public void setJsonElements(List<JsonElement> jsonElements) {
		this.jsonElements = jsonElements;
	}
	
	public void removeEntry(JsonElement jsonElement) {
		this.jsonElements.remove(jsonElement);
	}
	
	public void addChild(JsonElement jsonElement) {
		this.jsonElements.add(jsonElement);
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
