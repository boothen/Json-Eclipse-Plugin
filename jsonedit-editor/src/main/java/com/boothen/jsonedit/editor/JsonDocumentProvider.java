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
package com.boothen.jsonedit.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.boothen.jsonedit.editor.model.JsonPartitionScanner;
import com.boothen.jsonedit.editor.model.JsonPartitioner;
import com.boothen.jsonedit.type.JsonDocumentType;

public class JsonDocumentProvider extends FileDocumentProvider {

    protected IDocument createDocument(Object element) throws CoreException

    {
        IDocument document = super.createDocument(element);
        if (document != null) {
            IDocumentPartitioner partitioner = new JsonPartitioner(
                    new JsonPartitionScanner(), new String[] {
                            JsonDocumentType.JSON_OBJECT_CLOSE,
                            JsonDocumentType.JSON_OBJECT_OPEN,
                            JsonDocumentType.JSON_ARRAY_CLOSE,
                            JsonDocumentType.JSON_ARRAY_OPEN,
                            JsonDocumentType.JSON_STRING,
                            JsonDocumentType.JSON_NUMBER,
                            JsonDocumentType.JSON_TRUE,
                            JsonDocumentType.JSON_FALSE,
                            JsonDocumentType.JSON_NULL,
                            JsonDocumentType.JSON_COMMA,
                            JsonDocumentType.JSON_COLON });
            partitioner.connect(document);
            document.setDocumentPartitioner(partitioner);
        }
        return document;
    }
}
