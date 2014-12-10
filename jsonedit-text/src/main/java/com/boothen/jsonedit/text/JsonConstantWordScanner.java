package com.boothen.jsonedit.text;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.Color;

import com.boothen.jsonedit.coloring.JsonColorProvider;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.detector.JsonWhitespaceDetector;
import com.boothen.jsonedit.text.detector.JsonWordDetector;

public class JsonConstantWordScanner extends RuleBasedScanner implements Reinitable {

	private final JsonColorProvider jsonColorProvider = new JsonColorProvider();
	private final String word;
	
	public JsonConstantWordScanner(String word) {
		this.word = word;
		reinit();
	}

	@Override
	public void reinit() {
		IToken defaultText = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.ERROR_COLOR)));
		IToken nullValue = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.NULL_COLOR)));

		List<IRule> rules= new LinkedList<IRule>();

		WordRule wordRule= new WordRule(new JsonWordDetector(), defaultText);
		wordRule.addWord(word, nullValue);
		rules.add(wordRule);
		rules.add(new WhitespaceRule(new JsonWhitespaceDetector()));

		IRule[] result= new IRule[rules.size()];
		setRules(rules.toArray(result));
	}

	private Color getPreferenceColor(String preferenceValue) {
		return jsonColorProvider.getColor(StringConverter.asRGB(JsonPreferenceStore.getIPreferenceStore().getString(preferenceValue)));
	}
}
