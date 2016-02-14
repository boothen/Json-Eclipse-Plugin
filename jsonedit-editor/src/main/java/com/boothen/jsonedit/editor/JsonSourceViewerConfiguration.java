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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.boothen.jsonedit.editor.model.JsonReconcilingStrategy;
import com.boothen.jsonedit.editor.text.JsonIndentLineAutoEditStrategy;
import com.boothen.jsonedit.preferences.JsonPreferences;
import com.boothen.jsonedit.preferences.JsonPreferencesPlugin;
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

    public JsonSourceViewerConfiguration(JsonTextEditor textEditor) {
        super();
        this.textEditor = textEditor;
        IPreferenceStore store = JsonPreferencesPlugin.getDefault().getPreferenceStore();
        boolean spaces = store.getBoolean(JsonPreferences.SPACES_FOR_TABS);
        int numSpaces = store.getInt(JsonPreferences.NUM_SPACES);
        String lineEnding = "\n";
        jsonIndentLineAutoEditStrategy = new JsonIndentLineAutoEditStrategy(spaces, numSpaces, lineEnding);
        jsonStringScanner = new JsonStringScanner();
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler= new PresentationReconciler();

        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(jsonStringScanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }

    @Override
    public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
        return new JsonContentFormatter(textEditor);
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
        IPreferenceStore store = JsonPreferencesPlugin.getDefault().getPreferenceStore();
        boolean spaces = store.getBoolean(JsonPreferences.SPACES_FOR_TABS);
        int numSpaces = store.getInt(JsonPreferences.NUM_SPACES);

        String lineEnding = getTextEditorLineEnding();
        textEditor.updateTabWidth(numSpaces);
        jsonIndentLineAutoEditStrategy.initPreferences(spaces, numSpaces, lineEnding);
        jsonStringScanner.reinit();

    }

    private String getTextEditorLineEnding() {
        IFile file = textEditor.getEditorInput().getAdapter(IFile.class);
        return LineEndingUtil.determineProjectLineEnding(file);
    }
}
