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

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jface.text.TypedRegion;

import com.boothen.jsonedit.model.entry.JsonEntry;
import com.boothen.jsonedit.type.JsonDocumentType;

public class JsonNodeBuilder {

	public JsonNodeBuilder() {
		super();
	}

	public List<JsonNode> buildJsonNodes(List<JsonEntry> jsonEntryList) {
		List<JsonNode> jsonNodes = new LinkedList<JsonNode>();

		Queue<JsonEntry> jsonEntries = new LinkedList<JsonEntry>(jsonEntryList);
		Deque<JsonEntry> stackEntries = new LinkedList<JsonEntry>();

		JsonEntry jsonEntry = jsonEntries.poll();
		while (jsonEntry != null) {

			JsonNode jsonNode = null;
			JsonNode terminalNode = null;
			if (JsonDocumentType.JSON_ARRAY_OPEN.equals(jsonEntry.getType())) {
				jsonNode = jsonNodeOpen(jsonEntry, stackEntries);
			}

			if (JsonDocumentType.JSON_OBJECT_OPEN.equals(jsonEntry.getType())) {
				jsonNode = jsonNodeOpen(jsonEntry, stackEntries);
			}

			if (JsonDocumentType.JSON_OBJECT_CLOSE.equals(jsonEntry.getType())) {
				jsonNode = jsonNodeClose(jsonEntry, stackEntries);
				if (jsonNode == null) {
					jsonNode = createTerminalNode(jsonEntry);
				} else {
					terminalNode = createTerminalNode(jsonEntry);
				}
			}

			if (JsonDocumentType.JSON_ARRAY_CLOSE.equals(jsonEntry.getType())) {
				jsonNode = jsonNodeClose(jsonEntry, stackEntries);
				if (jsonNode == null) {
					jsonNode = createTerminalNode(jsonEntry);
				} else {
					terminalNode = createTerminalNode(jsonEntry);
				}
			}

			if (JsonDocumentType.JSON_STRING.equals(jsonEntry.getType())) {
				stackEntries.addFirst(jsonEntry);
			}

			if (JsonDocumentType.JSON_NUMBER.equals(jsonEntry.getType())) {
				stackEntries.addFirst(jsonEntry);
			}

			if (JsonDocumentType.JSON_TRUE.equals(jsonEntry.getType())) {
				stackEntries.addFirst(jsonEntry);
			}

			if (JsonDocumentType.JSON_FALSE.equals(jsonEntry.getType())) {
				stackEntries.addFirst(jsonEntry);
			}

			if (JsonDocumentType.JSON_NULL.equals(jsonEntry.getType())) {
				stackEntries.addFirst(jsonEntry);
			}

			if (JsonDocumentType.JSON_COLON.equals(jsonEntry.getType())) {
				stackEntries.addFirst(jsonEntry);
			}

			if (JsonDocumentType.JSON_COMMA.equals(jsonEntry.getType())) {
				jsonNode = jsonComma(jsonEntry, stackEntries);
			}

			if (jsonNode != null) {
				JsonNode anyErrorNodes = anyErrorNodes(stackEntries);
				if (anyErrorNodes != null) {
					jsonNodes.add(anyErrorNodes);
				}
				jsonNodes.add(jsonNode);
				if (terminalNode != null) {
					jsonNodes.add(terminalNode);
				}
			}

			jsonEntry = jsonEntries.poll();
		}

		return jsonNodes;
	}

	private JsonNode anyErrorNodes(Deque<JsonEntry> stackEntries) {
		if (stackEntries.isEmpty()) {
			return null;
		}
		
		JsonEntry value = stackEntries.pollFirst();
		String content = "";
		int length = 0;
		int offset = 0;
		while (value != null) {
			content += value.getContent();
			length += value.getLength();
			offset = value.getOffset();
			value = stackEntries.pollFirst();
		}
		
		
		TypedRegion typedRegion = new TypedRegion(offset, length, JsonDocumentType.JSON_ERROR);
		return new JsonNode(null, new JsonEntry(typedRegion, content));
	}

	private JsonNode createTerminalNode(JsonEntry jsonEntry) {
		return new JsonNode(null, jsonEntry);
	}

	private JsonNode jsonComma(JsonEntry jsonEntry, Deque<JsonEntry> stackEntries) {
		JsonEntry value = stackEntries.pollFirst();
		if (value == null || !JsonDocumentType.VALUE_TYPES.contains(value.getType())) {
			if (value != null) {
				stackEntries.addFirst(value);
			}
			return null;
		}

		return createJsonNodeWithKey(value, stackEntries);
	}

	private JsonNode jsonNodeClose(JsonEntry jsonEntry, Deque<JsonEntry> stackEntries) {
		return jsonComma(jsonEntry, stackEntries);
	}

	private JsonNode jsonNodeOpen(JsonEntry jsonEntry, Deque<JsonEntry> stackEntries) {
		return createJsonNodeWithKey(jsonEntry, stackEntries);
	}

	private JsonNode createJsonNodeWithKey(JsonEntry value, Deque<JsonEntry> stackEntries) {
		JsonEntry colon = stackEntries.pollFirst();
		if (colon == null || !colon.getType().equals(JsonDocumentType.JSON_COLON)) {
			if (colon != null) {
				stackEntries.addFirst(colon);
			}
			return new JsonNode(null, value);
		}

		JsonEntry key = stackEntries.pollFirst();
		if (key == null || !key.getType().equals(JsonDocumentType.JSON_STRING)) {
			stackEntries.addFirst(colon);
			if (key != null) {
				stackEntries.addFirst(key);
			}
			return new JsonNode(null, value);
		}

		return new JsonNode(key, value);
	}
}
