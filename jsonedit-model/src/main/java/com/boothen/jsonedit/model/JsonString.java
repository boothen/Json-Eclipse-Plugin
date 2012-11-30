package com.boothen.jsonedit.model;

import org.eclipse.jface.text.Position;

public class JsonString extends JsonModel {

	private String value;

	public JsonString(String value, Position tokenPosition, Position completePosition) {
		super(JsonModelType.String, tokenPosition, completePosition);
	}

	@Override
	public String toString() {
		return value;
	}


}
