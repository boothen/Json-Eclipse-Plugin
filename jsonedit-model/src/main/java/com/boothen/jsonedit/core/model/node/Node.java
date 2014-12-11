/*******************************************************************************
 * Copyright 2014 Boothen Technology Ltd.
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   
 * https://eclipse.org/org/documents/epl-v10.html
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.boothen.jsonedit.core.model.node;


import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;

public class Node {

	private Type type;

	private Position position;

	private String value;

	private JsonNode owner;

	public Node(Type type) {
		super();
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public Position getPosition() {
		return position;
	}

	public int getStart() {
		if (position != null && !position.isDeleted) {
			return position.getOffset();
		}
		return -1;
	}

	public void setStart(int start) {
		position = new Position(start);
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

	public void setPosition(int start, int length) {
		position = new Position(start, length);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value != null) {
			value.replaceAll("\\\"", "\"");
			this.value = value;
		}
	}

	public int getEnd() {

		if (position != null && !position.isDeleted) {
			return position.getOffset() + position.getLength();
		}
		return -1;
	}

	public JsonNode getOwner() {
		return owner;
	}

	public void setOwner(JsonNode owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		String toString = type.toString();
		toString += ", " + position.offset + ", " + position.length;
		toString += ", " + value;
		return toString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Node other = (Node) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
