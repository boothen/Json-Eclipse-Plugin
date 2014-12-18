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
package com.boothen.jsonedit.model.entry;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TypedPosition;

public class JsonEntry {

	private final TypedPosition typedPosition;
	private String content;
	
	public JsonEntry(TypedPosition typedPosition, String content) {
		super();
		this.typedPosition = typedPosition;
		this.content = content;
	}
	
	public static JsonEntry createJsonEntry(TypedPosition typedPosition, IDocument document) {
		try {
			String content = document.get(typedPosition.getOffset(), typedPosition.getLength());
			return new JsonEntry(typedPosition, content);
		} catch (BadLocationException e) {
			return null;
		}
	}

	public String getContent() {
		return content;
	}

	public String getType() {
		return typedPosition.getType();
	}
	
	public int getOffset() {
		return typedPosition.getOffset();
	}
	
	public int getLength() {
		return typedPosition.getLength();
	}
}
