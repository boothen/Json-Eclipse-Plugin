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
package com.boothen.jsonedit.preferences;

import static com.boothen.jsonedit.core.JsonPreferences.AUTO_FORMAT_ON_SAVE;
import static com.boothen.jsonedit.core.JsonPreferences.EDITOR_MATCHING_BRACKETS;
import static com.boothen.jsonedit.core.JsonPreferences.EDITOR_MATCHING_BRACKETS_COLOR;
import static com.boothen.jsonedit.core.JsonPreferences.NUM_SPACES;
import static com.boothen.jsonedit.core.JsonPreferences.SPACES_FOR_TABS;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_BOOLEAN_BOLD;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_BOOLEAN_COLOR;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_ERROR_COLOR;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_KEY_COLOR;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_NULL_BOLD;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_NULL_COLOR;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_NUMBER_COLOR;
import static com.boothen.jsonedit.core.JsonPreferences.STYLE_TEXT_COLOR;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.core.JsonEditorPlugin;

/**
 *
 * @author Matt Garner
 *
 */
public class JsonPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
//        IEclipsePreferences node = DefaultScope.INSTANCE.getNode(JsonPreferencesPlugin.PLUGIN_ID);
        IPreferenceStore node = JsonEditorPlugin.getDefault().getPreferenceStore();

        node.setDefault(SPACES_FOR_TABS, true);
        node.setDefault(NUM_SPACES, 4);
        node.setDefault(EDITOR_MATCHING_BRACKETS, true);
        node.setDefault(AUTO_FORMAT_ON_SAVE, false);
        node.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, colorToString(0, 128, 0));
        node.setDefault(STYLE_KEY_COLOR, colorToString(0, 128, 0));
        node.setDefault(STYLE_TEXT_COLOR, colorToString(0, 0, 128));
        node.setDefault(STYLE_NULL_COLOR, colorToString(0, 0, 0));
        node.setDefault(STYLE_BOOLEAN_COLOR, colorToString(0, 0, 0));
        node.setDefault(STYLE_NUMBER_COLOR, colorToString(0, 0, 128));
        node.setDefault(STYLE_ERROR_COLOR, colorToString(224, 0, 0));

        node.setDefault(STYLE_NULL_BOLD, true);
        node.setDefault(STYLE_BOOLEAN_BOLD, true);
    }

    private static String colorToString(int red, int green, int blue) {
        return StringConverter.asString(new RGB(red, green, blue));
    }
}
