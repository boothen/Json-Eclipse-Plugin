package com.boothen.jsonedit.editor;

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

    public enum TokenFormat {
        ARRAY_NEWLINE_BEFORE(JSONLexer.BEGIN_ARRAY, Affix.NEWLINE, Affix.NONE),
        ARRAY_NEWLINE_AFTER(JSONLexer.BEGIN_ARRAY, Affix.NONE, Affix.NEWLINE),
        ARRAY_SPACE_BEFORE(JSONLexer.BEGIN_ARRAY, Affix.SPACE, Affix.NONE),
        ARRAY_SPACE_AFTER(JSONLexer.BEGIN_ARRAY, Affix.NONE, Affix.SPACE),
        OBJECT_NEWLINE_BEFORE(JSONLexer.BEGIN_OBJECT, Affix.NEWLINE, Affix.NONE),
        OBJECT_NEWLINE_AFTER(JSONLexer.BEGIN_OBJECT, Affix.NONE, Affix.NEWLINE),
        OBJECT_SPACE_BEFORE(JSONLexer.BEGIN_OBJECT, Affix.SPACE, Affix.NONE),
        OBJECT_SPACE_AFTER(JSONLexer.BEGIN_OBJECT, Affix.NONE, Affix.SPACE),
        COMMA_SPACE_AFTER(JSONLexer.COMMA, Affix.NONE, Affix.SPACE),
        COLON_SPACE_BEFORE(JSONLexer.COLON, Affix.SPACE, Affix.NONE),
        COLON_SPACE_AFTER(JSONLexer.COLON, Affix.NONE, Affix.SPACE)
        ;

        private final int type;
        private final Affix prefix;
        private final Affix suffix;

        TokenFormat(int type, Affix prefix, Affix suffix) {
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

//    public static final String PREF_BLANK_LINE_AFTER_COMPLEX = "newLineAfterComplex";

    public static final String PREF_SPACE_AFTER_OBJECT_OPEN = "spaceAfterObjectOpen";
    public static final String PREF_SPACE_BEFORE_OBJECT_CLOSE = "spaceAfterObjectClose";

    public static final String PREF_SPACE_AFTER_ARRAY_OPEN = "spaceAfterArrayOpen";
    public static final String PREF_SPACE_BEFORE_ARRAY_CLOSE = "spaceAfterArrayClose";

    private Set<TokenFormat> enabledTriggers = new LinkedHashSet<>();
    private String newline;

    public JsonFormatter(String newline, Map<String, Object> map) {
        this.newline = newline;
        for (TokenFormat format : TokenFormat.values()) {
            Object value = map.get(format.name());
            if (!Boolean.TRUE.equals(value)) {
                enabledTriggers.add(format);
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
        for (TokenFormat format : enabledTriggers) {
            if (format.getType() == token.getType()) {
                // only the first hit is returned
                return convertAffix(format.getPrefix());
            }
        }
        return null;
    }


    private String suffix(Token token) {
        for (TokenFormat format : enabledTriggers) {
            if (format.getType() == token.getType()) {
                // only the first hit is returned
                return convertAffix(format.getSuffix());
            }
        }
        return null;
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
}
