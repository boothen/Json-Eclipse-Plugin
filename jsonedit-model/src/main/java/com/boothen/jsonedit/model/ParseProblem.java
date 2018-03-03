package com.boothen.jsonedit.model;

import org.eclipse.core.resources.IMarker;

/**
 * Describes a parsing problem with the input document
 */
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
    private final int startPositionInLine;
    private final int endPositionInLine;
    private final Severity severity;

    /**
     * @param severity the severity of the problem
     * @param msg the problem message to emit
     * @param line the line number in the input where the error occurred
     * @param start The start position within that line where the error occurred
     * @param end The end position within that line where the error occurred
     */
    public ParseProblem(Severity severity, String msg, int line, int start, int end) {
        this.msg = msg;
        this.line = line;
        this.startPositionInLine = start;
        this.endPositionInLine = end;
        this.severity = severity;
    }

    /**
     * @return the message to emit
     */
    public String getMessage() {
        return msg;
    }

    /**
     * @return line number in the input where the error occurred
     */
    public int getLine() {
        return line;
    }

    /**
     * @return The start position within that line where the error occurred
     */
    public int getStartPositionInLine() {
        return startPositionInLine;
    }

    /**
     * @return The end position within that line where the error occurred
     */
    public int getEndPositionInLine() {
        return endPositionInLine;
    }

    /**
     * @return the severity of the problem
     */
    public Severity getSeverity() {
        return severity;
    }
}