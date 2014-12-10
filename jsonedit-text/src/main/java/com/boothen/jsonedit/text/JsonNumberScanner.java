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
import org.eclipse.swt.graphics.Color;

import com.boothen.jsonedit.coloring.JsonColorProvider;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.detector.JsonWhitespaceDetector;

public class JsonNumberScanner extends RuleBasedScanner implements Reinitable {

	private JsonColorProvider jsonColorProvider = new JsonColorProvider();

	public JsonNumberScanner() {
		super();
		initScanner();
	}

	@Override
	public void reinit() {
		initScanner();
	}
	
	private void initScanner() {
		IToken value = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.VALUE_COLOR)));

		List<IRule> rules= new LinkedList<IRule>();
		rules.add(new JsonNumberRule(value));
		rules.add(new WhitespaceRule(new JsonWhitespaceDetector()));

		IRule[] result= new IRule[rules.size()];
		setRules(rules.toArray(result));
	}

	private Color getPreferenceColor(String preferenceValue) {
		return jsonColorProvider.getColor(StringConverter.asRGB(JsonPreferenceStore.getIPreferenceStore().getString(preferenceValue)));
	}
}
