package com.boothen.jsonedit.model.single;

import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;
import com.boothen.jsonedit.model.JsonModel;

public interface JsonModelBuilder {

	JsonModel buildModel(JsonReader parser) throws JsonReaderException;

}
