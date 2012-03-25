package org.sourceforge.jsonedit.core.outline.elements;

public interface JsonParent {
	
	public void removeEntry(JsonElement jsonElement);
	
	public boolean hasChildren();
	
	public void addChild(JsonElement jsonElement);
}
