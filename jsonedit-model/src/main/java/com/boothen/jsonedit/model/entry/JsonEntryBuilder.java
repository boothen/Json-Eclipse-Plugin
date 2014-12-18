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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TypedPosition;

import com.boothen.jsonedit.type.JsonDocumentType;

public class JsonEntryBuilder {

    public final static String JSON_ELEMENTS = "__json_elements"; //$NON-NLS-1$
    
	public List<JsonEntry> buildJsonEntries(IDocument document) {
		List<JsonEntry> jsonEntries = new LinkedList<JsonEntry>();

		Position[] positions = getPositions(document);
		for (Position position : positions) {
		    if (!(position instanceof TypedPosition)) {
		        continue;
		    }
		    TypedPosition typedPosition = (TypedPosition) position;
			if (JsonDocumentType.DOCUMENT_TYPES.contains(typedPosition.getType())) {
				JsonEntry jsonEntry = JsonEntry.createJsonEntry(typedPosition, document);
				jsonEntries.add(jsonEntry);
			}
		}
		return jsonEntries;
	}
	
	private Position[] getPositions(IDocument document) {
	    try {
            return document.getPositions(JSON_ELEMENTS);
        } catch (BadPositionCategoryException e) {
            return new Position[0];
        }
	}
}
