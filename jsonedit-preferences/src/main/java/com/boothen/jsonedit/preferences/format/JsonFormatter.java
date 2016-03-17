package com.boothen.jsonedit.preferences.format;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.Token;

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

    public JsonFormatter(String newline, Map prefs) {
        this.newline = newline;
        JSONLexer lexer = new JSONLexer(null);
        for (int i = 0; i < lexer.getTokenNames().length; i++) {
            String name = JSONLexer.VOCABULARY.getSymbolicName(i);
            String prefix = convertAffix(toAffix(prefs.get(name + ".prefix")));
            String suffix = convertAffix(toAffix(prefs.get(name + ".suffix")));
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
        while (token.getType() != Token.EOF) {
            boolean isFirst = token.getTokenIndex() > 0;
            if (!isFirst) {
                String prefix = prefix(token);
                if (prefix != null) {
                    buffer.append(prefix);
                }
            }

            buffer.append(token.getText());
            String suffix = suffix(token);
            if (suffix != null) {
                buffer.append(suffix);
            }
            token = lexer.nextToken();
        }

        return buffer.toString();
    }

    private String prefix(Token token) {
        for (FormatterRule format : enabledTriggers) {
            if (format.getType() == token.getType()) {
                // only the first hit is returned
                return format.getPrefix();
            }
        }
        return null;
    }


    private String suffix(Token token) {
        for (FormatterRule format : enabledTriggers) {
            if (format.getType() == token.getType()) {
                // only the first hit is returned
                return format.getSuffix();
            }
        }
        return null;
    }

    private String convertAffix(Affix affix) {
        if (affix == null) {
            return null;
        }

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
        private final String prefix;
        private final String suffix;

        public FormatterRule(int type, String prefix, String suffix) {
            this.type = type;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public int getType() {
            return type;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }

}
