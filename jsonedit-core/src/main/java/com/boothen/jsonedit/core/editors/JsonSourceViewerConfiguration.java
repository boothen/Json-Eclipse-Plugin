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
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import com.boothen.jsonedit.core.model.JsonReconcilingStrategy;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.JsonIndentLineAutoEditStrategy;
import com.boothen.jsonedit.text.JsonScanner;
import com.boothen.jsonedit.text.LineEndingUtil;

/**
 * JsonSourceViewerConfiguration manages the coloring of the text.
 *
 * @author Matt Garner
 *
 */
public class JsonSourceViewerConfiguration extends SourceViewerConfiguration {

	private JsonTextEditor textEditor;
	private JsonIndentLineAutoEditStrategy jsonIndentLineAutoEditStrategy;
	private JsonScanner jsonScanner;
	private JsonPreferenceStore store;

	public JsonSourceViewerConfiguration(JsonTextEditor textEditor, JsonPreferenceStore store) {
		super();
		this.store = store;
		this.textEditor = textEditor;
		boolean spaces = store.getSpacesForTab();
		int numSpaces = store.getTabWidth();
		String lineEnding = getTextEditorLineEnding();
		jsonIndentLineAutoEditStrategy = new JsonIndentLineAutoEditStrategy(spaces, numSpaces, lineEnding);
		jsonScanner = new JsonScanner();
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler= new PresentationReconciler();
		DefaultDamagerRepairer dr= new DefaultDamagerRepairer(jsonScanner);
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
		jsonScanner.reinitScanner();
	}

	private String getTextEditorLineEnding() {
		IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);
		return LineEndingUtil.determineProjectLineEnding(file);
	}
}
