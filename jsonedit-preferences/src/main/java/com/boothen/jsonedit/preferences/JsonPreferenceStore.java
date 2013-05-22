package com.boothen.jsonedit.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;

import com.boothen.jsonedit.coloring.JsonColorProvider;

public class JsonPreferenceStore {

	public static final String SPACES_FOR_TABS = "spaces_for_tabs"; //$NON-NLS-1$
	public static final String NUM_SPACES = "num_spaces"; //$NON-NLS-1$
	public static final String EDITOR_MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1l$
	public static final String EDITOR_MATCHING_BRACKETS_COLOR =  "matchingBracketsColor"; //$NON-NLS-1$
	public static final String AUTO_FORMAT_ON_SAVE =  "autoFormatOnSave"; //$NON-NLS-1$
	public static final String STRING_COLOR = "stringColor"; //$NON-NLS-1$
	public static final String VALUE_COLOR = "valueColor"; //$NON-NLS-1$
	public static final String NULL_COLOR = "nullColor"; //$NON-NLS-1$
	public static final String DEFAULT_COLOR = "defaultColor"; //$NON-NLS-1$

	private IPreferenceStore preferenceStore;

	@Deprecated
	// Look to remove static reference
	private static JsonPreferenceStore jsonPreferenceStore;

	public JsonPreferenceStore(IPreferenceStore preferenceStore) {
		this.preferenceStore = preferenceStore;
		this.preferenceStore.setDefault(SPACES_FOR_TABS, true);
		this.preferenceStore.setDefault(NUM_SPACES, 4);
		this.preferenceStore.setDefault(EDITOR_MATCHING_BRACKETS, true);
		this.preferenceStore.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, StringConverter.asString(JsonColorProvider.STRING));
		this.preferenceStore.setDefault(AUTO_FORMAT_ON_SAVE, false);
		this.preferenceStore.setDefault(STRING_COLOR, StringConverter.asString(JsonColorProvider.STRING));
		this.preferenceStore.setDefault(VALUE_COLOR, StringConverter.asString(JsonColorProvider.VALUE));
		this.preferenceStore.setDefault(NULL_COLOR, StringConverter.asString(JsonColorProvider.NULL));
		this.preferenceStore.setDefault(DEFAULT_COLOR, StringConverter.asString(JsonColorProvider.DEFAULT));
		jsonPreferenceStore = this;
	}

	public IPreferenceStore getIPreferenceStore() {
		return preferenceStore;
	}

	public static JsonPreferenceStore getJsonPreferenceStore() {
		return jsonPreferenceStore;
	}
}
