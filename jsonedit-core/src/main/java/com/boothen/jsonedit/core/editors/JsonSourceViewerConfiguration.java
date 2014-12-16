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


import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.boothen.jsonedit.core.model.JsonReconcilingStrategy;
import com.boothen.jsonedit.core.text.JsonIndentLineAutoEditStrategy;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.JsonConstantWordScanner;
import com.boothen.jsonedit.text.JsonNumberScanner;
import com.boothen.jsonedit.text.JsonStringScanner;
import com.boothen.jsonedit.text.LineEndingUtil;
import com.boothen.jsonedit.type.JsonDocumentType;

/**
 * JsonSourceViewerConfiguration manages the coloring of the text.
 *
 * @author Matt Garner
 *
 */
public class JsonSourceViewerConfiguration extends TextSourceViewerConfiguration {

	private JsonTextEditor textEditor;
	private JsonIndentLineAutoEditStrategy jsonIndentLineAutoEditStrategy;
	private JsonStringScanner jsonStringScanner;
	private JsonConstantWordScanner jsonTrueScanner;
	private JsonConstantWordScanner jsonFalseScanner;
	private JsonConstantWordScanner jsonNullScanner;
	private JsonNumberScanner jsonNumberScanner;
	private JsonPreferenceStore store;

	public JsonSourceViewerConfiguration(JsonTextEditor textEditor, JsonPreferenceStore store) {
		super();
		this.store = store;
		this.textEditor = textEditor;
		boolean spaces = store.getSpacesForTab();
		int numSpaces = store.getTabWidth();
		String lineEnding = "\n";
		jsonIndentLineAutoEditStrategy = new JsonIndentLineAutoEditStrategy(spaces, numSpaces, lineEnding);
		jsonStringScanner = new JsonStringScanner();
		jsonTrueScanner = new JsonConstantWordScanner("true");
		jsonFalseScanner = new JsonConstantWordScanner("false");
		jsonNullScanner = new JsonConstantWordScanner("null");
		jsonNumberScanner = new JsonNumberScanner();
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler= new PresentationReconciler();
		
		DefaultDamagerRepairer dr= new DefaultDamagerRepairer(jsonStringScanner);	
		reconciler.setDamager(dr, JsonDocumentType.JSON_STRING);
		reconciler.setRepairer(dr, JsonDocumentType.JSON_STRING);
		
		dr= new DefaultDamagerRepairer(jsonTrueScanner);	
		reconciler.setDamager(dr, JsonDocumentType.JSON_TRUE);
		reconciler.setRepairer(dr, JsonDocumentType.JSON_TRUE);
		
		dr= new DefaultDamagerRepairer(jsonFalseScanner);	
		reconciler.setDamager(dr, JsonDocumentType.JSON_FALSE);
		reconciler.setRepairer(dr, JsonDocumentType.JSON_FALSE);
		
		dr= new DefaultDamagerRepairer(jsonNullScanner);	
		reconciler.setDamager(dr, JsonDocumentType.JSON_NULL);
		reconciler.setRepairer(dr, JsonDocumentType.JSON_NULL);
		
		dr= new DefaultDamagerRepairer(jsonNumberScanner);	
		reconciler.setDamager(dr, JsonDocumentType.JSON_NUMBER);
		reconciler.setRepairer(dr, JsonDocumentType.JSON_NUMBER);
		
		return reconciler;
	}
	
	

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { 
				IDocument.DEFAULT_CONTENT_TYPE,
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
				JsonDocumentType.JSON_COLON};
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		JsonReconcilingStrategy strategy = new JsonReconcilingStrategy();
		strategy.setTextEditor(textEditor);
        MonoReconciler reconciler = new MonoReconciler(strategy,false);
        return reconciler;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		return new IAutoEditStrategy[] { jsonIndentLineAutoEditStrategy };
	}

	public void handlePreferenceStoreChanged() {
		boolean spacesForTab = store.getSpacesForTab();
		int tabWidth = store.getTabWidth();

		String lineEnding = getTextEditorLineEnding();
		textEditor.updateTabWidth(tabWidth);
		jsonIndentLineAutoEditStrategy.initPreferences(spacesForTab, tabWidth, lineEnding);
		jsonStringScanner.reinit();
		jsonTrueScanner.reinit();
		jsonFalseScanner.reinit();
		jsonNullScanner.reinit();
		jsonNumberScanner.reinit();
		
	}

	private String getTextEditorLineEnding() {
		IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);
		return LineEndingUtil.determineProjectLineEnding(file);
	}
}
