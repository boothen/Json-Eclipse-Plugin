package com.boothen.jsonedit.preferences.format;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;
import org.eclipse.jface.preference.IPreferenceStore;

import com.boothen.jsonedit.antlr.JSONLexer;
/**
 * TODO: describe
 */
public class JsonFormatter {

    public enum Affix {
        NONE,
        SPACE,
        NEWLINE
    }
    private final Map<Integer, Affix> prefixes = new HashMap<>();
    private final Map<Integer, Affix> suffixes = new HashMap<>();

    private String newline;

    public JsonFormatter(String newline, IPreferenceStore store) {
        this.newline = newline;
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
    }

    /**
     * @param lexer
     * @param map
     * @return
     */
    public String format(JSONLexer lexer) {
        StringBuffer buffer = new StringBuffer();

        Token prevToken = null; // only on default channel
        Token token = lexer.nextToken();
        while (token.getType() != Token.EOF) {
            // format only content that is parsed (no whitespace)
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
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
            }
        }
    }

    private void addSuffix(Token token, StringBuffer buffer) {
        Affix prefix = suffixes.get(token.getType());
        if (prefix != null) {
            String text = convertAffix(prefix);
            buffer.append(text);
        }
    }

    private String convertAffix(Affix affix) {
        switch (affix) {
        case NEWLINE:
            return newline;
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
