package com.boothen.jsonedit.model.single;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;

public class JsonTrueBuilder implements JsonModelBuilder {

	public static final char T = 't';
	public static final char R = 'r';
	public static final char U = 'u';
	public static final char E = 'e';

	private static final Logger LOG = LoggerFactory.getLogger(JsonTrueBuilder.class);

	private static final char[] NAME = new char[] {R, U, E};

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {
		LOG.debug("JsonTrueBuilder");

		int openingOffset = parser.getPosition();
		for (int i = 0; i < NAME.length; i++) {
			char ch = parser.getNextClean();
			if (ch != NAME[i]) {
				return new JsonModel(JsonModelType.Error, new Position(parser.getPosition(), 0), new Position(openingOffset, parser.getPosition() - openingOffset));
			}
		}

		parser.getNextClean();

		return new JsonModel(JsonModelType.True, new Position(openingOffset, parser.getPosition() - openingOffset),
				new Position(openingOffset, parser.getPosition() - openingOffset));
	}

}
