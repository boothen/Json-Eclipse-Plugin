package org.sourceforge.jsonedit.core.outline.elements;

import java.util.List;

public abstract class JsonEntry extends JsonElement {
	
	private String key;
	private String value;
	
	public JsonEntry(JsonParent parent, String key, String value) {
		super(parent);
		this.key = key;
		this.value = value;
	}
	
	public JsonEntry(JsonParent parent, String key) {
		super(parent);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void removeFromParent() {
		((JsonObject) getParent()).removeEntry(this);
	}

	@Override
	public List<JsonElement> getChildren() {
		return NO_CHILDREN;
	}
	
	@Override
	public String toString() {
		return key + " : " + value;
	}
}
