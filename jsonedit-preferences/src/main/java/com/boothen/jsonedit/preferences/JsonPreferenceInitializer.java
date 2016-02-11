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

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;

import com.boothen.jsonedit.core.JsonEditorPlugin;

/**
 *
 * @author Matt Garner
 *
 */
public class JsonPreferenceInitializer extends AbstractPreferenceInitializer {

    public static final String SPACES_FOR_TABS = "spaces_for_tabs"; //$NON-NLS-1$
    public static final String NUM_SPACES = "num_spaces"; //$NON-NLS-1$
    public static final String EDITOR_MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1l$
    public static final String EDITOR_MATCHING_BRACKETS_COLOR =  "matchingBracketsColor"; //$NON-NLS-1$
    public static final String AUTO_FORMAT_ON_SAVE =  "autoFormatOnSave"; //$NON-NLS-1$
    public static final String STRING_COLOR = "stringColor"; //$NON-NLS-1$
    public static final String VALUE_COLOR = "valueColor"; //$NON-NLS-1$
    public static final String NULL_COLOR = "nullColor"; //$NON-NLS-1$
    public static final String ERROR_COLOR = "errorColor"; //$NON-NLS-1$
    public static final String DEFAULT_COLOR = "defaultColor"; //$NON-NLS-1$
    public static final String ERROR_TEXT_STYLE = "errorTextStyle"; //$NON-NLS-1$
    public static final String ERROR_INDICATION = "errorIndication"; //$NON-NLS-1$
    public static final String ERROR_INDICATION_COLOR = "errorIndicationColor"; //$NON-NLS-1$

    private JsonPreferenceInitializer() {

    }

    @Override
    public void initializeDefaultPreferences() {
        IEclipsePreferences node = DefaultScope.INSTANCE.getNode(JsonEditorPlugin.PLUGIN_ID);

        node.putBoolean(SPACES_FOR_TABS, true);
        node.putInt(NUM_SPACES, 4);
        node.putBoolean(EDITOR_MATCHING_BRACKETS, true);
        node.putBoolean(AUTO_FORMAT_ON_SAVE, false);
        node.put(EDITOR_MATCHING_BRACKETS_COLOR, StringConverter.asString(new RGB(0, 128, 0)));
        node.put(STRING_COLOR, StringConverter.asString(new RGB(0, 128, 0)));
        node.put(VALUE_COLOR, StringConverter.asString(new RGB(0, 0, 128)));
        node.put(NULL_COLOR, StringConverter.asString(new RGB(128, 0, 128)));
        node.put(ERROR_COLOR, StringConverter.asString(new RGB(255, 0, 0)));
        node.put(DEFAULT_COLOR, StringConverter.asString(new RGB(0, 0, 0)));
    }
}
