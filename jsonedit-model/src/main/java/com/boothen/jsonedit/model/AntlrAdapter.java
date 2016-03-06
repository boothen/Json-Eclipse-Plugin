package com.boothen.jsonedit.model;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
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
import com.boothen.jsonedit.model.ParseError.Severity;

/**
 *
 */
public class AntlrAdapter {

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
        List<ParseError> lexerErrors = lexerErrorListener.getErrors();
        List<ParseError> parserErrors = parserErrorListener.getErrors();
        ParseResult result = new ParseResult(syntaxTree, lexerErrors, parserErrors);
        return result;
    }

    public static class ParseResult {
        private final JsonContext tree;
        private final List<ParseError> lexerErrors;
        private final List<ParseError> parserErrors;

        public ParseResult(JsonContext json, List<ParseError> lexerErrors, List<ParseError> parserErrors) {
            this.tree = json;
            this.lexerErrors = lexerErrors;
            this.parserErrors = parserErrors;
        }

        public JsonContext getTree() {
            return tree;
        }

        public List<ParseError> getLexerErrors() {
            return Collections.unmodifiableList(lexerErrors);
        }

        public List<ParseError> getParserErrors() {
            return Collections.unmodifiableList(parserErrors);
        }
    }

    private static class LexerErrorListener extends BaseErrorListener {
        private final List<ParseError> errorList = new ArrayList<>();

        public LexerErrorListener() {
            // public
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int posInLine,
                String msg, RecognitionException e) {
            Token offendingToken = e != null ? e.getOffendingToken() : null;
            ParseError error = new ParseError(msg, line, posInLine, offendingToken, Severity.ERROR);
            errorList.add(error);
        }

        /**
         * @return the errorList
         */
        public List<ParseError> getErrors() {
            return errorList;
        }
    }

    private static class ParserErrorListener extends BaseErrorListener {
        private final List<ParseError> errorList = new ArrayList<>();

        public ParserErrorListener() {
            // public
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            Token offendingToken = null;
            if (e != null) {
                offendingToken = e.getOffendingToken();
            }
            if (offendingSymbol instanceof Token) {
                offendingToken = (Token) offendingSymbol;
            }

            ParseError error = new ParseError(msg, line, charPositionInLine, offendingToken, Severity.ERROR);
            errorList.add(error);
        }

        /**
         * @return the errorList
         */
        public List<ParseError> getErrors() {
            return errorList;
        }
    }
}
