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

import com.boothen.jsonedit.editor.model.JsonReconcilingStrategy;
import com.boothen.jsonedit.editor.text.JsonIndentLineAutoEditStrategy;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.JsonStringScanner;
import com.boothen.jsonedit.text.LineEndingUtil;

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
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler= new PresentationReconciler();
        
        DefaultDamagerRepairer dr= new DefaultDamagerRepairer(jsonStringScanner);    
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        
        return reconciler;
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
        
    }

    private String getTextEditorLineEnding() {
        IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);
        return LineEndingUtil.determineProjectLineEnding(file);
    }
}
