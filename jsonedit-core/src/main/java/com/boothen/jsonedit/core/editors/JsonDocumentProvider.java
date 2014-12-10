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
