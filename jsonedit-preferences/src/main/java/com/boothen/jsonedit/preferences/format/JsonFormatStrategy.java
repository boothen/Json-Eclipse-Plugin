package com.boothen.jsonedit.preferences.format;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.eclipse.jface.preference.IPreferenceStore;
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
    private IPreferenceStore store;

    public JsonFormatStrategy(IPreferenceStore store) {
        this.store = store;
    }

    @Override
    public void formatterStarts(IFormattingContext context) {
        super.formatterStarts(context);
        region = (Region) context.getProperty(FormattingContextProperties.CONTEXT_REGION);
        document = (IDocument) context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM);
        if (region == null) {
            region = new Region(0, document.getLength());
        }
    }

    @Override
    public void format() {

        String delimiter = TextUtilities.getDefaultLineDelimiter(document);
        JsonFormatter formatter = new JsonFormatter(delimiter, store);

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
