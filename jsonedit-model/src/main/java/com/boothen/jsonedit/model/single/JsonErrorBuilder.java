package com.boothen.jsonedit.model.single;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;

public class JsonErrorBuilder implements JsonModelBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(JsonErrorBuilder.class);

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {
		LOG.debug("JsonErrorBuilder");

		return new JsonModel(JsonModelType.Error, new Position(0, 0), new Position(0, 0));
	}
}
