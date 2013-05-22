package com.boothen.jsonedit.core.handlers;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.core.editors.JsonPageEditor;
import com.boothen.jsonedit.core.editors.JsonTextEditor;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;
import com.boothen.jsonedit.text.JsonTextFormatter;

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
		if (!(editor instanceof JsonPageEditor)) {
			return null;
		}

		JsonTextEditor textEditor = (JsonTextEditor) ((JsonPageEditor) editor).getEditor();

		IPreferenceStore store = JsonEditorPlugin.getDefault().getPreferenceStore();
		boolean spaces = store.getBoolean(JsonPreferenceStore.SPACES_FOR_TABS);
		int numSpaces = store.getInt(JsonPreferenceStore.NUM_SPACES);

		formatText(textEditor, spaces, numSpaces);
		return null;
	}

	public static void formatText(JsonTextEditor textEditor, boolean spaces, int numSpaces) {


		IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		String text = document.get();

		IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);

		String lineEnding = null;
		if (file != null && file.getProject() != null) {
			System.out.println("project");
			lineEnding = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { new ProjectScope(file.getProject()) });
		}

		if (lineEnding == null) {
			lineEnding = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] {
					InstanceScope.INSTANCE});
		}

		if (lineEnding == null) {
			lineEnding = System.getProperty(Platform.PREF_LINE_SEPARATOR);
		}
		System.out.println(lineEnding.length());

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
