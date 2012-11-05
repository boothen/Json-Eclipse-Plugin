/**
 *
 */
package com.boothen.jsonedit.core.prefs;


import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.boothen.jsonedit.core.JsonEditorPlugin;

/**
 * Workbench Preferences for the plugin.
 *
 * @author Matt Garner
 *
 */
public class WorkbenchPreferencePage extends FieldEditorPreferencePage
 implements
		IWorkbenchPreferencePage {


	/**
	 *
	 */
	public WorkbenchPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		IPreferenceStore store = JsonEditorPlugin.getJsonPreferenceStore();
		setPreferenceStore(store);

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
				JsonEditorPlugin.SPACES_FOR_TABS,
				"Insert Spaces For Tabs",
				getFieldEditorParent());
		addField(spacesForTab);

		IntegerFieldEditor indentSpaces = new IntegerFieldEditor(
				JsonEditorPlugin.NUM_SPACES,
				"&Number of spaces to indent:",
				getFieldEditorParent(), 1);
		indentSpaces.setValidRange(0, 10);
		addField(indentSpaces);
	}

}
