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


/**
 * Workbench Preferences for the plugin.
 *
 * @author Matt Garner
 *
 */
public class WorkbenchPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage, IPropertyChangeListener {

    private BooleanFieldEditor usePlatformTabSetting;
    private BooleanFieldEditor spacesForTab;
    private IntegerFieldEditor indentSpaces;

    /**
     *
     */
    public WorkbenchPreferencePage() {
        super(FieldEditorPreferencePage.GRID);
        IPreferenceStore store = JsonPreferenceStore.getIPreferenceStore();
        setPreferenceStore(store);

    }

    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return JsonPreferenceStore.getIPreferenceStore();
    }



    /**
     * @param title
     */

    public WorkbenchPreferencePage(String title) {
        super(FieldEditorPreferencePage.GRID);
    }

    @Override
    public void init(IWorkbench workbench) {

    }

    @Override
    public void dispose() {
        if (usePlatformTabSetting != null) {
            usePlatformTabSetting.setPropertyChangeListener(null);
        }
        super.dispose();
    }

    @Override
    protected void createFieldEditors() {

        Boolean overrideTabSetting = getPreferenceStore().getBoolean(JsonPreferenceStore.OVERRIDE_TAB_SETTING);
        usePlatformTabSetting = new BooleanFieldEditor(
                JsonPreferenceStore.OVERRIDE_TAB_SETTING,
                "Override Workbench Tab Setting",
                getFieldEditorParent());
        addField(usePlatformTabSetting);
        usePlatformTabSetting.setPropertyChangeListener(this);

        spacesForTab = new BooleanFieldEditor(
                JsonPreferenceStore.SPACES_FOR_TABS,
                "Insert Spaces For Tabs",
                getFieldEditorParent());
        spacesForTab.setEnabled(overrideTabSetting, getFieldEditorParent());
        addField(spacesForTab);

        indentSpaces = new IntegerFieldEditor(
                JsonPreferenceStore.NUM_SPACES,
                "&Number of spaces to indent:",
                getFieldEditorParent(), 1);
        indentSpaces.setValidRange(0, 10);
        indentSpaces.setEnabled(overrideTabSetting, getFieldEditorParent());
        addField(indentSpaces);

        BooleanFieldEditor autoFormatOnSave = new BooleanFieldEditor(
                JsonPreferenceStore.AUTO_FORMAT_ON_SAVE,
                "Auto Format On Save",
                getFieldEditorParent());
        addField(autoFormatOnSave);

        ColorFieldEditor stringColor = new ColorFieldEditor(JsonPreferenceStore.STRING_COLOR, "&Key Attribute Color", getFieldEditorParent());
        addField(stringColor);

        ColorFieldEditor valueColor = new ColorFieldEditor(JsonPreferenceStore.VALUE_COLOR, "&Value Attribute Color", getFieldEditorParent());
        addField(valueColor);

        ColorFieldEditor nullColor = new ColorFieldEditor(JsonPreferenceStore.NULL_COLOR, "&Null Value Color", getFieldEditorParent());
        addField(nullColor);

        ColorFieldEditor defaultColor = new ColorFieldEditor(JsonPreferenceStore.DEFAULT_COLOR, "&Default Color", getFieldEditorParent());
        addField(defaultColor);
    }

    @Override
    protected void performDefaults() {
        Boolean defaultUsePlatformTabSetting = getPreferenceStore().getDefaultBoolean(JsonPreferenceStore.OVERRIDE_TAB_SETTING);
        togglePreferences(defaultUsePlatformTabSetting);
        super.performDefaults();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getSource() == usePlatformTabSetting) {
            togglePreferences((Boolean) event.getNewValue());
        }
        super.propertyChange(event);
    }

    private void togglePreferences(Boolean enabled) {
        spacesForTab.setEnabled(enabled, getFieldEditorParent());
        indentSpaces.setEnabled(enabled, getFieldEditorParent());
    }
}
