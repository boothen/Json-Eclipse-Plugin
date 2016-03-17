package com.boothen.jsonedit.preferences.format;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.JsonLog;

/**
 * @author denis.mirochnik
 */
public class JsonFormatStrategy extends ContextBasedFormattingStrategy {

    private IDocument document;
    private Region region;

    @Override
    public void formatterStarts(IFormattingContext context) {
        super.formatterStarts(context);
        region = (Region) context.getProperty(FormattingContextProperties.CONTEXT_REGION);
        document = (IDocument) context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM);
    }

    @Override
    public void format() {

        String delimiter = TextUtilities.getDefaultLineDelimiter(document);

        Map<String, Object> map = new HashMap<String, Object>(); // TODO: fix this
        map.put(JSONLexer.VOCABULARY.getSymbolicName(JSONLexer.BEGIN_OBJECT), JsonFormatter.Affix.NEWLINE);
        map.put(JSONLexer.VOCABULARY.getSymbolicName(JSONLexer.END_OBJECT), JsonFormatter.Affix.NEWLINE);

        JsonFormatter formatter = new JsonFormatter(delimiter, map);

        try {
            String content = document.get(region.getOffset(), region.getLength());
            JSONLexer lexer = new JSONLexer(new ANTLRInputStream(content));
            String format = formatter.format(lexer);
            document.replace(region.getOffset(), region.getLength(), format);
        } catch (BadLocationException e) {
            JsonLog.logError("Unable to format JSON region", e);
        }
    }

    @Override
    public void formatterStops() {
        super.formatterStops();
        document = null;
    }
}
