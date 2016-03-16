package com.boothen.jsonedit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategy;

/**
 * TODO: describe
 */
public class JsonContentFormatter implements IContentFormatter, IContentFormatterExtension {

    private final JsonFormatStrategy strategy;

    public JsonContentFormatter() {
        strategy = new JsonFormatStrategy();
    }

    @Override
    public void format(IDocument document, IRegion region) {
        throw new UnsupportedOperationException("Not implemented in favor of the IContentFormatterExtension");
    }

    @Override
    public void format(IDocument document, IFormattingContext context) {
        context.setProperty(FormattingContextProperties.CONTEXT_MEDIUM, document);
        strategy.formatterStarts(context);
        strategy.format();
        strategy.formatterStops();
    }

    @Override
    public IFormattingStrategy getFormattingStrategy(String contentType) {
        if (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType)) {
            return strategy;
        }

        return null;
    }

}
