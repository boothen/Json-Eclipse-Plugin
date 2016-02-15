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
        private Token offender;
        private Collection<Integer> set;
        private String localizedMessage;

        public ParseError(Token offender, Collection<Integer> set, String localizedMessage) {
            this.offender = offender;
            this.set = set;
            this.localizedMessage = localizedMessage;
        }

           public Token getOffender() {
            return offender;
        }

        public Collection<Integer> getSet() {
            return set;
        }

        public String getLocalizedMessage() {
            return localizedMessage;
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
            Collection<Integer> set = e.getExpectedTokens().toSet();
            Token offender = e.getOffendingToken();
            ParseError error = new ParseError(offender, set, e.getLocalizedMessage());
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
