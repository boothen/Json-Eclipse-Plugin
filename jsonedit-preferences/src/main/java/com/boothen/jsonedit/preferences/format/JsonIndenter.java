package com.boothen.jsonedit.preferences.format;

import java.util.Arrays;

import org.antlr.v4.runtime.Token;

import com.boothen.jsonedit.antlr.JSONLexer;

/**
 * Indents JSON code snippets.
 */
public class JsonIndenter {

    private final int tabWidth;
    private final boolean spacesForTabs;
    private int level;

    /**
     * @param tabWidth width of a tab, measured in spaces
     * @param spacesForTabs true if spaces should be used for indentation instead of tabs
     */
    public JsonIndenter(int tabWidth, boolean spacesForTabs) {
        this.tabWidth = tabWidth;
        this.spacesForTabs = spacesForTabs;
    }

    /**
     * Updates the current indentation level based on the supplied token
     * @param token the token to scan
     */
    public void updateIndentLevel(Token token) {
        if (increasesIndent(token)) {
            increaseIndent();
        }

        if (decreasesIndent(token)) {
            decreaseIndent();
        }
    }

    /**
     * Increases the indentation level
     */
    public void increaseIndent() {
        level++;
    }

    /**
     * Decreases the indentation level
     */
    public void decreaseIndent() {
        level--;
    }

    /**
     * @param token the token to scan
     * @return true if the token would increase the indentation level
     */
    public boolean increasesIndent(Token token) {
        switch (token.getType()) {
        case JSONLexer.BEGIN_OBJECT:
        case JSONLexer.BEGIN_ARRAY:
            return true;
        default:
            return false;
        }
    }

    /**
     * @param token the token to scan
     * @return true if the token would decrease the indentation level
     */
    public boolean decreasesIndent(Token token) {
        switch (token.getType()) {
        case JSONLexer.END_OBJECT:
        case JSONLexer.END_ARRAY:
            return true;
        default:
            return false;
        }
    }

    /**
     * Appends whitespace to the supplied buffer accordings to the current indentation level
     * @param buffer the buffer to append to
     */
    public void indent(StringBuffer buffer) {
        if (level <= 0) {
            return;
        }

        if (spacesForTabs) {
            char[] array = createArray(' ', level * tabWidth);
            buffer.append(array);
        } else {
            char[] array = createArray('\t', level);
            buffer.append(array);
        }
    }

    private static char[] createArray(char fillChar, int length) {
        // TODO: consider caching the created arrays
        char[] array = new char[length];
        Arrays.fill(array, fillChar);
        return array;
    }
}
