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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.editor.JsonTextEditor;
import com.boothen.jsonedit.preferences.JsonPreferenceInitializer;
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

        formatText(textEditor);
        return null;
    }

    public static void formatText(JsonTextEditor textEditor) {

        IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
        String text = document.get();

        IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);
        String lineEnding = LineEndingUtil.determineProjectLineEnding(file);

        IPreferenceStore store = JsonEditorPlugin.getDefault().getPreferenceStore();
        boolean spaces = store.getBoolean(JsonPreferenceInitializer.SPACES_FOR_TABS);
        int numSpaces = store.getInt(JsonPreferenceInitializer.NUM_SPACES);

        JsonTextFormatter textFormatter = new JsonTextFormatter(text, spaces , numSpaces, lineEnding);
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
