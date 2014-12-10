/**
 *
 */
package com.boothen.jsonedit.folding;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.core.model.jsonnode.JsonType;

/**
 * @author garner_m
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
