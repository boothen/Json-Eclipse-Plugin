package com.boothen.jsonedit.core.editors;


import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.core.model.JsonReconcilingStrategy;
import com.boothen.jsonedit.core.text.JsonIndentLineAutoEditStrategy;

/**
 * JsonSourceViewerConfiguration manages the coloring of the text.
 *
 * @author Matt Garner
 *
 */
public class JsonSourceViewerConfiguration extends SourceViewerConfiguration {

	private JsonTextEditor textEditor;

	public JsonSourceViewerConfiguration(JsonTextEditor textEditor) {
		super();
		this.textEditor = textEditor;
	}

	/* (non-Javadoc)
	 * Method declared on SourceViewerConfiguration
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

		PresentationReconciler reconciler= new PresentationReconciler();

		DefaultDamagerRepairer dr= new DefaultDamagerRepairer(JsonEditorPlugin.getDefault().getJsonScanner());
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
		return new IAutoEditStrategy[] { new JsonIndentLineAutoEditStrategy() };
	}

}
