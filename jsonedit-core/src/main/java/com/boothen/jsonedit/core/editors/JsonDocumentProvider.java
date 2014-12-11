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
package com.boothen.jsonedit.core.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.boothen.jsonedit.core.model.JsonPartitionScanner;
import com.boothen.jsonedit.core.model.JsonPartitioner;

public class JsonDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException

	{
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner = new JsonPartitioner(
					new JsonPartitionScanner(), new String[] {
							JsonPartitionScanner.JSON_OBJECT_CLOSE,
							JsonPartitionScanner.JSON_OBJECT_OPEN,
							JsonPartitionScanner.JSON_ARRAY_CLOSE,
							JsonPartitionScanner.JSON_ARRAY_OPEN,
							JsonPartitionScanner.JSON_STRING,
							JsonPartitionScanner.JSON_NUMBER,
							JsonPartitionScanner.JSON_TRUE,
							JsonPartitionScanner.JSON_FALSE,
							JsonPartitionScanner.JSON_NULL,
							JsonPartitionScanner.JSON_COMMA,
							JsonPartitionScanner.JSON_COLON
							});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}
