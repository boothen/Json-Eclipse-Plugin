package com.boothen.jsonedit.editor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.Token;

import com.boothen.jsonedit.model.DuplicateKeyListener;
import com.boothen.jsonedit.model.ParseProblem;
import com.boothen.jsonedit.model.ParseProblem.Severity;

/**
 * TODO: describe
 */
class DuplicateWarningGenerator implements DuplicateKeyListener {

    private final List<ParseProblem> warnings = new ArrayList<>();

    @Override
    public void reportDuplicate(String key, Token first, Token second) {
        String errorMessage = String.format("Duplicate key: %s - already at line %d: [%d]",
                key, first.getLine(), first.getCharPositionInLine());
        int line = second.getLine();
        int posInLine = second.getCharPositionInLine();
        warnings.add(new ParseProblem(errorMessage, line, posInLine, second, Severity.WARNING));
    }

    /**
     * @return the warnings
     */
    public List<ParseProblem> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }


}