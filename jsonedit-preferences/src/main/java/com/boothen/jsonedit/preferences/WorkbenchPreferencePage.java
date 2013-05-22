/**
 *
 */
package com.boothen.jsonedit.preferences;


import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Workbench Preferences for the plugin.
 *
 * @author Matt Garner
 *
 */
public class WorkbenchPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {


	/**
	 *
	 */
	public WorkbenchPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		IPreferenceStore store = JsonPreferenceStore.getJsonPreferenceStore().getIPreferenceStore();
		setPreferenceStore(store);

	}



	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return JsonPreferenceStore.getJsonPreferenceStore().getIPreferenceStore();
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
	protected void createFieldEditors() {
		BooleanFieldEditor spacesForTab = new BooleanFieldEditor(
				JsonPreferenceStore.SPACES_FOR_TABS,
				"Insert Spaces For Tabs",
				getFieldEditorParent());
		addField(spacesForTab);

		IntegerFieldEditor indentSpaces = new IntegerFieldEditor(
				JsonPreferenceStore.NUM_SPACES,
				"&Number of spaces to indent:",
				getFieldEditorParent(), 1);
		indentSpaces.setValidRange(0, 10);
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

}
