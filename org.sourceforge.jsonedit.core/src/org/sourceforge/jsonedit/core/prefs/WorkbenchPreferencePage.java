/**
 * 
 */
package org.sourceforge.jsonedit.core.prefs;


import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sourceforge.jsonedit.core.JsonEditorPlugin;

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
		// TODO Auto-generated constructor stub
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
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
