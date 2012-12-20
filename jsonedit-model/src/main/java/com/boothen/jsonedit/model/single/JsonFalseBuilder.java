package com.boothen.jsonedit.model.single;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;

public class JsonFalseBuilder implements JsonModelBuilder {

	public static final char F = 'f';
	public static final char A = 'a';
	public static final char L = 'l';
	public static final char S = 's';
	public static final char E = 'e';

	private static final Logger LOG = LoggerFactory.getLogger(JsonFalseBuilder.class);

	private static final char[] NAME = new char[] {A, L, S, E};

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {
		LOG.debug("JsonFalseBuilder");
		int openingOffset = parser.getPosition();
		for (int i = 0; i < NAME.length; i++) {
			char ch = parser.getNextClean();
			if (ch != NAME[i]) {
				return new JsonModel(JsonModelType.Error, new Position(parser.getPosition(), 0), new Position(openingOffset, parser.getPosition() - openingOffset));
			}
		}

		parser.getNextClean();

		return new JsonModel(JsonModelType.False, new Position(openingOffset, 4), new Position(openingOffset, 4));
	}
}
