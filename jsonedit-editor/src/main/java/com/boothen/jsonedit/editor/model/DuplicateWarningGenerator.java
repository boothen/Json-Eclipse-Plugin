package com.boothen.jsonedit.editor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.Token;

import com.boothen.jsonedit.model.DuplicateKeyListener;
import com.boothen.jsonedit.model.ParseError;
import com.boothen.jsonedit.model.ParseError.Severity;

/**
 * TODO: describe
 */
class DuplicateWarningGenerator implements DuplicateKeyListener {

    private final List<ParseError> warnings = new ArrayList<>();

    @Override
    public void reportDuplicate(String key, Token first, Token second) {
        String errorMessage = String.format("Duplicate key: %s - already at line %d: [%d]",
                key, first.getLine(), first.getCharPositionInLine());
        int line = second.getLine();
        int posInLine = second.getCharPositionInLine();
        warnings.add(new ParseError(errorMessage, line, posInLine, second, Severity.WARNING));
    }

    /**
     * @return the warnings
     */
    public List<ParseError> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }


}