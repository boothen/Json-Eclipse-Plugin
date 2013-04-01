package org.sourceforge.jsonedit.core.editors;

import java.util.HashMap;
import java.util.List;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.sourceforge.jsonedit.core.JsonEditorPlugin;
import org.sourceforge.jsonedit.core.model.jsonnode.JsonNode;
import org.sourceforge.jsonedit.core.outline.JsonContentOutlinePage;

/**
 * JsonTextEditor is the TextEditor instance used by the plugin.
 * 
 * @author Matt Garner
 *
 */
public class JsonTextEditor extends TextEditor {
	
	private JsonSourceViewerConfiguration viewerConfiguration;
	
	protected final static char[] PAIRS= { '{', '}', '[', ']' };
	
	private DefaultCharacterPairMatcher pairsMatcher = new DefaultCharacterPairMatcher(PAIRS);

	
	/** The outline page */
	private JsonContentOutlinePage fOutlinePage;
	
	public JsonTextEditor() {
		super();
		setDocumentProvider(new TextFileDocumentProvider());
	}
	
	@Override
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		support.setCharacterPairMatcher(pairsMatcher);
		
		support.setMatchingCharacterPainterPreferenceKeys(JsonEditorPlugin.EDITOR_MATCHING_BRACKETS, JsonEditorPlugin.EDITOR_MATCHING_BRACKETS_COLOR);
		super.configureSourceViewerDecorationSupport(support);
	}

	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId("#JsonTextEditorContext"); //$NON-NLS-1$
		setRulerContextMenuId("#JsonTextRulerContext"); //$NON-NLS-1$
		viewerConfiguration = new JsonSourceViewerConfiguration(this);
		setSourceViewerConfiguration(viewerConfiguration);
	}
	
	public void dispose() {
		if (fOutlinePage != null)
			fOutlinePage.setInput(null);
		
		if (pairsMatcher != null) {
			pairsMatcher.dispose();
			pairsMatcher = null;
		}

		super.dispose();
	}
	
	public void doRevertToSaved() {
		super.doRevertToSaved();
		if (fOutlinePage != null)
			fOutlinePage.update();
	}
	
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		if (fOutlinePage != null)
			fOutlinePage.update();
	}
	
	/** The <code>JavaEditor</code> implementation of this 
	 * <code>AbstractTextEditor</code> method performs any extra 
	 * save as behavior required by the java editor.
	 */
	public void doSaveAs() {
		super.doSaveAs();
		if (fOutlinePage != null)
			fOutlinePage.update();
	}
	
	/** The <code>JavaEditor</code> implementation of this 
	 * <code>AbstractTextEditor</code> method performs sets the 
	 * input of the outline page after AbstractTextEditor has set input.
	 * 
	 * @param input the editor input
	 * @throws CoreException in case the input can not be set
	 */ 
	public void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		if (fOutlinePage != null)
			fOutlinePage.setInput(input);
	}
	
	/** The <code>JavaEditor</code> implementation of this 
	 * <code>AbstractTextEditor</code> method performs gets
	 * the java content outline page if request is for a an 
	 * outline page.
	 * 
	 * @param required the required type
	 * @return an adapter for the required type or <code>null</code>
	 */ 
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (fOutlinePage == null) {
				fOutlinePage= new JsonContentOutlinePage(getDocumentProvider(), this);
				if (getEditorInput() != null)
					fOutlinePage.setInput(getEditorInput());
			}
			return fOutlinePage;
		}
		
		return super.getAdapter(required);
	}
	
	private ProjectionAnnotationModel annotationModel;
	private ProjectionSupport projectionSupport;
	private Annotation[] oldAnnotations;
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createPartControl(parent);
		ProjectionViewer viewer =(ProjectionViewer)getSourceViewer();
        
        projectionSupport = new ProjectionSupport(viewer,getAnnotationAccess(),getSharedColors());
		projectionSupport.install();
		
		//turn projection mode on
		viewer.doOperation(ProjectionViewer.TOGGLE);
		
		annotationModel = viewer.getProjectionAnnotationModel();
		
		SourceViewerDecorationSupport support = getSourceViewerDecorationSupport(viewer);
		support.install(JsonEditorPlugin.getJsonPreferenceStore());
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {

		ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);

		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);
		return viewer;
	}
	
	public void updateFoldingStructure(List<Position> positions)
	{
		Annotation[] annotations = new Annotation[positions.size()];
		
		//this will hold the new annotations along
		//with their corresponding positions
		HashMap<ProjectionAnnotation, Position> newAnnotations = new HashMap<ProjectionAnnotation, Position>();
		
		for(int i =0;i<positions.size();i++)
		{
			ProjectionAnnotation annotation = new ProjectionAnnotation();
			newAnnotations.put(annotation,positions.get(i));
			annotations[i]=annotation;
		}
		
		annotationModel.modifyAnnotations(oldAnnotations,newAnnotations,null);
		
		oldAnnotations=annotations;
	}

	public JsonContentOutlinePage getFOutlinePage() {
		return fOutlinePage;
	}
	
	public void updateContentOutlinePage(List<JsonNode> jsonNodes) {
		if (fOutlinePage != null) {
			fOutlinePage.setJsonNodes(jsonNodes);
		}
	}
}
