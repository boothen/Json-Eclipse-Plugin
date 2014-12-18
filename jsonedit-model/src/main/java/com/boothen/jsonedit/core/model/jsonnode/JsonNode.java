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
package com.boothen.jsonedit.core.model.jsonnode;

import com.boothen.jsonedit.model.entry.JsonEntry;


public class JsonNode {

	private JsonEntry key;
	private JsonEntry value;

	public JsonNode(JsonEntry key, JsonEntry value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		if (key == null) {
			return null;
		}
		return key.getContent();
	}
	

	public String getValue() {
		return value.getContent();
	}

	public String getJsonType() {
		return value.getType();
	}
	
	
	public int getStart() {
		JsonEntry startEntry = key;
		if (startEntry == null) {
			startEntry = value;
		}

		return startEntry.getOffset();
	}

	public int getEnd() {
		return value.getOffset() + value.getLength();
	}
	
	public boolean containsLocation(int offset) {
	    return offset >= getStart() && offset <= getEnd();
	}
	

	@Override
	public String toString() {
		String toString = getJsonType();
		String keyString = (key != null) ? key.getContent() + "," : "";
//		toString += ", " + position.offset + ", " + position.length;
		toString += ", " + keyString + ", " + ((value != null) ? value.getContent() : "");
		return toString;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
