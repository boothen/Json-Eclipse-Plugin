package com.boothen.jsonedit.text;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

public class LineEndingUtil {

	public static String determineProjectLineEnding(IFile file) {
		String lineEnding = null;
		if (file != null && file.getProject() != null) {
			lineEnding = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { new ProjectScope(file.getProject()) });
		}

		if (lineEnding == null) {
			lineEnding = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] {
					InstanceScope.INSTANCE});
		}

		if (lineEnding == null) {
			lineEnding = System.getProperty(Platform.PREF_LINE_SEPARATOR);
		}

		return lineEnding;
	}
}
