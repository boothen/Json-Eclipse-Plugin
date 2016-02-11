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
package com.boothen.jsonedit.preferences;


import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.boothen.jsonedit.core.JsonEditorPlugin;


/**
 * Workbench Preferences for the plugin.
 *
 * @author Matt Garner
 *
 */
public class WorkbenchPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage, IPropertyChangeListener {

    private BooleanFieldEditor spacesForTab;
    private IntegerFieldEditor indentSpaces;

    /**
     *
     */
    public WorkbenchPreferencePage() {
        super(FieldEditorPreferencePage.GRID);
        IPreferenceStore store = JsonEditorPlugin.getDefault().getPreferenceStore();
        setPreferenceStore(store);
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected void createFieldEditors() {

        spacesForTab = new BooleanFieldEditor(
                JsonPreferenceInitializer.SPACES_FOR_TABS,
                "Insert Spaces For Tabs",
                getFieldEditorParent());
        addField(spacesForTab);

        indentSpaces = new IntegerFieldEditor(
                JsonPreferenceInitializer.NUM_SPACES,
                "&Number of spaces to indent:",
                getFieldEditorParent(), 1);
        indentSpaces.setValidRange(0, 10);
        addField(indentSpaces);

        BooleanFieldEditor autoFormatOnSave = new BooleanFieldEditor(
                JsonPreferenceInitializer.AUTO_FORMAT_ON_SAVE,
                "Auto Format On Save",
                getFieldEditorParent());
        addField(autoFormatOnSave);

        ColorFieldEditor stringColor = new ColorFieldEditor(JsonPreferenceInitializer.STRING_COLOR, "&Key Attribute Color", getFieldEditorParent());
        addField(stringColor);

        ColorFieldEditor valueColor = new ColorFieldEditor(JsonPreferenceInitializer.VALUE_COLOR, "&Value Attribute Color", getFieldEditorParent());
        addField(valueColor);

        ColorFieldEditor nullColor = new ColorFieldEditor(JsonPreferenceInitializer.NULL_COLOR, "&Null Value Color", getFieldEditorParent());
        addField(nullColor);

        ColorFieldEditor defaultColor = new ColorFieldEditor(JsonPreferenceInitializer.DEFAULT_COLOR, "&Default Color", getFieldEditorParent());
        addField(defaultColor);
    }
}
