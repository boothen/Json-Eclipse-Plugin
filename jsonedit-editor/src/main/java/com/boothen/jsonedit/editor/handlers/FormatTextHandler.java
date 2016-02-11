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
package com.boothen.jsonedit.editor.handlers;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.boothen.jsonedit.editor.JsonTextEditor;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.JsonTextFormatter;
import com.boothen.jsonedit.text.LineEndingUtil;

/**
 * Handler for the format text command. Configured in the plugin.xml
 *
 * @author Matt Garner
 *
 */
public class FormatTextHandler implements IHandler {


    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {

    }

    @Override
    public void dispose() {

    }

    /**
     * Execute the text formatting request.
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        IEditorPart editor = HandlerUtil.getActiveEditor(event);
        if (!(editor instanceof JsonTextEditor)) {
            return null;
        }

        JsonTextEditor textEditor = (JsonTextEditor) editor;

        JsonPreferenceStore store = new JsonPreferenceStore();
        boolean spaces = store.getSpacesForTab();
        int numSpaces = store.getTabWidth();

        formatText(textEditor, spaces, numSpaces);
        return null;
    }

    public static void formatText(JsonTextEditor textEditor, boolean spaces, int numSpaces) {

        IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
        String text = document.get();

        IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);
        String lineEnding = LineEndingUtil.determineProjectLineEnding(file);

        JsonTextFormatter textFormatter = new JsonTextFormatter(text, spaces, numSpaces, lineEnding);
        textEditor.storeOutlineState();
        textEditor.storeTextLocation();
        textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput()).set(textFormatter.formatText());
        textEditor.getFOutlinePage().update();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isHandled() {
        return true;
    }

    @Override
    public void removeHandlerListener(IHandlerListener handlerListener) {

    }
}
