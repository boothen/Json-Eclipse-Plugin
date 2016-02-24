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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.outline.JsonContentOutlinePage;
import com.boothen.jsonedit.preferences.JsonPreferences;
import com.boothen.jsonedit.preferences.JsonPreferencesPlugin;

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

    public JsonTextEditor() {
        super();
        viewerConfiguration = new JsonSourceViewerConfiguration(this);
        setSourceViewerConfiguration(viewerConfiguration);
    }

    @Override
    protected void createActions() {
        super.createActions();

        Action action = new TextOperationAction(Messages.RESOURCE_BUNDLE, "Format.", this, ISourceViewer.FORMAT);
        action.setActionDefinitionId(Constants.FORMAT_ACTION_ID);
        setAction(Constants.FORMAT_ACTION_ID, action);
        markAsStateDependentAction(Constants.FORMAT_ACTION_ID, true);
        markAsSelectionDependentAction(Constants.FORMAT_ACTION_ID, true);
    }

    @Override
    protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
        support.setCharacterPairMatcher(pairsMatcher);
        support.setMatchingCharacterPainterPreferenceKeys(JsonPreferences.EDITOR_MATCHING_BRACKETS,
                JsonPreferences.EDITOR_MATCHING_BRACKETS_COLOR);
        super.configureSourceViewerDecorationSupport(support);
    }

    @Override
    protected void initializeKeyBindingScopes() {
        setKeyBindingScopes(new String[] { Constants.EDITOR_CONTEXT_ID });
    }

    @Override
    protected void initializeEditor() {
        super.initializeEditor();

        setEditorContextMenuId("#JsonTextEditorContext"); //$NON-NLS-1$
        setRulerContextMenuId("#JsonTextRulerContext"); //$NON-NLS-1$
        IPreferenceStore store = JsonPreferencesPlugin.getDefault().getPreferenceStore();
        setPreferenceStore(store);
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
        boolean autoFormatOnSave = store.getBoolean(JsonPreferences.AUTO_FORMAT_ON_SAVE);
        if (autoFormatOnSave) {
            new JsonFormatStrategy(this).format(); // TODO: consider storing this
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
                fOutlinePage = new JsonContentOutlinePage(this);
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

    public void updateFoldingStructure(List<Position> newPositions) {

        // this will hold the new annotations along with their corresponding positions
        Map<Annotation, Position> additionMap = new HashMap<>();
        List<Annotation> deletions = new ArrayList<>();

        // existing annotations will be removed so that only additions remain
        List<Position> additions = new ArrayList<>(newPositions);

        @SuppressWarnings("unchecked") // we can be sure that only Annotation instances are in the Iterable
        Iterator<Annotation> it = annotationModel.getAnnotationIterator();
        while (it.hasNext()) {
            Annotation anno = it.next();
            Position pos = annotationModel.getPosition(anno);
            if (!additions.remove(pos)) {
                // element is not present in the new list
                deletions.add(anno);
            }
        }

        for (Position pos : additions) {
            additionMap.put(new ProjectionAnnotation(), pos);
        }

        // trigger modification event only if either additions or deletions contains an element
        if (!deletions.isEmpty() || !additionMap.isEmpty()) {
            Annotation[] deletionsArray = deletions.toArray(new Annotation[deletions.size()]);
            annotationModel.modifyAnnotations(deletionsArray, additionMap, null);
        }
    }

    public JsonContentOutlinePage getFOutlinePage() {
        return fOutlinePage;
    }

    public void updateContentOutlinePage(JsonContext jsonContext) {
        if (fOutlinePage != null) {
            fOutlinePage.setInput(jsonContext);
        }
    }

    public void updateTabWidth(int tabWidth) {
        getSourceViewer().getTextWidget().setTabs(tabWidth);
    }
}
