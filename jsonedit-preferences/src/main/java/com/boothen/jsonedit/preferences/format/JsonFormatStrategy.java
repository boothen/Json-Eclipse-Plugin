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
import com.boothen.jsonedit.core.preferences.JsonPreferences;

/**
 * A formatting strategy that extract information from the formatting context and delegates
 * the formatting to a {@link JsonFormatter}.
 * <p>
 * Appends a trailing newline, if {@link JsonPreferences#EDITOR_TRAILING_NEWLINE} is set.
 */
public class JsonFormatStrategy extends ContextBasedFormattingStrategy {

    private final IPreferenceStore store;
    private IDocument document;
    private Region region;

    /**
     * @param store the store that defines the format style
     */
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
            int endOffset = region.getOffset() + region.getLength();
            // must be computed before the document is changed (formatted)
            boolean endIncluded = (endOffset == document.getLength());

            int lineStartOffset = getLineStartOffset(region.getOffset());
            int lineEndOffset = getLineEndOffset(endOffset);

            String content = document.get(0, lineEndOffset);
            JSONLexer lexer = new JSONLexer(new ANTLRInputStream(content));
            lexer.removeErrorListeners();

            String format = formatter.format(lineStartOffset, lexer);
            document.replace(lineStartOffset, lineEndOffset - lineStartOffset, format);

            boolean appendNewline = store.getBoolean(JsonPreferences.EDITOR_TRAILING_NEWLINE);
            if (appendNewline && endIncluded) {
                char lastChar = format.charAt(format.length() - 1);
                // not necessarily the usual delimiter - checking "\n" covers "\r\n" endings as well
                if (lastChar != '\n') {
                    // document end index was changed by the replace op. so get it again
                    document.replace(document.getLength(), 0, delimiter);
                }
            }
        } catch (BadLocationException e) {
            JsonLog.logError("Unable to format JSON region", e);
        }
    }

    private int getLineStartOffset(int offset) throws BadLocationException {
        return document.getLineInformationOfOffset(offset).getOffset();
    }

    private int getLineEndOffset(int offset) throws BadLocationException {
        int line = document.getLineOfOffset(offset);
        // getLineLength() includes the line delimiter in contrast to getLineInformation()
        return document.getLineOffset(line) + document.getLineLength(line);
    }

    @Override
    public void formatterStops() {
        super.formatterStops();
        document = null;
        region = null;
    }
}
