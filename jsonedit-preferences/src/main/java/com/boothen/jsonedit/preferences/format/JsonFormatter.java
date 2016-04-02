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
     * @param lexer the lexer that provides the tokens
     * @return formatted and indented tokens as a single text string
     */
    public String format(JSONLexer lexer) {
        StringBuffer buffer = new StringBuffer();

        Token prevToken = null; // only on default channel
        Token token = lexer.nextToken();
        while (token.getType() != Token.EOF) {
            // format only content that is parsed (no whitespace)
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                indenter.updateIndentLevel(token);
                if (prevToken != null) {
                    addPrefix(token, prevToken, buffer);
                }

                buffer.append(token.getText());
                addSuffix(token, buffer);
                prevToken = token;
            }
            token = lexer.nextToken();
        }

        return buffer.toString();
    }

    private void addPrefix(Token token, Token prevToken, StringBuffer buffer) {
        Affix prefix = prefixes.get(token.getType());
        if (prefix != null) {
            Affix prevSuffix = suffixes.get(prevToken.getType());
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
            if (suffix == Affix.NEWLINE) {
                indenter.indent(buffer);
            }
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
