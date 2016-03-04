package com.boothen.jsonedit.editor;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.statushandlers.StatusManager;

import com.boothen.jsonedit.model.ParseError;

/**
 * TODO: describe
 */
public class DocumentValidator {

    public static String MARKER_ID = "com.boothen.jsonedit.validation.marker";

    public static void refresh(IDocument doc, IResource resource, List<ParseError> errors) throws CoreException {

        resource.deleteMarkers(MARKER_ID, false, 0);

        for (final ParseError parseError : errors)
        {
            try {
                final IMarker marker = resource.createMarker(MARKER_ID);

                int offset = doc.getLineOffset(parseError.getLine() - 1) + parseError.getCharPositionInLine();
                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                marker.setAttribute(IMarker.LOCATION, "Line " + parseError.getLine());
                marker.setAttribute(IMarker.MESSAGE, parseError.getMessage());
                marker.setAttribute(IMarker.CHAR_START, offset);
                marker.setAttribute(IMarker.CHAR_END, offset + 1);
            } catch (BadLocationException e) {
                Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Invalid position", e);
                StatusManager.getManager().handle(status);
            }
        }

    }
}
