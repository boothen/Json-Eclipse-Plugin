package com.boothen.jsonedit.model;

import org.eclipse.jface.text.Position;

public class JsonPair extends JsonModel {

	private JsonModel jsonString;
	private JsonModel jsonValue;

	public JsonPair(JsonModel jsonString, JsonModel jsonValue, Position tokenPosition, Position completePosition) {
		super(JsonModelType.Pair, tokenPosition, completePosition);
		this.jsonString = jsonString;
		this.jsonValue = jsonValue;
	}

	@Override
	public String toString() {
		return jsonString.toString() + " : " + jsonValue.toString();
	}
}
