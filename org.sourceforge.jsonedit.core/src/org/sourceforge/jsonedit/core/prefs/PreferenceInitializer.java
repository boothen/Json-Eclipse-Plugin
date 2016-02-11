package org.sourceforge.jsonedit.core.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.sourceforge.jsonedit.core.JsonEditorPlugin;
import org.sourceforge.jsonedit.core.util.JsonColorProvider;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		
		IPreferenceStore store = JsonEditorPlugin.getJsonPreferenceStore();
		PreferenceConverter.setDefault(store, JsonColorProvider.STRING, new RGB(0, 128, 0));
		PreferenceConverter.setDefault(store, JsonColorProvider.DEFAULT, new RGB(0, 0, 0));
		PreferenceConverter.setDefault(store, JsonColorProvider.VALUE, new RGB(0, 0, 128));
		PreferenceConverter.setDefault(store, JsonColorProvider.NULL, new RGB(128, 0, 128));
		
		store.setDefault(JsonEditorPlugin.SPACES_FOR_TABS, true);
		store.setDefault(JsonEditorPlugin.NUM_SPACES, 4);
		store.setDefault(JsonEditorPlugin.EDITOR_MATCHING_BRACKETS, true);
		
		store.setDefault(JsonEditorPlugin.EDITOR_MATCHING_BRACKETS_COLOR, new Color(Display.getCurrent(), new RGB(0, 128, 0)).toString());
		
	}
}
