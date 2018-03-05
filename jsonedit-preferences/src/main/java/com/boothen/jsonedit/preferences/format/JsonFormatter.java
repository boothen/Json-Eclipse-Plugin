package com.boothen.jsonedit.preferences.format;

import static org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS;
import static org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.antlr.JSONParser;

/**
 * Reads from a token source and writes formatted and indented text.
 * The following preferences are respected:
 * <ul>
 * <li>{@link Vocabulary#getSymbolicName}<code>(i) + .prefix=(NONE, SPACE, NEWLINE)</code></li>
 * <li>{@link Vocabulary#getSymbolicName}<code>(i) + .suffix=(NONE, SPACE, NEWLINE)</code></li>
 * <li>{@link AbstractDecoratedTextEditorPreferenceConstants#EDITOR_SPACES_FOR_TABS}</li>
 * <li>{@link AbstractDecoratedTextEditorPreferenceConstants#EDITOR_TAB_WIDTH}</li>
 * </ul>
 */
public class JsonFormatter {

    public enum Affix {
        NONE,
        SPACE,
        NEWLINE
    }
    private final Map<Integer, Affix> prefixes = new HashMap<>();
    private final Map<Integer, Affix> suffixes = new HashMap<>();

    private String lineDelim;
    private JsonIndenter indenter;

    /**
     * @param delimiter the line delimiter to write
     * @param store the store that defines the format style
     */
    public JsonFormatter(String delimiter, IPreferenceStore store) {
        this.lineDelim = delimiter;
        // TODO: use Vocabulary.getMaxTokenType() in ANTLR 4.5.3+
        int vocabularySize = new JSONLexer(null).getTokenNames().length;
        for (int i = 0; i < vocabularySize; i++) {
            String name = JSONLexer.VOCABULARY.getSymbolicName(i);

            Affix prefix = toAffix(store.getString(name + ".prefix"));
            if (prefix != Affix.NONE) {
                prefixes.put(i, prefix);
            }

            Affix suffix = toAffix(store.getString(name + ".suffix"));
            if (suffix != Affix.NONE) {
                suffixes.put(i, suffix);
            }
        }

        boolean spacesForTabs = store.getBoolean(EDITOR_SPACES_FOR_TABS);
        int tabWidth = store.getInt(EDITOR_TAB_WIDTH);
        indenter = new JsonIndenter(tabWidth, spacesForTabs);
    }

    /**
     * @param formatStart the offset of the text that should be formatted in chars.
     * @param lexer the lexer that provides the tokens starting at the <b>beginning of the document</b>.
     * @return formatted and indented tokens as a single text string
     */
    public String format(int formatStart, JSONLexer lexer) {
        StringBuffer buffer = new StringBuffer();

        Token token = lexer.nextToken();

        // update indent level only until we hit the starting position
        while (token.getType() != Token.EOF && token.getStartIndex() < formatStart) {
            indenter.updateIndentLevel(token);
            token = lexer.nextToken();
        }

        Token prevToken = null; // only on default channel
        while (token.getType() != Token.EOF) {
            // ignore existing whitespace by using the default channel only
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {

                // closing elements are processed first so they are
                // one level above already
                if (indenter.decreasesIndent(token)) {
                    indenter.decreaseIndent();
                }
                if (prevToken != null) {
                    addPrefix(token, prevToken, buffer);
                } else {
                    // a one time action: indent the first written token AFTER the
                    // indenter level has been adjusted
                    indenter.indent(buffer);
                }

                buffer.append(token.getText());

                // opening elements are "post-processed": only sub-elements
                // should have a higher indentation
                if (indenter.increasesIndent(token)) {
                    indenter.increaseIndent();
                }

                addSuffix(token, buffer);
                prevToken = token;
            }

            if (token.getChannel() == JSONLexer.COMMENTS_CHANNEL) {
                indenter.indent(buffer);
                buffer.append(token.getText());
                buffer.append(lineDelim);
            }

            token = lexer.nextToken();
        }

        return buffer.toString();
    }

    private void addPrefix(Token token, Token prevToken, StringBuffer buffer) {
        Affix prevSuffix = suffixes.get(prevToken.getType());
        Affix prefix = prefixes.get(token.getType());

        // apply indentation from the previous element
        // if this token is a closing element, the indent level has been reduced
        if (prevSuffix == Affix.NEWLINE) {
            indenter.indent(buffer);
        }

        if (prefix != null) {
            // prefix only if different from last written suffix (avoid double newlines)
            if (prefix != prevSuffix) {
                String text = convertAffix(prefix);
                buffer.append(text);
                if (prefix == Affix.NEWLINE) {
                    indenter.indent(buffer);
                }
            }
        }
    }

    private void addSuffix(Token token, StringBuffer buffer) {
        Affix suffix = suffixes.get(token.getType());
        if (suffix != null) {
            String text = convertAffix(suffix);
            buffer.append(text);
            // indentation cannot happen here, since the next element could be a closing tag
            // thus decreasing the indentation
        }
    }

    private String convertAffix(Affix affix) {
        switch (affix) {
        case NEWLINE:
            return lineDelim;
        case SPACE:
            return " ";
        default:
            return null;
        }
    }

    private static Affix toAffix(Object value) {
        if (value instanceof String) {
            String val = (String) value;
            try {
                return Affix.valueOf(val);
            } catch (IllegalArgumentException e) {
                // ignore
            }
        }

        return Affix.NONE;
    }
}
