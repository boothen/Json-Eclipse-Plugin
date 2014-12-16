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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;

import com.boothen.jsonedit.type.JsonDocumentType;

public class JsonEntryBuilder {

	public List<JsonEntry> buildJsonEntries(IDocument document) {
		List<JsonEntry> jsonEntries = new LinkedList<JsonEntry>();

		ITypedRegion[] partitions = getPartitions(document);
		for (ITypedRegion partition : partitions) {
			if (JsonDocumentType.DOCUMENT_TYPES.contains(partition.getType())) {
				JsonEntry jsonEntry = JsonEntry.createJsonEntry(partition, document);
				jsonEntries.add(jsonEntry);
			}
		}
		return jsonEntries;
	}

	private ITypedRegion[] getPartitions(IDocument document) {

		try {
			return document.computePartitioning(0, document.getLength());
		} catch (BadLocationException e) {
			return new ITypedRegion[0];
		}
	}
}
