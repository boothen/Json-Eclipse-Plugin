package com.boothen.jsonedit.editor;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.model.AntlrAdapter;
import com.boothen.jsonedit.model.ParseResult;

/**
 * Computes a JSON syntax tree (e.g. for the quick outline view)
 */
class JsonInformationProvider implements IInformationProvider, IInformationProviderExtension {

    @Override
    public IRegion getSubject(ITextViewer textViewer, int offset) {
        return new Region(offset, 0);
    }

    @Override
    public String getInformation(ITextViewer textViewer, IRegion subject) {
        // not used, since this class also implements IInformationProviderExtension
        return String.valueOf(getInformation2(textViewer, subject));
    }

    @Override
    public Object getInformation2(ITextViewer textViewer, IRegion subject) {
        // subject is currently ignored
        try {
            String content = textViewer.getDocument().get();
            Reader reader = new StringReader(content);
            ParseResult result = AntlrAdapter.convert(reader);
            JsonContext syntaxTree = result.getTree();

            return syntaxTree;
        } catch (IOException e) {
            JsonLog.logError("Could not compute information", e);
            return null;
        }
    }
}
