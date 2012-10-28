package org.sourceforge.jsonedit.core.handlers;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sourceforge.jsonedit.core.editors.JsonPageEditor;
import org.sourceforge.jsonedit.core.editors.JsonTextEditor;
import org.sourceforge.jsonedit.core.text.JsonTextFormatter;

/**
 * Handler for the format text command. Configured in the plugin.xml
 *
 * @author Matt Garner
 *
 */
public class FormatTextHandler implements IHandler {



	public void addHandlerListener(IHandlerListener handlerListener) {

	}


	public void dispose() {

	}

	/**
	 * Execute the text formatting request.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (!(editor instanceof JsonPageEditor)) {
			return null;
		}

		JsonTextEditor textEditor = (JsonTextEditor) ((JsonPageEditor) editor).getEditor();
		String text = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput()).get();

		JsonTextFormatter textFormatter = new JsonTextFormatter(text);

		textEditor.storeOutlineState();
		textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput()).set(textFormatter.formatText());
		textEditor.getFOutlinePage().update();

		return null;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isHandled() {
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {

	}

}
