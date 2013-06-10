package com.boothen.jsonedit.core.editors.listeners;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.boothen.jsonedit.core.editors.JsonSourceViewerConfiguration;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;

public class PlatformPreferenceListener implements IEclipsePreferences.IPreferenceChangeListener, IPropertyChangeListener {

	private JsonSourceViewerConfiguration viewerConfiguration;
	private JsonPreferenceStore jsonPreferenceStore;
	private IEclipsePreferences iEclipsePreferences;

	public PlatformPreferenceListener(JsonSourceViewerConfiguration viewerConfiguration, JsonPreferenceStore jsonPreferenceStore) {
		this.viewerConfiguration = viewerConfiguration;
		this.jsonPreferenceStore = jsonPreferenceStore;
	}

	/**
	 * Handle changes to the project preferences. Interested in line ending changes.
	 */
	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		viewerConfiguration.handlePreferenceStoreChanged();
	}

	/**
	 * Handle text editor preference changes. Interested in spacesForTabs and tabWidth.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		viewerConfiguration.handlePreferenceStoreChanged();
		jsonPreferenceStore.updateEditorPreferences();
	}

	public void setPreferenceChangeListener(IFile file) {
		iEclipsePreferences = new ProjectScope(file.getProject()).getNode(Platform.PI_RUNTIME);
		iEclipsePreferences.addPreferenceChangeListener(this);
		JsonPreferenceStore.getEditorPreferenceStore().addPropertyChangeListener(this);
	}

	public void removePreferenceChangeListener() {
		if (iEclipsePreferences != null) {
			iEclipsePreferences.removePreferenceChangeListener(this);
			JsonPreferenceStore.getEditorPreferenceStore().removePropertyChangeListener(this);
		}
	}
}
