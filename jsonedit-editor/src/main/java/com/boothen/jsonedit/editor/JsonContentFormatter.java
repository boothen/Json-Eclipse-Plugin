package com.boothen.jsonedit.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategy;

/**
 * @author denis.mirochnik
 */
public class JsonContentFormatter implements IContentFormatter, IContentFormatterExtension
{
    private final JsonFormatStrategy mStrategy;

    public JsonContentFormatter(JsonTextEditor editor)
    {
        mStrategy = new JsonFormatStrategy(editor);
    }

    @Override
    public void format(IDocument document, IRegion region)
    {
    }

    @Override
    public IFormattingStrategy getFormattingStrategy(String contentType)
    {
        if (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType))
        {
            return mStrategy;
        }

        return null;
    }

    @Override
    public void format(IDocument document, IFormattingContext context)
    {
        context.setProperty(FormattingContextProperties.CONTEXT_MEDIUM, document);

        mStrategy.formatterStarts(context);
        mStrategy.format();
        mStrategy.formatterStops();
    }
}
