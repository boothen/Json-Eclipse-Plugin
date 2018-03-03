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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.formatter.IContentFormatter;
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
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.preferences.JsonPreferences;
import com.boothen.jsonedit.model.ParseProblem;
import com.boothen.jsonedit.outline.JsonContentOutlinePage;

/**
 * JsonTextEditor is the TextEditor instance used by the plugin.
 *
 * @author Matt Garner
 *
 */
public class JsonTextEditor extends TextEditor {

    /**
     * The action ID to trigger re-formatting the selected code
     */
    static final String FORMAT_ACTION_ID = "com.boothen.jsonedit.format"; //$NON-NLS-1$

    /**
     * The action ID to open the Quick Outline View
     */
    static final String QUICK_OUTLINE_ACTION_ID = "com.boothen.jsonedit.quickOutline"; //$NON-NLS-1$

    /**
     * The editor context ID wrt. to the key binding scope
     */
    static final String EDITOR_CONTEXT_ID = "com.boothen.jsonedit.jsonEditorScope"; //$NON-NLS-1$

    /**
     * The marker ID for JSON (e.g. warnings, compile errors)
     */
    static final String MARKER_ID = "com.boothen.jsonedit.validation.marker";

    /**
     * The document category for JSON files
     */
    static final String JSON_CATEGORY = "__json_elements"; //$NON-NLS-1$

    /**
     * Text operation code for requesting the outline for the current input.
     */
    private static final int SHOW_OUTLINE = 51;

    private final static char[] PAIRS = { '{', '}', '[', ']' };

    private DefaultCharacterPairMatcher pairsMatcher = new DefaultCharacterPairMatcher(PAIRS);
    private JsonSourceViewerConfiguration viewerConfiguration;
    private RangeHighlighter rangeHighlighter = new RangeHighlighter(this);
    private JsonContentOutlinePage fOutlinePage;

    private ProjectionAnnotationModel annotationModel;

    @Override
    public void createPartControl(Composite parent) {

        super.createPartControl(parent);

        ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();
        ProjectionSupport projectionSupport = new ProjectionSupport(viewer,getAnnotationAccess(),getSharedColors());
        projectionSupport.install();

        // turn projection mode on
        viewer.doOperation(ProjectionViewer.TOGGLE);

        annotationModel = viewer.getProjectionAnnotationModel();

        SourceViewerDecorationSupport support = getSourceViewerDecorationSupport(viewer);
        support.install(getPreferenceStore());

        getSite().getPage().addPostSelectionListener(rangeHighlighter);
    }

