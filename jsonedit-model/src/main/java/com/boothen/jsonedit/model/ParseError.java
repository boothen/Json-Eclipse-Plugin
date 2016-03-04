package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.Token;

public class ParseError {

    private final String msg;
    private final int line;
    private final int charPositionInLine;
    private final Object offendingSymbol;
    private final Token token;

    public ParseError(String msg, int line, int charPositionInLine, Object offendingSymbol, Token token) {
        this.msg = msg;
        this.line = line;
        this.charPositionInLine = charPositionInLine;
        this.offendingSymbol = offendingSymbol;
        this.token = token;
    }

    /**
     * @return the message to emit
     */
    public String getMessage() {
        return msg;
    }

    /**
     * @return The character position within that line where the error occurred.
     */
    public int getLine() {
        return line;
    }

    /**
     * @return line number in the input where the error occurred.
     */
    public int getCharPositionInLine() {
        return charPositionInLine;
    }

    /**
     * @return The offending token in the input token stream, unless recognizer is a lexer (then it's null).
     * If no viable alternative error, the token at which we started production for the decision is not null.
     */
    public Object getOffendingSymbol() {
        return offendingSymbol;
    }

    /**
     * @return The current Token when an error occurred. Since not all streams support accessing symbols
     * by index, we have to track the Token instance itself.
     */
    public Token getToken() {
        return token;
    }
}