package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.Token;
import org.eclipse.core.resources.IMarker;

public class ParseProblem {

    public static enum Severity {
        INFO(IMarker.SEVERITY_INFO),
        WARNING(IMarker.SEVERITY_WARNING),
        ERROR(IMarker.SEVERITY_ERROR);

        private int markerValue;

        Severity(int markerValue) {
            this.markerValue = markerValue;
        }

        /**
         * @return the markerValue
         */
        public int getMarkerValue() {
            return markerValue;
        }
    }

    private final String msg;
    private final int line;
    private final int charPositionInLine;
    private final Token offendingToken;
    private final Severity severity;

    public ParseProblem(String msg, int line, int charPositionInLine, Token offendingToken, Severity severity) {
        this.msg = msg;
        this.line = line;
        this.charPositionInLine = charPositionInLine;
        this.offendingToken = offendingToken;
        this.severity = severity;
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

    public Token getOffendingToken() {
        return offendingToken;
    }

    /**
     * @return
     */
    public Severity getSeverity() {
        return severity;
    }
}