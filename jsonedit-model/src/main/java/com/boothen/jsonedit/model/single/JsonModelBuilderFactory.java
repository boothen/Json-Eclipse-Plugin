package com.boothen.jsonedit.model.single;


public class JsonModelBuilderFactory {

	public JsonModelBuilder getContainerModelBuilder(char ch) {
		JsonModelBuilder jsonModelBuilder = getObjectModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getArrayModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		return new JsonErrorBuilder();
	}

	public JsonModelBuilder getPairModelBuilder(char ch) {
		if (ch == JsonObjectBuilder.CLOSE_OBJECT) {
			return null;
		}

		if (ch == JsonPairBuilder.DOUBLE_QUOTE) {
			return new JsonPairBuilder();
		}

		return new JsonErrorBuilder();
	}

	public JsonModelBuilder getValueModelBuilder(char ch) {
		JsonModelBuilder jsonModelBuilder = getStringModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getObjectModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getArrayModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getTrueModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getFalseModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getNumberModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		jsonModelBuilder = getNullModelBuilder(ch);
		if (jsonModelBuilder != null) {
			return jsonModelBuilder;
		}

		return new JsonErrorBuilder();
	}

	private JsonModelBuilder getTrueModelBuilder(char ch) {
		if (ch == JsonTrueBuilder.T) {
			return new JsonTrueBuilder();
		}
		return null;
	}

	private JsonModelBuilder getFalseModelBuilder(char ch) {
		if (ch == JsonFalseBuilder.F) {
			return new JsonFalseBuilder();
		}
		return null;
	}

	private JsonModelBuilder getNullModelBuilder(char ch) {
		if (ch == JsonNullBuilder.N) {
			return new JsonNullBuilder();
		}
		return null;
	}

	private JsonModelBuilder getNumberModelBuilder(char ch) {
		if (JsonNumberBuilder.isStartOfNumber(ch)) {
			return new JsonNumberBuilder();
		}
		return null;
	}

	private JsonModelBuilder getObjectModelBuilder(char ch) {
		if (ch == JsonObjectBuilder.OPEN_OBJECT) {
			return new JsonObjectBuilder();
		}
		return null;
	}

	private JsonModelBuilder getArrayModelBuilder(char ch) {
		if (ch == JsonArrayBuilder.OPEN_ARRAY) {
			return new JsonArrayBuilder();
		}
		return null;
	}

	private JsonModelBuilder getStringModelBuilder(char ch) {
		if (ch == JsonStringBuilder.DOUBLE_QUOTE) {
			return new JsonStringBuilder();
		}
		return null;
	}


}
