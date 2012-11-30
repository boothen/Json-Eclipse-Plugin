package com.boothen.jsonedit.model.single;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonArray;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;

public class JsonArrayBuilder implements JsonModelBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(JsonArrayBuilder.class);

	private static final JsonModelBuilderFactory JSON_MODEL_BUILDER_FACTORY = new JsonModelBuilderFactory();

	public static final char OPEN_ARRAY = '[';
	public static final char CLOSE_ARRAY = ']';

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {
		LOG.debug("JsonArrayBuilder");

		char ch;
		List<JsonModel> valueModels = new LinkedList<JsonModel>();
		boolean success = true;
		do {
			ch = parser.getNextClean();

			JsonModelBuilder jsonModelBuilder = JSON_MODEL_BUILDER_FACTORY.getValueModelBuilder(ch);
			if (jsonModelBuilder  != null) {
				JsonModel valueModel = jsonModelBuilder.buildModel(parser);
				valueModels.add(valueModel);
			}

			if (jsonModelBuilder instanceof JsonErrorBuilder) {
				success = false;
				break;
			}

			ch = parser.getCurrent();
		} while (ch == ',');

		if (success && ch != CLOSE_ARRAY) {
			success = false;
			valueModels.add(new JsonModel(JsonModelType.Error, new Position(0, 0), new Position(0, 0)));
		}

		if (success) {
			ch = parser.getNextClean();
		}

		return new JsonArray(valueModels, new Position(0, 0), new Position(0, 0));
	}
}
