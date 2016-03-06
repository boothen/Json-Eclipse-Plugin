package com.boothen.jsonedit.preferences.format;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.model.AntlrAdapter;

/**
 * @author denis.mirochnik
 */
public class TextFormatter {
    private final TextViewer mViewer;
    private final IPreferenceStore mStore;
    private JsonContext mElement;

    public TextFormatter(TextViewer viewer, IPreferenceStore store) {
        mViewer = viewer;
        mStore = store;

        try {
            String jsonText = "{ \"key\" : \"value\"}";
            Reader reader = new StringReader(jsonText);
            mElement = AntlrAdapter.convert(reader).getTree();
        } catch (IOException e) {
            // TODO: handle exception with status handler
            mElement = null;
        }
    }

    public void update() {
        final IDocument document = mViewer.getDocument();
        final int length = document == null ? 0 : document.getLength();
        final String format = "TODO"; //new JsonFormatter(mElement, mStore).format(length);

        if (document != null) {
            try {
                document.replace(0, length, format);
            } catch (final BadLocationException e) {
                //                String text = "Attempting to access a non-existing position";
                // TODO: handle exception
            }
        } else {
            mViewer.setDocument(new Document(format));
        }
    }
}
