package com.boothen.jsonedit.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;

import com.boothen.jsonedit.coloring.JsonColorProvider;

/**
 *
 * @author Matt Garner
 *
 */
public class JsonPreferenceStore extends AbstractPreferenceInitializer {

	private static final String PLUGIN_BUNDLE_SYMBOLIC_NAME = "jsonedit-core";

	public static final String OVERRIDE_TAB_SETTING = "override_tab_setting";
	public static final String SPACES_FOR_TABS = "spaces_for_tabs"; //$NON-NLS-1$
	public static final String NUM_SPACES = "num_spaces"; //$NON-NLS-1$
	public static final String EDITOR_MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1l$
	public static final String EDITOR_MATCHING_BRACKETS_COLOR =  "matchingBracketsColor"; //$NON-NLS-1$
	public static final String AUTO_FORMAT_ON_SAVE =  "autoFormatOnSave"; //$NON-NLS-1$
	public static final String STRING_COLOR = "stringColor"; //$NON-NLS-1$
	public static final String VALUE_COLOR = "valueColor"; //$NON-NLS-1$
	public static final String NULL_COLOR = "nullColor"; //$NON-NLS-1$
	public static final String DEFAULT_COLOR = "defaultColor"; //$NON-NLS-1$
	public static final String ERROR_TEXT_STYLE = "errorTextStyle"; //$NON-NLS-1$
	public static final String ERROR_INDICATION = "errorIndication"; //$NON-NLS-1$
	public static final String ERROR_INDICATION_COLOR = "errorIndicationColor"; //$NON-NLS-1$


	private static IPreferenceStore preferenceStore;
	private static IPreferenceStore editorPreferenceStore;
	private static IPreferenceStore chainedPreferenceStore;

	public JsonPreferenceStore() {

	}

	public  Boolean getSpacesForTab() {
		IPreferenceStore preferenceStore = getIPreferenceStore();
		if (preferenceStore.getBoolean(OVERRIDE_TAB_SETTING)) {
			return preferenceStore.getBoolean(SPACES_FOR_TABS);
		}
		IPreferenceStore editorPreferenceStore = getEditorPreferenceStore();
		return editorPreferenceStore.getBoolean("spacesForTabs");
	}

	public int getTabWidth() {
		IPreferenceStore preferenceStore = getIPreferenceStore();
		if (preferenceStore.getBoolean(OVERRIDE_TAB_SETTING)) {
			return preferenceStore.getInt(NUM_SPACES);
		}
		IPreferenceStore editorPreferenceStore = getEditorPreferenceStore();
		return editorPreferenceStore.getInt("tabWidth");
	}

	public void updateEditorPreferences() {
		IPreferenceStore preferenceStore = getIPreferenceStore();
		preferenceStore.setValue(ERROR_TEXT_STYLE, editorPreferenceStore.getString(ERROR_TEXT_STYLE));
		preferenceStore.setValue(ERROR_INDICATION, editorPreferenceStore.getBoolean(ERROR_INDICATION));
		preferenceStore.setValue(ERROR_INDICATION_COLOR, editorPreferenceStore.getString(ERROR_INDICATION_COLOR));
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferenceStore = getIPreferenceStore();
		IPreferenceStore editorPreferenceStore = getEditorPreferenceStore();
		preferenceStore.setDefault(OVERRIDE_TAB_SETTING, false);
		preferenceStore.setDefault(SPACES_FOR_TABS, true);
		preferenceStore.setDefault(NUM_SPACES, 4);
		preferenceStore.setDefault(EDITOR_MATCHING_BRACKETS, true);
		preferenceStore.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, StringConverter.asString(JsonColorProvider.STRING));
		preferenceStore.setDefault(AUTO_FORMAT_ON_SAVE, false);
		preferenceStore.setDefault(STRING_COLOR, StringConverter.asString(JsonColorProvider.STRING));
		preferenceStore.setDefault(VALUE_COLOR, StringConverter.asString(JsonColorProvider.VALUE));
		preferenceStore.setDefault(NULL_COLOR, StringConverter.asString(JsonColorProvider.NULL));
		preferenceStore.setDefault(DEFAULT_COLOR, StringConverter.asString(JsonColorProvider.DEFAULT));
		preferenceStore.setDefault(ERROR_TEXT_STYLE, editorPreferenceStore.getDefaultString(ERROR_TEXT_STYLE));
		preferenceStore.setDefault(ERROR_INDICATION, editorPreferenceStore.getDefaultString(ERROR_INDICATION));
		preferenceStore.setDefault(ERROR_INDICATION_COLOR, editorPreferenceStore.getDefaultString(ERROR_INDICATION_COLOR));
	}

	public static IPreferenceStore getChainedPreferenceStore() {
		if (chainedPreferenceStore == null) {
			chainedPreferenceStore = new ChainedPreferenceStore(new IPreferenceStore[]{ getIPreferenceStore(), getEditorPreferenceStore()});
		}
		return chainedPreferenceStore;
	}

	public static IPreferenceStore getIPreferenceStore() {
		if (preferenceStore == null) {
			preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_BUNDLE_SYMBOLIC_NAME);
		}
		return preferenceStore;
	}

	public static IPreferenceStore getEditorPreferenceStore() {
		if (editorPreferenceStore == null) {
			editorPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.ui.editors");
		}
		return editorPreferenceStore;
	}
}
