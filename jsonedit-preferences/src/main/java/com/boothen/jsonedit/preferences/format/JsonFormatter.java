package com.boothen.jsonedit.preferences.format;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<FormatterRule> enabledTriggers = new LinkedHashSet<>();
    private String newline;

    public JsonFormatter(String newline, IPreferenceStore store) {
        this.newline = newline;
        int vocabularySize = new JSONLexer(null).getTokenNames().length;
        for (int i = 0; i < vocabularySize; i++) {
            String name = JSONLexer.VOCABULARY.getSymbolicName(i);
            Affix prefix = toAffix(store.getString(name + ".prefix"));
            Affix suffix = toAffix(store.getString(name + ".suffix"));
            if (prefix != null || suffix != null) {
                enabledTriggers.add(new FormatterRule(i, prefix, suffix));
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

        Token token = lexer.nextToken();
        boolean isFirst = true;
        while (token.getType() != Token.EOF) {
            // format only content that is parsed (no whitespace)
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                if (!isFirst) {
                    addPrefix(token, buffer);
                }

                buffer.append(token.getText());
                addSuffix(token, buffer);
                isFirst = false;
            }
            token = lexer.nextToken();
        }

        return buffer.toString();
    }

    private void addPrefix(Token token, StringBuffer buffer) {
        for (FormatterRule format : enabledTriggers) {
            if (format.getType() == token.getType()) {
                Affix prefix = format.getPrefix();
                if (prefix != Affix.NONE) {
                    String text = convertAffix(prefix);
                    buffer.append(text);
                }
            }
        }
    }


    private void addSuffix(Token token, StringBuffer buffer) {
        for (FormatterRule format : enabledTriggers) {
            if (format.getType() == token.getType()) {
                Affix suffix = format.getSuffix();
                if (suffix != Affix.NONE) {
                    String text = convertAffix(suffix);
                    buffer.append(text);
                }
            }
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

    private static class FormatterRule {

        private final int type;
        private final Affix prefix;
        private final Affix suffix;

        public FormatterRule(int type, Affix prefix, Affix suffix) {
            this.type = type;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public int getType() {
            return type;
        }

        public Affix getPrefix() {
            return prefix;
        }

        public Affix getSuffix() {
            return suffix;
        }
    }

}
