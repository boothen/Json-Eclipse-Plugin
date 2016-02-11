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
/**
 *
 */
package com.boothen.jsonedit.text;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
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

import com.boothen.jsonedit.core.JsonColorProvider;
import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.preferences.JsonPreferenceInitializer;
import com.boothen.jsonedit.text.detector.JsonWhitespaceDetector;
import com.boothen.jsonedit.text.detector.JsonWordDetector;

/**
 * JsonScanner is used to scan the JSON and apply coloring.
 *
 * @author Matt Garner
 *
 */
public class JsonStringScanner extends RuleBasedScanner implements Reinitable {

    public JsonStringScanner() {
        super();
        initScanner();
    }

    @Override
    public void reinit() {
        initScanner();
    }

    private void initScanner() {
        IToken string = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceInitializer.STRING_COLOR)));
        IToken value = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceInitializer.VALUE_COLOR)));
        IToken defaultText = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceInitializer.ERROR_COLOR)));
        IToken nullValue = new Token(new TextAttribute(getPreferenceColor(JsonPreferenceInitializer.NULL_COLOR)));

        List<IRule> rules= new LinkedList<IRule>();

        rules.add(new MultiLineRule(":\"", "\"", value, '\\'));
        rules.add(new MultiLineRule("\"", "\"", string, '\\')); //$NON-NLS-2$ //$NON-NLS-1$
        rules.add(new JsonNumberRule(value));

        WordRule wordRule= new WordRule(new JsonWordDetector(), defaultText);
        wordRule.addWord("true", nullValue);
        wordRule.addWord("false", nullValue);
        wordRule.addWord("null", nullValue);

        rules.add(wordRule);
        rules.add(new WhitespaceRule(new JsonWhitespaceDetector()));

        IRule[] result= new IRule[rules.size()];
        setRules(rules.toArray(result));
    }

    private Color getPreferenceColor(String preferenceValue) {
        IPreferenceStore store = JsonEditorPlugin.getDefault().getPreferenceStore();
        return JsonEditorPlugin.getColorProvider().getColor(StringConverter.asRGB(store.getString(preferenceValue)));
    }
}
