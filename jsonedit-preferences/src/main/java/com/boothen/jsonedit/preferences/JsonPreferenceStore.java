package com.boothen.jsonedit.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.boothen.jsonedit.coloring.JsonColorProvider;

/**
 * AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH
 * AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS
 * org.eclipse.ui.editors.prefs
 *
 *
 * @author Matt Garner
 *
 */
public class JsonPreferenceStore {

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


	private IPreferenceStore preferenceStore;
	private IPreferenceStore editorPreferenceStore;

	@Deprecated
	// Look to remove static reference
	private static JsonPreferenceStore jsonPreferenceStore;

	private JsonPreferenceStore(IPreferenceStore preferenceStore, IPreferenceStore editorPreferenceStore) {
		this.preferenceStore = preferenceStore;
		this.editorPreferenceStore = editorPreferenceStore;
		this.preferenceStore.setDefault(OVERRIDE_TAB_SETTING, false);
		this.preferenceStore.setDefault(SPACES_FOR_TABS, true);
		this.preferenceStore.setDefault(NUM_SPACES, 4);
		this.preferenceStore.setDefault(EDITOR_MATCHING_BRACKETS, true);
		this.preferenceStore.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, StringConverter.asString(JsonColorProvider.STRING));
		this.preferenceStore.setDefault(AUTO_FORMAT_ON_SAVE, false);
		this.preferenceStore.setDefault(STRING_COLOR, StringConverter.asString(JsonColorProvider.STRING));
		this.preferenceStore.setDefault(VALUE_COLOR, StringConverter.asString(JsonColorProvider.VALUE));
		this.preferenceStore.setDefault(NULL_COLOR, StringConverter.asString(JsonColorProvider.NULL));
		this.preferenceStore.setDefault(DEFAULT_COLOR, StringConverter.asString(JsonColorProvider.DEFAULT));
		this.preferenceStore.setDefault(ERROR_TEXT_STYLE, editorPreferenceStore.getDefaultString(ERROR_TEXT_STYLE));
		this.preferenceStore.setDefault(ERROR_INDICATION, editorPreferenceStore.getDefaultString(ERROR_INDICATION));
		this.preferenceStore.setDefault(ERROR_INDICATION_COLOR, editorPreferenceStore.getDefaultString(ERROR_INDICATION_COLOR));
		jsonPreferenceStore = this;
	}

	public IPreferenceStore getIPreferenceStore() {
		return preferenceStore;
	}

	public IPreferenceStore getEditorPreferenceStore() {
		return editorPreferenceStore;
	}

	public static JsonPreferenceStore getJsonPreferenceStore() {
		if (jsonPreferenceStore == null) {
			IPreferenceStore preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_BUNDLE_SYMBOLIC_NAME);
			IPreferenceStore editorPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.ui.editors");
			jsonPreferenceStore = new JsonPreferenceStore(preferenceStore, editorPreferenceStore);
		}
		return jsonPreferenceStore;
	}

	public  Boolean getSpacesForTab() {
		if (preferenceStore.getBoolean(OVERRIDE_TAB_SETTING)) {
			return preferenceStore.getBoolean(SPACES_FOR_TABS);
		}
		return editorPreferenceStore.getBoolean("spacesForTabs");
	}

	public int getTabWidth() {
		if (preferenceStore.getBoolean(OVERRIDE_TAB_SETTING)) {
			return preferenceStore.getInt(NUM_SPACES);
		}
		return editorPreferenceStore.getInt("tabWidth");
	}

	public void updateEditorPreferences() {
		this.preferenceStore.setValue(ERROR_TEXT_STYLE, editorPreferenceStore.getString(ERROR_TEXT_STYLE));
		this.preferenceStore.setValue(ERROR_INDICATION, editorPreferenceStore.getBoolean(ERROR_INDICATION));
		this.preferenceStore.setValue(ERROR_INDICATION_COLOR, editorPreferenceStore.getString(ERROR_INDICATION_COLOR));
	}
}
