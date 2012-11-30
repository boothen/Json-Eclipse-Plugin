package com.boothen.jsonedit.model;

import java.util.List;

import org.eclipse.jface.text.Position;

public class JsonObject extends JsonModel {

	private List<JsonModel> jsonPairs;

	public JsonObject(List<JsonModel> jsonPairs, Position tokenPosition, Position completePosition) {
		super(JsonModelType.Object, tokenPosition, completePosition);
		this.jsonPairs = jsonPairs;
	}
}
