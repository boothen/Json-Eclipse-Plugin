package com.boothen.jsonedit.problems;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser.ValueContext;
import com.boothen.jsonedit.model.ParseProblem;
import com.boothen.jsonedit.model.ParseProblem.Severity;

/**
 * Recurses into tree finding all string-related problems.
 */
public class StringProblemFinder extends JSONBaseVisitor<Void> {

    // Do a negative lookahead for [bnfrt\/"] or uXXXX (four digit hex code)
    // Original pattern: \\(?![bnfrt\/"]|u[0-9a-fA-F]{4}).
    private static final Pattern UNESCAPED_UNSAFE = Pattern.compile("\\\\(?![bnfrt\\/\"]|u[0-9a-fA-F]{4}).");

    private final Collection<ParseProblem> problems;

    /**
     * @param problems the list of problems that will receive all found encountered problems
     */
    public StringProblemFinder(Collection<ParseProblem> problems) {
        this.problems = problems;
    }

    @Override
    public Void visitValue(ValueContext ctx) {
        TerminalNode s = ctx.STRING();
        if (s != null) {
            checkEscaping(s.getSymbol());
        }

        return super.visitValue(ctx);
    }

    private void checkEscaping(Token token) {
        // replace double-occurrences of backslash to identify odd numbers only
        // this will make it work for \\\ too
        String text = token.getText().replace("\\\\", "__");  // preserve length

        Matcher matcher = UNESCAPED_UNSAFE.matcher(text);
        while (matcher.find()) {
            report(token, matcher);
        }
    }

    private void report(Token token, Matcher matcher) {
        String text = matcher.group();
        String errorMessage = String.format("Escaping is not allowed in ECMA-404 for '%s'", text);
        int line = token.getLine();
        int posInLine = token.getCharPositionInLine() + matcher.start();
        int endPos = posInLine + text.length();
        problems.add(new ParseProblem(Severity.WARNING, errorMessage, line, posInLine, endPos));
    }
}
