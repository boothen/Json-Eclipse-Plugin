package com.boothen.jsonedit.model;

import java.util.Collections;
import java.util.List;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;

/**
 * Describes the result of a parsing operation
 */
public class ParseResult {
    private final JsonContext tree;
    private final List<ParseProblem> lexerErrors;
    private final List<ParseProblem> parserErrors;

    /**
     * @param jsonTree the JSON root element
     * @param lexerErrors a list of lexer errors
     * @param parserErrors a list of parser errors
     */
    public ParseResult(JsonContext jsonTree, List<ParseProblem> lexerErrors, List<ParseProblem> parserErrors) {
        this.tree = jsonTree;
        this.lexerErrors = lexerErrors;
        this.parserErrors = parserErrors;
    }

    /**
     * @return the JSON root element
     */
    public JsonContext getTree() {
        return tree;
    }

    /**
     * @return a list of lexer errors
     */
    public List<ParseProblem> getLexerErrors() {
        return Collections.unmodifiableList(lexerErrors);
    }

    /**
     * @return a list of parser problems
     */
    public List<ParseProblem> getParserErrors() {
        return Collections.unmodifiableList(parserErrors);
    }
}
