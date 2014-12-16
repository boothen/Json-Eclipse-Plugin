/*******************************************************************************
 * Copyright 2014 Boothen Technology Ltd.
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   
 * https://eclipse.org/org/documents/epl-v10.html
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.boothen.jsonedit.type;

import java.util.HashSet;
import java.util.Set;

public final class JsonDocumentType {

	public final static String JSON_OBJECT_OPEN = "__json_object_open";
	public final static String JSON_OBJECT_CLOSE = "__json_object_close";
	
	public final static String JSON_ARRAY_OPEN = "__json_array_open";
	public final static String JSON_ARRAY_CLOSE = "__json_array_close";
	
	public final static String JSON_COLON = "__json_colon";
	public final static String JSON_COMMA = "__json_comma";
	
	public final static String JSON_STRING = "__json_string";
	public final static String JSON_NUMBER = "__json_number";
	
	public final static String JSON_TRUE = "__json_true";
	public final static String JSON_FALSE = "__json_false";
	public final static String JSON_NULL = "__json_null";
	
	public final static String JSON_ERROR = "__json_error";
	
	public final static Set<String> DOCUMENT_TYPES = new HashSet<String>();
	
	public final static Set<String> VALUE_TYPES = new HashSet<String>();
	
	static {
		DOCUMENT_TYPES.add(JSON_OBJECT_OPEN);
		DOCUMENT_TYPES.add(JSON_OBJECT_CLOSE);
		DOCUMENT_TYPES.add(JSON_ARRAY_OPEN);
		DOCUMENT_TYPES.add(JSON_ARRAY_CLOSE);
		DOCUMENT_TYPES.add(JSON_COLON);
		DOCUMENT_TYPES.add(JSON_COMMA);
		DOCUMENT_TYPES.add(JSON_STRING);
		DOCUMENT_TYPES.add(JSON_NUMBER);
		DOCUMENT_TYPES.add(JSON_TRUE);
		DOCUMENT_TYPES.add(JSON_FALSE);
		DOCUMENT_TYPES.add(JSON_NULL);
		
		VALUE_TYPES.add(JSON_STRING);
		VALUE_TYPES.add(JSON_NUMBER);
		VALUE_TYPES.add(JSON_TRUE);
		VALUE_TYPES.add(JSON_FALSE);
		VALUE_TYPES.add(JSON_NULL);
	}
}
