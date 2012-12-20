package com.boothen.jsonedit.model.single;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;
import com.boothen.jsonedit.model.JsonObject;

public class JsonObjectBuilder implements JsonModelBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(JsonObjectBuilder.class);

	private static final JsonModelBuilderFactory JSON_MODEL_BUILDER_FACTORY = new JsonModelBuilderFactory();

	public static final char OPEN_OBJECT = '{';
	public static final char CLOSE_OBJECT = '}';


	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {

		// Build node
		// Get Pair
		LOG.debug("JsonObjectBuilder");
		char ch;
		List<JsonModel> pairModels = new LinkedList<JsonModel>();
		boolean success = true;
		int openingOffset = parser.getPosition();
		do {
			ch = parser.getNextClean();
			JsonModelBuilder jsonModelBuilder = JSON_MODEL_BUILDER_FACTORY.getPairModelBuilder(ch);
			if (jsonModelBuilder  != null) {
				JsonModel pairModel = jsonModelBuilder.buildModel(parser);
				pairModels.add(pairModel);
			}

			if (jsonModelBuilder instanceof JsonErrorBuilder) {
				success = false;
				break;
			}

			ch = parser.getCurrent();
		} while (ch == ',');

		if (success && ch != CLOSE_OBJECT) {
			success = false;
			pairModels.add(new JsonModel(JsonModelType.Error, new Position(parser.getPosition(), 0), new Position(openingOffset, parser.getPosition() - openingOffset)));
		}

		if (success) {
			ch = parser.getNextClean();
		}

		return new JsonObject(pairModels, new Position(openingOffset, parser.getPosition() - openingOffset),
				new Position(openingOffset, parser.getPosition() - openingOffset));
	}


}
