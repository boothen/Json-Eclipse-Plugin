package com.boothen.jsonedit.problems;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.antlr.JSONParser.PairContext;
import com.boothen.jsonedit.model.ParseProblem;
import com.boothen.jsonedit.model.ParseProblem.Severity;

/**
 * Recurses into tree finding all duplicate keys.
 */
public class DuplicateKeyFinder extends JSONBaseVisitor<Void> {

    private final Collection<ParseProblem> problems;

    /**
     * @param problems the list of problems that will receive all found encountered problems
     */
    public DuplicateKeyFinder(Collection<ParseProblem> problems) {
        this.problems = problems;
    }

    @Override
    public Void visitObject(ObjectContext ctx) {
        Map<String, Token> keys = new HashMap<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if (child instanceof PairContext) {
                PairContext pair = (PairContext) child;
                // Evaluate successful rule matches only
                if (pair.exception == null) {
                    Token keyToken = pair.STRING().getSymbol();
                    String key = keyToken.getText();
                    Token existing = keys.put(key, keyToken);
                    if (existing != null) {
                        reportDuplicate(key, existing, keyToken);
                    }
                }
            }
        }
        return super.visitObject(ctx);
    }

    private void reportDuplicate(String key, Token first, Token second) {
        String errorMessage = String.format("Duplicate key: %s - already at line %d: [%d]",
                key, first.getLine(), first.getCharPositionInLine());
        int line = second.getLine();
        int startPos = second.getCharPositionInLine();
        int endPos = startPos + second.getText().length();
        problems.add(new ParseProblem(Severity.WARNING, errorMessage, line, startPos, endPos));
    }
}
