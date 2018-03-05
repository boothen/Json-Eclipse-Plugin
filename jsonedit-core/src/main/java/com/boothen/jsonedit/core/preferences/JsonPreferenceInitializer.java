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
package com.boothen.jsonedit.core.preferences;

import static com.boothen.jsonedit.core.preferences.JsonPreferences.AUTO_FORMAT_ON_SAVE;
import static com.boothen.jsonedit.core.preferences.JsonPreferences.EDITOR_MATCHING_BRACKETS;
import static com.boothen.jsonedit.core.preferences.JsonPreferences.EDITOR_MATCHING_BRACKETS_COLOR;
import static com.boothen.jsonedit.core.preferences.JsonPreferences.EDITOR_TRAILING_NEWLINE;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.core.JsonCorePlugin;

/**
 *
 * @author Matt Garner
 *
 */
public class JsonPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
//        IEclipsePreferences node = DefaultScope.INSTANCE.getNode(JsonPreferencesPlugin.PLUGIN_ID);
        IPreferenceStore node = JsonCorePlugin.getDefault().getPreferenceStore();

        node.setDefault(AUTO_FORMAT_ON_SAVE, false);
        node.setDefault(EDITOR_TRAILING_NEWLINE, true);
        node.setDefault(EDITOR_MATCHING_BRACKETS, true);
        node.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, colorToString(0, 128, 0));

        // See com.boothen.jsonedit.preferences.format.JsonFormatter.Affix for valid values
        node.setDefault("BEGIN_ARRAY.suffix", "NEWLINE");
        node.setDefault("BEGIN_OBJECT.suffix", "NEWLINE");
        node.setDefault("COLON.suffix", "SPACE");
        node.setDefault("COMMA.suffix", "NEWLINE");
        node.setDefault("END_OBJECT.prefix", "NEWLINE");
        node.setDefault("END_ARRAY.prefix", "NEWLINE");

        node.setDefault(TokenStyle.KEY.color(), colorToString(64, 128, 64));
        node.setDefault(TokenStyle.TEXT.color(), colorToString(16, 0, 160));
        node.setDefault(TokenStyle.NULL.color(), colorToString(0, 0, 0));
        node.setDefault(TokenStyle.BOOLEAN.color(), colorToString(0, 0, 0));
        node.setDefault(TokenStyle.NUMBER.color(), colorToString(16, 0, 160));
        node.setDefault(TokenStyle.COMMENT.color(), colorToString(16, 128, 16));
        node.setDefault(TokenStyle.ERROR.color(), colorToString(224, 0, 0));

        node.setDefault(TokenStyle.NULL.isBold(), true);
        node.setDefault(TokenStyle.BOOLEAN.isBold(), true);
    }

    private static String colorToString(int red, int green, int blue) {
        return StringConverter.asString(new RGB(red, green, blue));
    }
}
