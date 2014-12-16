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
package com.boothen.jsonedit.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordPatternRule;

import com.boothen.jsonedit.type.JsonDocumentType;

public class JsonPartitionScanner extends RuleBasedPartitionScanner {

	public JsonPartitionScanner() {

		IToken jsonObjectOpen = new Token(JsonDocumentType.JSON_OBJECT_OPEN);
		IToken jsonObjectClose = new Token(JsonDocumentType.JSON_OBJECT_CLOSE);
		
		IToken jsonArrayOpen = new Token(JsonDocumentType.JSON_ARRAY_OPEN);
		IToken jsonArrayClose = new Token(JsonDocumentType.JSON_ARRAY_CLOSE);
		
		IToken jsonColon = new Token(JsonDocumentType.JSON_COLON);
		IToken jsonComma = new Token(JsonDocumentType.JSON_COMMA);
		
		IToken jsonString = new Token(JsonDocumentType.JSON_STRING);
		IToken jsonNumber = new Token(JsonDocumentType.JSON_NUMBER);
		
		IToken jsonTrue = new Token(JsonDocumentType.JSON_TRUE);
		IToken jsonFalse = new Token(JsonDocumentType.JSON_FALSE);
		IToken jsonNull = new Token(JsonDocumentType.JSON_NULL);

		List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

		rules.add(new MultiLineRule("\"", "\"", jsonString));
		rules.add(new JsonNumberRule(jsonNumber));
		rules.add(new WordPatternRule(new JsonConstantWordDetector("true"), "t", "e", jsonTrue));
		rules.add(new WordPatternRule(new JsonConstantWordDetector("false"),"f", "e", jsonFalse));
		rules.add(new WordPatternRule(new JsonConstantWordDetector("null"),"n", "l", jsonNull));
		rules.add(new SingleCharacterRule('{', jsonObjectOpen));
		rules.add(new SingleCharacterRule('}', jsonObjectClose));
		rules.add(new SingleCharacterRule('[', jsonArrayOpen));
		rules.add(new SingleCharacterRule(']', jsonArrayClose));
		rules.add(new SingleCharacterRule(':', jsonColon));
		rules.add(new SingleCharacterRule(',', jsonComma));
		
		setPredicateRules(rules.toArray(new IPredicateRule[0]));
	}
}