package com.boothen.jsonedit.model.single;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;

public class JsonSingleModelBuilder implements JsonModelBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(JsonSingleModelBuilder.class);

	private JsonModelBuilderFactory jsonModelBuilderFactory = new JsonModelBuilderFactory();

	public JsonSingleModelBuilder() {

	}

	private JsonModelBuilder getModel(char ch) {
		return jsonModelBuilderFactory.getContainerModelBuilder(ch);
	}

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {
		LOG.debug("JsonSingleModelBuilder");
		char ch = parser.getNextClean();
		return getModel(ch).buildModel(parser);
	}
}
