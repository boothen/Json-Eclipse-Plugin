package org.sourceforge.jsonedit.core.model.jsonnode;

import org.sourceforge.jsonedit.core.model.node.Node;

public class JsonNode {
	
	private Node key;
	private Node value;
	private JsonType jsonType;
	

	public JsonNode(Node key, Node value, JsonType jsonType) {
		super();
		this.key = key;
		this.value = value;
		this.jsonType = jsonType;
	}
	
	public Node getKey() {
		return key;
	}
	public void setKey(Node key) {
		this.key = key;
	}
	public Node getValue() {
		return value;
	}
	public void setValue(Node value) {
		this.value = value;
	}
	public JsonType getJsonType() {
		return jsonType;
	}
	public void setJsonType(JsonType jsonType) {
		this.jsonType = jsonType;
	}
	
	@Override
	public String toString() {
		String toString = jsonType.toString();
		String keyString = (key != null) ? key.getValue() + "," : "";
//		toString += ", " + position.offset + ", " + position.length;
		toString += ", " + keyString + ", " + ((value != null) ? value.getValue() : "");
		return toString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jsonType == null) ? 0 : jsonType.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonNode other = (JsonNode) obj;
		if (jsonType == null) {
			if (other.jsonType != null)
				return false;
		} else if (!jsonType.equals(other.jsonType))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
