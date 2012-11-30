package com.boothen.jsonedit.model;

import org.eclipse.jface.text.Position;

public class JsonModel {

	private final JsonModelType jsonModelType;

	private final Position tokenPosition;

	private final Position completePosition;

	public JsonModel(JsonModelType jsonModelType, Position tokenPosition, Position completePosition) {
		super();
		this.jsonModelType = jsonModelType;
		this.tokenPosition = tokenPosition;
		this.completePosition = completePosition;
	}

	public JsonModelType getJsonModelType() {
		return jsonModelType;
	}

	@Override
	public String toString() {
		return jsonModelType.toString();
	}

	public Position getTokenPosition() {
		return tokenPosition;
	}

	public Position getCompletePosition() {
		return completePosition;
	}
}
