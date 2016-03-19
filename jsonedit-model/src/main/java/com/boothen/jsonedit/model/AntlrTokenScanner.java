package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.JsonLog;

/**
 * Use the ANTLR lexer to extract tokens from a document.
 */
public class AntlrTokenScanner implements ITokenScanner {

    private int offset;
    private Lexer lexer;
    private CommonToken previous;
    private CommonToken current;
    private TokenMapping tokenMapping;

    public AntlrTokenScanner() {
        this(new JSONLexer(null));
    }

    public AntlrTokenScanner(Lexer lexer) {
        this.lexer = lexer;
        this.tokenMapping = new TokenMapping() {
            @Override
            public Object apply(int currentTokenType, int previousTokenType) {
                return null;
            }
        };
    }

    public AntlrTokenScanner(Lexer lexer, TokenMapping mapping) {
        this.lexer = lexer;
        this.tokenMapping = mapping;
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

        previous = current;

        if (!(token instanceof CommonToken)) {
            current = null;
            return org.eclipse.jface.text.rules.Token.UNDEFINED;
        }

        current = (CommonToken) token;

        if (token.getType() == Token.EOF) {
            return org.eclipse.jface.text.rules.Token.EOF;
        }

        if (token.getType() == JSONLexer.WS) {
            return org.eclipse.jface.text.rules.Token.WHITESPACE;
        }

        int currentType = current.getType();
        int previousType = previous != null ? previous.getType() : Token.EOF;
        Object data = tokenMapping.apply(currentType, previousType);

        return new org.eclipse.jface.text.rules.Token(data);
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
