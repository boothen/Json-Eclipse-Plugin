package com.boothen.jsonedit.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordPatternRule;

public class JsonPartitionScanner extends RuleBasedPartitionScanner {

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

	public JsonPartitionScanner() {

		IToken jsonObjectOpen = new Token(JSON_OBJECT_OPEN);
		IToken jsonObjectClose = new Token(JSON_OBJECT_CLOSE);
		
		IToken jsonArrayOpen = new Token(JSON_ARRAY_OPEN);
		IToken jsonArrayClose = new Token(JSON_ARRAY_CLOSE);
		
		IToken jsonColon = new Token(JSON_COLON);
		IToken jsonComma = new Token(JSON_COMMA);
		
		IToken jsonString = new Token(JSON_STRING);
		IToken jsonNumber = new Token(JSON_NUMBER);
		
		IToken jsonTrue = new Token(JSON_TRUE);
		IToken jsonFalse = new Token(JSON_FALSE);
		IToken jsonNull = new Token(JSON_NULL);

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