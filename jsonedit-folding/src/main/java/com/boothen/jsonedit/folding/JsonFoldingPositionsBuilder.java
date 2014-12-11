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
package com.boothen.jsonedit.folding;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.core.model.jsonnode.JsonType;

/**
 * @author Matt Garner
 *
 */
public class JsonFoldingPositionsBuilder {

	private List<JsonNode> jsonNodes;

	public JsonFoldingPositionsBuilder(List<JsonNode> jsonNodes) {
		this.jsonNodes = jsonNodes;
	}

	public List<Position> buildFoldingPositions() {

		List<Position> positions = new LinkedList<Position>();
		List<Position> positionsStack = new LinkedList<Position>();
		if (jsonNodes != null) {
			for (JsonNode jsonNode : jsonNodes) {
				if (isJsonNodeType(jsonNode, JsonType.Array, JsonType.Object)) {
					Position position = new Position(jsonNode.getStart());
					positionsStack.add(0, position);
					positions.add(position);
				}

				if (isJsonNodeType(jsonNode, JsonType.End)) {
					if (positionsStack.size() > 0) {
						Position position = positionsStack.remove(0);
						position.setLength(jsonNode.getEnd() - position.getOffset());
					}
				}
			}
		}
		return positions;
	}

	private boolean isJsonNodeType(JsonNode jsonNode, JsonType ... jsonTypes) {

		for (JsonType jsonType : jsonTypes) {
			if (jsonNode.getJsonType() == jsonType) {
				return true;
			}
		}

		return false;
	}


}
