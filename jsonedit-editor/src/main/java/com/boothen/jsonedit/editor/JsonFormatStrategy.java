package com.boothen.jsonedit.editor;

import java.util.HashMap;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;

import com.boothen.jsonedit.antlr.JSONLexer;

/**
 * @author denis.mirochnik
 */
public class JsonFormatStrategy extends ContextBasedFormattingStrategy {

    private IDocument document;
    private Region region;

    public JsonFormatStrategy() {
    }

    @Override
    public void formatterStarts(IFormattingContext context) {
        super.formatterStarts(context);
        region = (Region) context.getProperty(FormattingContextProperties.CONTEXT_REGION);
        document = (IDocument) context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM);
    }

    @Override
    public void format() {

        String delimiter = TextUtilities.getDefaultLineDelimiter(document);

        HashMap<String, Object> map = new HashMap<String, Object>(); // TODO: fix this
        JsonFormatter formatter = new JsonFormatter(delimiter, map);

        String content = document.get();
        JSONLexer lexer = new JSONLexer(new ANTLRInputStream(content));
        String format = formatter.format(lexer);
        document.set(format);
    }

    @Override
    public void formatterStops() {
        super.formatterStops();
        document = null;
    }
}
