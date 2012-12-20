package com.boothen.jsonedit.model.single;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonNumber;

public class JsonNumberBuilder implements JsonModelBuilder {

	public static final char MINUS = '-';
	public static final char E = 'E';
	public static final char e = 'e';
	public static final char PLUS = '+';
	public static final char POINT = '.';

	private static final Logger LOG = LoggerFactory.getLogger(JsonNumberBuilder.class);

	@Override
	public JsonNumber buildModel(JsonReader parser) throws JsonReaderException {

		int openingOffset = parser.getPosition();
		StringBuilder stringBuilder = new StringBuilder("" + parser.getCurrent());
		stringBuilder.append(doDigit(parser));
		char current = parser.getCurrent();
		if (current == POINT) {
			stringBuilder.append(current);
			stringBuilder.append(doFrac(parser));
		}
		current = parser.getCurrent();
		if (current == E || current == e) {
			stringBuilder.append(current);
			stringBuilder.append(doExp(parser));
		}
		LOG.debug("JsonNumberBuilder: " + stringBuilder.toString());
		return new JsonNumber(stringBuilder.toString(), new Position(openingOffset, parser.getPosition() - openingOffset),
				new Position(openingOffset, parser.getPosition() - openingOffset));
	}

	private StringBuilder doExp(JsonReader parser) throws JsonReaderException {
		char ch = parser.getNextChar();
		StringBuilder stringBuilder = new StringBuilder("" + ch);
		stringBuilder.append(doDigit(parser));
		return stringBuilder;
	}

	private StringBuilder doFrac(JsonReader parser) throws JsonReaderException {
		return doDigit(parser);
	}

	private StringBuilder doDigit(JsonReader parser) throws JsonReaderException {

		char ch = parser.getNextChar();
		StringBuilder stringBuilder = new StringBuilder(ch);
		while (Character.isDigit(ch)) {
			stringBuilder.append(ch);
			ch = parser.getNextChar();
		}

		return stringBuilder;
	}

	public static boolean isStartOfNumber(char ch) {
		if (ch == MINUS || Character.isDigit(ch)) {
			return true;
		}
		return false;
	}

}
