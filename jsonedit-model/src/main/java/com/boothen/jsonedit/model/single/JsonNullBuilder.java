package com.boothen.jsonedit.model.single;

import org.eclipse.jface.text.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;
import com.boothen.jsonedit.model.JsonModelType;

public class JsonNullBuilder implements JsonModelBuilder {

	public static final char N = 'n';
	public static final char U = 'u';
	public static final char L = 'l';

	private static final Logger LOG = LoggerFactory.getLogger(JsonNullBuilder.class);

	private static final char[] NAME = new char[] {U, L, L};

	@Override
	public JsonModel buildModel(JsonReader parser) throws JsonReaderException {
		LOG.debug("JsonNullBuilder");

		for (int i = 0; i < NAME.length; i++) {
			char ch = parser.getNextClean();
			if (ch != NAME[i]) {
				return new JsonModel(JsonModelType.Error, new Position(0, 0), new Position(0, 0));
			}
		}

		parser.getNextClean();

		return new JsonModel(JsonModelType.Null, new Position(0, 0), new Position(0, 0));
	}

}
