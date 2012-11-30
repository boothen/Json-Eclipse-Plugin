package com.boothen.jsonedit.model;

import org.eclipse.jface.text.Position;

public class JsonNumber extends JsonModel {

	private String number;

	public JsonNumber(String number, Position tokenPosition, Position completePosition) {
		super(JsonModelType.Number, tokenPosition, completePosition);
		this.number = number;
	}

	@Override
	public String toString() {
		return number;
	}

}