    @Override
    protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
        // A projection source viewer is a source viewer which supports multiple
        // visible regions which can dynamically be changed (aka document folding).
        return new JsonSourceViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);
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
        setKeyBindingScopes(new String[] { EDITOR_CONTEXT_ID });
    }

    @Override
    protected void initializeEditor() {
        super.initializeEditor();

        setEditorContextMenuId("#JsonTextEditorContext"); //$NON-NLS-1$
        setRulerContextMenuId("#JsonTextRulerContext"); //$NON-NLS-1$

        // chain default preference store to get reasonable default values (e.g. for annotations)
        IPreferenceStore store = getPreferenceStore();
        IPreferenceStore myStore = JsonCorePlugin.getDefault().getPreferenceStore();
        setPreferenceStore(new ChainedPreferenceStore(new IPreferenceStore[] { store, myStore }));

        // set source viewer configuration before TextEditor constructor is called
        viewerConfiguration = new JsonSourceViewerConfiguration(this, getPreferenceStore());
        setSourceViewerConfiguration(viewerConfiguration);
    }

    @Override
    protected void createActions() {
        super.createActions();

        Action outline = new TextOperationAction(Messages.RESOURCE_BUNDLE, "ShowOutline.", this, SHOW_OUTLINE, true);
        outline.setActionDefinitionId(QUICK_OUTLINE_ACTION_ID);
        setAction(QUICK_OUTLINE_ACTION_ID, outline);

        Action format = new TextOperationAction(Messages.RESOURCE_BUNDLE, "Format.", this, ISourceViewer.FORMAT);
        format.setActionDefinitionId(FORMAT_ACTION_ID);
        setAction(FORMAT_ACTION_ID, format);
        markAsStateDependentAction(FORMAT_ACTION_ID, true);
        markAsSelectionDependentAction(FORMAT_ACTION_ID, true);
    }

    @Override
    protected void editorContextMenuAboutToShow(IMenuManager menu) {
        super.editorContextMenuAboutToShow(menu);

        addAction(menu, ITextEditorActionConstants.GROUP_EDIT, FORMAT_ACTION_ID);
        addAction(menu, ITextEditorActionConstants.GROUP_OPEN, QUICK_OUTLINE_ACTION_ID);
    }

    @Override
    public void dispose() {
        if (fOutlinePage != null) {
            Map<ParseTree, ParseTree> oldToNew = Collections.emptyMap();
            Map<ParseTree, Position> positions = Collections.emptyMap();
            fOutlinePage.setInput(null, oldToNew, positions);
        }

        if (pairsMatcher != null) {
            pairsMatcher.dispose();
            pairsMatcher = null;
        }

        getSite().getPage().removePostSelectionListener(rangeHighlighter);

        super.dispose();
    }

    @Override
    public void doRevertToSaved() {
        super.doRevertToSaved();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        doAutoFormatOnSave();
        super.doSave(monitor);
    }

    @Override
    public void doSaveAs() {
        doAutoFormatOnSave();
        super.doSaveAs();
    }

    private void doAutoFormatOnSave() {
        IPreferenceStore store = getPreferenceStore();
        boolean autoFormatOnSave = store.getBoolean(JsonPreferences.AUTO_FORMAT_ON_SAVE);
        if (autoFormatOnSave) {
            ISourceViewer viewer = getSourceViewer();
            IContentFormatter formatter = viewerConfiguration.getContentFormatter(viewer);
            IDocument document = viewer.getDocument();
            formatter.format(document, null);
        }
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
            return getOutlinePage();
        }

        return super.getAdapter(required);
    }

    @Override
    protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
        super.handlePreferenceStoreChanged(event);

        // TODO: invalidate only when coloring scheme changes
        getSourceViewer().invalidateTextPresentation();
    }

    public void updateDocumentPositions(Collection<Position> positions) {
        IDocument doc = getSourceViewer().getDocument();
        try {
            if (doc.containsPositionCategory(JSON_CATEGORY)) {
                doc.removePositionCategory(JSON_CATEGORY);
            }
            doc.addPositionCategory(JSON_CATEGORY);
            for (Position pos : positions) {
                doc.addPosition(JSON_CATEGORY, pos);
            }
        } catch (BadPositionCategoryException | BadLocationException e) {
            Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.toString(), e);
            StatusManager.getManager().handle(status);
        }

    }

    public void updateFoldingStructure(List<Position> newPositions) {

        // this will hold the new annotations along with their corresponding positions
        Map<Annotation, Position> additionMap = new HashMap<>();
        List<Annotation> deletions = new ArrayList<>();

        // existing annotations will be removed so that only additions remain
        List<Position> additions = new ArrayList<>(newPositions);

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

    public void updateSyntaxTree(JsonContext jsonContext, Map<ParseTree, ParseTree> oldToNew,
            Map<ParseTree, Position> positions) {
        getOutlinePage().setInput(jsonContext, oldToNew, positions);
        rangeHighlighter.setInput(jsonContext, positions);
    }

    public void updateTabWidth(int tabWidth) {
        getSourceViewer().getTextWidget().setTabs(tabWidth);
    }

    private JsonContentOutlinePage getOutlinePage() {
        if (fOutlinePage == null) {
            fOutlinePage = new JsonContentOutlinePage(this);
        }
        return fOutlinePage;
    }

    public void updateProblemMarkers(List<ParseProblem> problems) {
        IResource resource = ResourceUtil.getResource(getEditorInput());
        if (resource != null) {
            try {
                IDocument doc = getSourceViewer().getDocument();
                updateMarkers(doc, resource, problems);
            } catch (CoreException e) {
                StatusManager.getManager().handle(e.getStatus());
            }
        }
    }

    private static void updateMarkers(IDocument doc, IResource res, List<ParseProblem> probs) throws CoreException {

        res.deleteMarkers(MARKER_ID, false, 0);

        for (final ParseProblem problem : probs) {
            try {
                IMarker marker = res.createMarker(MARKER_ID);

                int offset = doc.getLineOffset(problem.getLine() - 1);
                marker.setAttribute(IMarker.SEVERITY, problem.getSeverity().getMarkerValue());
                marker.setAttribute(IMarker.LOCATION, "Line " + problem.getLine());
                marker.setAttribute(IMarker.MESSAGE, problem.getMessage());
                marker.setAttribute(IMarker.CHAR_START, offset + problem.getStartPositionInLine());
                marker.setAttribute(IMarker.CHAR_END, offset + problem.getEndPositionInLine());
            } catch (BadLocationException e) {
                Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Invalid position", e);
                StatusManager.getManager().handle(status);
            }
        }
    }
}
