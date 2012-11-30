package com.boothen.jsonedit.model;

import java.util.List;

import org.eclipse.jface.text.Position;

public class JsonArray extends JsonModel {

	private List<JsonModel> jsonModels;

	public JsonArray(List<JsonModel> jsonModels, Position tokenPosition, Position completePosition) {
		super(JsonModelType.Array, tokenPosition, completePosition);
		this.jsonModels = jsonModels;
	}
}
