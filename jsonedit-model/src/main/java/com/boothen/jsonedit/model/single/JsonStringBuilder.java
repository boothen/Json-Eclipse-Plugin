package com.boothen.jsonedit.model.single;

import static com.boothen.jsonedit.core.util.JsonCharUtility.eof;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;
import com.boothen.jsonedit.model.JsonString;

public class JsonStringBuilder implements JsonModelBuilder {

	public static final char DOUBLE_QUOTE = '"';

	private static final Logger LOG = LoggerFactory.getLogger(JsonStringBuilder.class);

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {

		char ch = eof;
		StringBuilder stringBuilder = new StringBuilder("" + parser.getCurrent());
		do {
			ch = parser.getNextChar();
			stringBuilder.append(ch);
		} while ((ch != '"' && parser.getPrevious() != '\\') && ch != eof);

		if (ch == eof) {
			return new JsonModel(JsonModelType.Error, new Position(0, 0), new Position(0, 0));
		}

		ch = parser.getNextChar();

		LOG.debug("JsonStringBuilder: " + stringBuilder.toString());
		return new JsonString(stringBuilder.toString(), new Position(0, 0), new Position(0, 0));
	}
}
