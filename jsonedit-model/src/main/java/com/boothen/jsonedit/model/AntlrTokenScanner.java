package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.JsonColorProvider;
import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.core.JsonLog;

/**
 * Use the ANTLR lexer to extract tokens from a document.
 */
public class AntlrTokenScanner implements ITokenScanner {

    private int offset;
    private JSONLexer lexer;
    private CommonToken previous;
    private CommonToken current;

    public AntlrTokenScanner() {
        this(new JSONLexer(null));
    }

    public AntlrTokenScanner(JSONLexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void setRange(IDocument document, int offset, int length) {
        this.offset = offset;
        try {
            String text = document.get(offset, length);
            lexer.setInputStream(new ANTLRInputStream(text));
        } catch (BadLocationException e) {
            JsonLog.logError("Attempting to access a non-existing position", e);
        }
    }

    @Override
    public IToken nextToken() {
        Token token = lexer.nextToken();

        if (token.getType() == Token.EOF) {
            return org.eclipse.jface.text.rules.Token.EOF;
        }

        if (!(token instanceof CommonToken)) {
            return org.eclipse.jface.text.rules.Token.UNDEFINED;
        }

        previous = current;
        current = (CommonToken) token;

        RGB rgb = new RGB(0, 0, 0);

        int tokenTypeIndex = token.getType();
        switch (tokenTypeIndex) {
            case JSONLexer.STRING:
                if (previous != null && previous.getType() == JSONLexer.COLON) {
                    rgb = new RGB(0, 0, 128);
                } else {
                    rgb = new RGB(0, 128, 0);
                }
                break;

            case JSONLexer.NUMBER:
                rgb = new RGB(128, 0, 128);
                break;
        }

        JsonColorProvider colorProvider = JsonEditorPlugin.getColorProvider();
        TextAttribute attribute = new TextAttribute(colorProvider.getColor(rgb));

        return new org.eclipse.jface.text.rules.Token(attribute);
    }

    @Override
    public int getTokenOffset() {
        return offset + current.getStartIndex();
    }

    @Override
    public int getTokenLength() {
        return current.getStopIndex() - current.getStartIndex() + 1;
    }
}
