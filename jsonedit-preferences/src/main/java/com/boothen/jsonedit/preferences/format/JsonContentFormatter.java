package com.boothen.jsonedit.preferences.format;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategy;

/**
 * An eclipse content formatter that formats using a {@link JsonFormatStrategy}.
 */
public class JsonContentFormatter implements IContentFormatter, IContentFormatterExtension {

    private final JsonFormatStrategy strategy;

    /**
     * Takes a store that contains general text editor settings such as tab width, but also token-specifics.
     * @param store the store that provides the format settings
     */
    public JsonContentFormatter(IPreferenceStore store) {
        strategy = new JsonFormatStrategy(store);
    }

    @Override
    public void format(IDocument document, IRegion region) {
        IFormattingContext context = new FormattingContext();
        context.setProperty(FormattingContextProperties.CONTEXT_REGION, region);
        format(document, context);
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
