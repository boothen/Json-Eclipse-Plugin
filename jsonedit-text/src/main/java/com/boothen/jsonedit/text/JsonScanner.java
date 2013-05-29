/**
 *
 */
package com.boothen.jsonedit.text;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.Color;

import com.boothen.jsonedit.coloring.JsonColorProvider;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.detector.JsonWhitespaceDetector;
import com.boothen.jsonedit.text.detector.JsonWordDetector;

/**
 * JsonScanner is used to scan the JSON and apply coloring.
 *
 * @author Matt Garner
 *
 */
public class JsonScanner extends RuleBasedScanner {

	private JsonPreferenceStore store;
	private JsonColorProvider jsonColorProvider = new JsonColorProvider();

	public JsonScanner(JsonPreferenceStore store) {
		super();
		this.store = store;
		initScanner();
	}

	public void reinitScanner() {
		initScanner();
	}

	private void initScanner() {
		IToken string = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.STRING_COLOR)));
		IToken value = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.VALUE_COLOR)));
		IToken defaultText = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.DEFAULT_COLOR)));
		IToken nullValue = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceStore.NULL_COLOR)));

		List<IRule> rules= new LinkedList<IRule>();

		rules.add(new MultiLineRule(":\"", "\"", value, '\\'));
		rules.add(new MultiLineRule("\"", "\"", string, '\\')); //$NON-NLS-2$ //$NON-NLS-1$
		WordRule wordRule= new WordRule(new JsonWordDetector(), defaultText);
		wordRule.addWord("null", nullValue);
		rules.add(wordRule);
		rules.add(new WhitespaceRule(new JsonWhitespaceDetector()));

		IRule[] result= new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

	private Color getPreferenceColor(String preferenceValue) {
		return jsonColorProvider.getColor(StringConverter.asRGB(store.getIPreferenceStore().getString(preferenceValue)));
	}
}
