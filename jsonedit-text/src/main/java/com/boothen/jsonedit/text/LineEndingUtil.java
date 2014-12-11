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
