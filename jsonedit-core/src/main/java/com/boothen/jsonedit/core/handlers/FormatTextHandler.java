package com.boothen.jsonedit.core.handlers;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.boothen.jsonedit.core.editors.JsonPageEditor;
import com.boothen.jsonedit.core.editors.JsonTextEditor;
import com.boothen.jsonedit.core.text.JsonTextFormatter;

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
		String text = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput()).get();

		JsonTextFormatter textFormatter = new JsonTextFormatter(text);

		textEditor.storeOutlineState();
		textEditor.storeTextLocation();
		textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput()).set(textFormatter.formatText());
		textEditor.getFOutlinePage().update();
		return null;
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
