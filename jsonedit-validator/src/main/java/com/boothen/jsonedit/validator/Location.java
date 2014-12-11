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
package com.boothen.jsonedit.validator;

import org.eclipse.core.resources.IFile;

public class Location {

	public final IFile file;
	public final String key;
	public final int charStart;
	public final int charEnd;

	public Location(IFile file, String key, int charStart, int charEnd) {
		this.file = file;
		this.key = key;
		this.charStart = charStart;
		this.charEnd = charEnd;
	}

	public Location(IFile file, String key, int charStart) {
		this.file = file;
		this.key = key;
		this.charStart = charStart;
		this.charEnd = charStart + 1;
	}
}


