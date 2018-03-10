package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.core.preferences.TokenStyle;

/**
 * Use the ANTLR lexer to extract JSON tokens from a document.
 * It uses a 2-step lookahead to identify KEY text strings. It therefore does not require to
 * construct a syntax tree.
 */
public class AntlrTokenScanner implements ITokenScanner {

    private int offset;
    private Lexer lexer;
    private CommonToken current;
    private CommonToken next;
    private CommonToken afterNext;
    private TokenStyler tokenStyler;

    /**
     * Uses the given JSONLexer
     * @param lexer the lexer to use
     */
    public AntlrTokenScanner(Lexer lexer) {
        this.lexer = lexer;
        this.tokenStyler = new TokenStyler() {
            @Override
            public TextAttribute apply(TokenStyle style) {
                return new TextAttribute(null);
            }
        };
    }

    /**
     * @param lexer the lexer to use
     * @param mapping the mapping from token type to result
     */
    public AntlrTokenScanner(Lexer lexer, TokenStyler mapping) {
        this.lexer = lexer;
        this.tokenStyler = mapping;
    }

    @Override
    public void setRange(IDocument document, int offset, int length) {
        this.offset = offset;
        try {
            String text = document.get(offset, length);
            lexer.setInputStream(new ANTLRInputStream(text));
            next = (CommonToken) lexer.nextToken();
            afterNext = (CommonToken) lexer.nextToken();
        } catch (BadLocationException e) {
            JsonLog.logError("Attempting to access a non-existing position", e);
        }
    }

    @Override
    public IToken nextToken() {
        Token token = lexer.nextToken();

        if (!(token instanceof CommonToken)) {
            // this doesn't seem to happen in practice
            // if it ever does: don't update current/previous, just bail out
            return org.eclipse.jface.text.rules.Token.UNDEFINED;
        }

        current = next;
        next = afterNext;
        afterNext = (CommonToken) token;

        if (current.getType() == Token.EOF) {
            return org.eclipse.jface.text.rules.Token.EOF;
        }

        if (current.getType() == JSONLexer.WS) {
            return org.eclipse.jface.text.rules.Token.WHITESPACE;
        }

        int currentType = current.getType();
        int nextType = next.getType();
        int afterNextType = afterNext.getType();
        TokenStyle style = getStyle(currentType, nextType, afterNextType);
        TextAttribute data = tokenStyler.apply(style);

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

    private TokenStyle getStyle(int currentType, int nextType, int afterNextType) {
        switch (currentType) {
        case JSONLexer.STRING:
            // There could be whitespace between current and the next (real) token
            // we therefore need to look two tokens ahead
            if (nextType == JSONLexer.COLON || (nextType == JSONLexer.WS && afterNextType == JSONLexer.COLON)) {
                return TokenStyle.KEY;
            } else {
                return TokenStyle.TEXT;
            }

        case JSONLexer.NUMBER:
            return TokenStyle.NUMBER;

        case JSONLexer.TRUE:
        case JSONLexer.FALSE:
            return TokenStyle.BOOLEAN;

        case JSONLexer.NULL:
            return TokenStyle.NULL;

        case JSONLexer.LINE_COMMENT:
        case JSONLexer.BLOCK_COMMENT:
            return TokenStyle.COMMENT;

        default:
            return TokenStyle.DEFAULT;
        }
    }
}
