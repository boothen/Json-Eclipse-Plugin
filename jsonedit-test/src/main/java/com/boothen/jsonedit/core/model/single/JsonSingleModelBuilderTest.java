package com.boothen.jsonedit.core.model.single;

import org.eclipse.jface.text.IDocument;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothen.jsonedit.core.util.FileToDocUtility;
import com.boothen.jsonedit.core.util.reader.JsonDocReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.single.JsonSingleModelBuilder;

public class JsonSingleModelBuilderTest {

	private static final Logger LOG = LoggerFactory.getLogger(JsonSingleModelBuilderTest.class);

	private JsonSingleModelBuilder jsonSingleModelBuilder;

	@Test
	public void testObject() throws JsonReaderException {
		test("object.json");
	}

	@Test
	public void testArray() throws JsonReaderException {
		test("array.json");
	}

	@Test
	public void test36() throws JsonReaderException {

		test("test36.json");
	}

	private void test(String file) throws JsonReaderException {
		LOG.debug("Start test: " + file);
		IDocument fDocument = FileToDocUtility.getDocument(file);
		jsonSingleModelBuilder = new JsonSingleModelBuilder();
		jsonSingleModelBuilder.buildModel(new JsonDocReader(fDocument));
	}
}
