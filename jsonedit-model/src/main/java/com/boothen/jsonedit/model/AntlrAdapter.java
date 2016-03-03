package com.boothen.jsonedit.model;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.eclipse.jface.text.IDocument;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.antlr.JSONParser;
import com.boothen.jsonedit.antlr.JSONParser.JsonContext;

/**
 *
 */
public class AntlrAdapter {

    public static ParseResult convert(IDocument doc) throws IOException {
        Reader reader = new StringReader(doc.get());
        CharStream stream = new ANTLRInputStream(reader);
        MyErrorListener errorListener = new MyErrorListener();

        JSONLexer lexer = new JSONLexer(stream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        JSONParser parser = new JSONParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        ParseResult result = new ParseResult(parser.json(), errorListener.getErrors());
        return result;
    }

    public static class ParseResult {
        private final JsonContext tree;
        private final List<ParseError> errors;

        public ParseResult(JsonContext json, List<ParseError> errors) {
            this.tree = json;
            this.errors = errors;
        }

        public JsonContext getTree() {
            return tree;
        }

        public List<ParseError> getErrors() {
            return errors;
        }
    }

    public static class ParseError {

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

    private static class MyErrorListener extends BaseErrorListener {
        private final List<ParseError> errorList = new ArrayList<>();

        public MyErrorListener() {
            // public
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            // TODO: investigate why getExpectedTokens() reports illegal state on syntax errors inside Pair
//          Collection<Integer> set = e.getExpectedTokens().toSet();
            Token offendingToken = e != null ? e.getOffendingToken() : null;
            ParseError error = new ParseError(msg, line, charPositionInLine, offendingSymbol, offendingToken);
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
