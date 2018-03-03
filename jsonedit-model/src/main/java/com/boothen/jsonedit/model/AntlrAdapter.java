package com.boothen.jsonedit.model;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.antlr.JSONParser;
import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.model.ParseProblem.Severity;

/**
 *
 */
public class AntlrAdapter {

    /**
     * @param reader the reader that provides the document
     * @return the result of the parsing operation
     * @throws IOException if the document cannot be read - parsing errors are in ParseResult
     */
    public static ParseResult convert(Reader reader) throws IOException {
        CharStream stream = new ANTLRInputStream(reader);

        JSONLexer lexer = new JSONLexer(stream);
        LexerErrorListener lexerErrorListener = new LexerErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(lexerErrorListener);

        JSONParser parser = new JSONParser(new CommonTokenStream(lexer));
        ParserErrorListener parserErrorListener = new ParserErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(parserErrorListener);

        JsonContext syntaxTree = parser.json();
        List<ParseProblem> lexerErrors = lexerErrorListener.getErrors();
        List<ParseProblem> parserErrors = parserErrorListener.getErrors();
        ParseResult result = new ParseResult(syntaxTree, lexerErrors, parserErrors);
        return result;
    }

    private static class LexerErrorListener extends BaseErrorListener {
        private final List<ParseProblem> errorList = new ArrayList<>();

        public LexerErrorListener() {
            // public
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int posInLine,
                String msg, RecognitionException e) {
            Token offendingToken = e != null ? e.getOffendingToken() : null;
            int endPos;
            if (offendingToken != null) {
                endPos = posInLine + offendingToken.getText().length();
            } else {
                endPos = posInLine + 1;
            }
            ParseProblem error = new ParseProblem(Severity.ERROR, msg, line, posInLine, endPos);
            errorList.add(error);
        }

        /**
         * @return the errorList
         */
        public List<ParseProblem> getErrors() {
            return errorList;
        }
    }

    private static class ParserErrorListener extends BaseErrorListener {
        private final List<ParseProblem> errorList = new ArrayList<>();

        public ParserErrorListener() {
            // public
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int posInLine,
                String msg, RecognitionException e) {
            Token offendingToken = null;
            if (e != null) {
                offendingToken = e.getOffendingToken();
            }
            if (offendingSymbol instanceof Token) {
                offendingToken = (Token) offendingSymbol;
            }

            int endPos;
            if (offendingToken != null) {
                endPos = posInLine + offendingToken.getText().length();
            } else {
                endPos = posInLine + 1;
            }

            ParseProblem error = new ParseProblem(Severity.ERROR, msg, line, posInLine, endPos);
            errorList.add(error);
        }

        /**
         * @return the errorList
         */
        public List<ParseProblem> getErrors() {
            return errorList;
        }
    }
}
