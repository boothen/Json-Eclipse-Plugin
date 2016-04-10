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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.editor.model.JsonReconcilingStrategy;
import com.boothen.jsonedit.model.AntlrTokenScanner;
import com.boothen.jsonedit.model.TokenStyler;
import com.boothen.jsonedit.preferences.JsonTokenStyler;
import com.boothen.jsonedit.preferences.format.JsonContentFormatter;
import com.boothen.jsonedit.quickoutline.QuickOutlinePopup;

/**
 * Configures the text editor.
 */
public class JsonSourceViewerConfiguration extends TextSourceViewerConfiguration {

    private JsonTextEditor textEditor;

    public JsonSourceViewerConfiguration(JsonTextEditor textEditor, IPreferenceStore iPreferenceStore) {
        super(iPreferenceStore);
        this.textEditor = textEditor;
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler = new PresentationReconciler();

        JSONLexer lexer = new JSONLexer(null);
        lexer.removeErrorListeners(); // don't print lexer errors to stderr
        TokenStyler mapping = new JsonTokenStyler(fPreferenceStore);
        AntlrTokenScanner scanner = new AntlrTokenScanner(lexer, mapping);
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }

    @Override
    public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
        IContentFormatter fmt = new JsonContentFormatter(fPreferenceStore);
        return fmt;
    }

    @Override
    public IReconciler getReconciler(ISourceViewer sourceViewer) {
        JsonReconcilingStrategy strategy = new JsonReconcilingStrategy(textEditor);
        MonoReconciler reconciler = new MonoReconciler(strategy, false);
        return reconciler;
    }

    @Override
    public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
        return new IAutoEditStrategy[] { new DefaultIndentLineAutoEditStrategy() };
    }

    /**
     * Returns the outline presenter which will determine and shown
     * information requested for the current cursor position.
     *
     * @param sourceViewer the source viewer to be configured by this configuration
     * @return an information presenter
     */
    public IInformationPresenter getOutlinePresenter(final ISourceViewer sourceViewer) {
        IInformationControlCreator controlCreator = new IInformationControlCreator() {
            @Override
            public IInformationControl createInformationControl(Shell parent) {
                return new QuickOutlinePopup(parent, sourceViewer);
            }
        };
        InformationPresenter presenter = new InformationPresenter(controlCreator);
        presenter.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
        presenter.setInformationProvider(new JsonInformationProvider(), IDocument.DEFAULT_CONTENT_TYPE);
        presenter.setSizeConstraints(50, 20, true, false);
        return presenter;
    }
}
