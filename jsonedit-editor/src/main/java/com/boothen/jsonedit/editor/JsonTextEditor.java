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

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.internal.resources.PreferenceInitializer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.editor.handlers.FormatTextHandler;
import com.boothen.jsonedit.outline.JsonContentOutlinePage;
import com.boothen.jsonedit.preferences.JsonPreferenceInitializer;

/**
 * JsonTextEditor is the TextEditor instance used by the plugin.
 *
 * @author Matt Garner
 *
 */
public class JsonTextEditor extends TextEditor {

    private final static char[] PAIRS= { '{', '}', '[', ']' };

    private DefaultCharacterPairMatcher pairsMatcher = new DefaultCharacterPairMatcher(PAIRS);
    private JsonSourceViewerConfiguration viewerConfiguration;
    private JsonContentOutlinePage fOutlinePage;


    private ProjectionAnnotationModel annotationModel;
    private ProjectionAnnotation[] oldAnnotations;
    private boolean[] annotationCollapsedState;
    private boolean restoreCursorLocation = false;
    private int nodePositionOffset = 0;
    private int nodePosition = 0;
    private List<JsonNode> jsonNodes;

    public JsonTextEditor() {
        super();
        viewerConfiguration = new JsonSourceViewerConfiguration(this);
        setSourceViewerConfiguration(viewerConfiguration);
//        setDocumentProvider(new JsonDocumentProvider());
    }

    @Override
    protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
        support.setCharacterPairMatcher(pairsMatcher);
        support.setMatchingCharacterPainterPreferenceKeys(JsonPreferenceInitializer.EDITOR_MATCHING_BRACKETS,
                JsonPreferenceInitializer.EDITOR_MATCHING_BRACKETS_COLOR);
        super.configureSourceViewerDecorationSupport(support);
    }


    @Override
    protected void initializeEditor() {
        super.initializeEditor();

        setEditorContextMenuId("#JsonTextEditorContext"); //$NON-NLS-1$
        setRulerContextMenuId("#JsonTextRulerContext"); //$NON-NLS-1$
        setPreferenceStore(JsonEditorPlugin.getDefault().getPreferenceStore());

    }

    @Override
    public void dispose() {
        if (fOutlinePage != null)
            fOutlinePage.setInput(null);

        if (pairsMatcher != null) {
            pairsMatcher.dispose();
            pairsMatcher = null;
        }

        super.dispose();
    }

    @Override
    public void doRevertToSaved() {
        super.doRevertToSaved();
        if (fOutlinePage != null)
            fOutlinePage.update();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        doAutoFormatOnSave();
        super.doSave(monitor);
        if (fOutlinePage != null)
            fOutlinePage.update();
    }

    /** The <code>JavaEditor</code> implementation of this
     * <code>AbstractTextEditor</code> method performs any extra
     * save as behavior required by the java editor.
     */
    @Override
    public void doSaveAs() {
        doAutoFormatOnSave();
        super.doSaveAs();
        if (fOutlinePage != null)
            fOutlinePage.update();
    }

    private void doAutoFormatOnSave() {
        IPreferenceStore store = getPreferenceStore();
        boolean autoFormatOnSave = store.getBoolean(JsonPreferenceInitializer.AUTO_FORMAT_ON_SAVE);
        if (autoFormatOnSave) {
            FormatTextHandler.formatText(this);
        }
    }

    /** The <code>JavaEditor</code> implementation of this
     * <code>AbstractTextEditor</code> method performs sets the
     * input of the outline page after AbstractTextEditor has set input.
     *
     * @param input the editor input
     * @throws CoreException in case the input can not be set
     */
    @Override
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
    @Override
    public Object getAdapter(@SuppressWarnings("rawtypes") Class required) {
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

    @Override
    public void createPartControl(Composite parent) {

        super.createPartControl(parent);

        ProjectionViewer viewer =(ProjectionViewer) getSourceViewer();
        ProjectionSupport projectionSupport = new ProjectionSupport(viewer,getAnnotationAccess(),getSharedColors());
        projectionSupport.install();

        //turn projection mode on
        viewer.doOperation(ProjectionViewer.TOGGLE);

        annotationModel = viewer.getProjectionAnnotationModel();

        SourceViewerDecorationSupport support = getSourceViewerDecorationSupport(viewer);
        support.install(getPreferenceStore());
    }

    @Override
    protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {

        fAnnotationAccess = getAnnotationAccess();
        fOverviewRuler = createOverviewRuler(getSharedColors());

        ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);
        // ensure decoration support has been created and configured.
        getSourceViewerDecorationSupport(viewer);
        return viewer;
    }

    @Override
    protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
        ((JsonSourceViewerConfiguration) viewerConfiguration).handlePreferenceStoreChanged();
        super.handlePreferenceStoreChanged(event);
    }

    public void updateFoldingStructure(List<Position> positions)
    {
        ProjectionAnnotation[] annotations = new ProjectionAnnotation[positions.size()];

        //this will hold the new annotations along
        //with their corresponding positions
        HashMap<ProjectionAnnotation, Position> newAnnotations = new HashMap<ProjectionAnnotation, Position>();

        for (int i = 0; i < positions.size(); i++)
        {
            ProjectionAnnotation annotation = new ProjectionAnnotation();
            newAnnotations.put(annotation,positions.get(i));
            annotations[i] = annotation;
            if (annotationCollapsedState != null && annotationCollapsedState.length > i && annotationCollapsedState[i]) {
                annotation.markCollapsed();
            }
        }

        annotationModel.modifyAnnotations(oldAnnotations,newAnnotations,null);
        oldAnnotations = annotations;
    }

    public JsonContentOutlinePage getFOutlinePage() {
        return fOutlinePage;
    }

    public void updateContentOutlinePage(List<JsonNode> jsonNodes) {
        this.jsonNodes = jsonNodes;
        if (fOutlinePage != null) {
            fOutlinePage.setJsonNodes(jsonNodes);
        }

        restoreTextLocation();
    }

    public void storeOutlineState() {
        if (oldAnnotations != null) {
            annotationCollapsedState = new boolean[oldAnnotations.length];
            for (int i = 0; i < oldAnnotations.length; i++) {
                annotationCollapsedState[i] = oldAnnotations[i].isCollapsed();
            }
        }
    }

    public void storeTextLocation() {

        ITextSelection iTextSelection = (ITextSelection) this.getSite().getSelectionProvider().getSelection();
        int textLocation = iTextSelection.getOffset();
        if (jsonNodes != null) {
            for (int i = 0; i < jsonNodes.size(); i++) {
                JsonNode jsonNode = jsonNodes.get(i);
                if (jsonNode.containsLocation(textLocation)) {
                    nodePosition = i;
                    nodePositionOffset = textLocation - jsonNode.getStart();
                    break;
                }
            }
        }

        restoreCursorLocation = true;
    }

    public void restoreTextLocation() {

        if (!restoreCursorLocation) {
            return;
        }

        restoreCursorLocation = false;

        ITextOperationTarget target = (ITextOperationTarget) this.getAdapter(ITextOperationTarget.class);
        if (!(target instanceof ITextViewer)) {
            return ;
        }
        ITextViewer textViewer = (ITextViewer) target;
        if (jsonNodes != null && jsonNodes.size() > nodePosition) {
            JsonNode node = jsonNodes.get(nodePosition);
            if (node != null) {
                int textLocation = node.getStart() + nodePositionOffset;
                textViewer.getTextWidget().setSelection(textLocation);
            }
        }
    }

    public void updateTabWidth(int tabWidth) {
        getSourceViewer().getTextWidget().setTabs(tabWidth);
    }
}
