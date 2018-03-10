// Generated from JSONLexer.g4 by ANTLR 4.7.1
package com.boothen.jsonedit.antlr;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSONLexer extends Lexer {
    static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    public static final int
        STRING=1, NUMBER=2, TRUE=3, FALSE=4, NULL=5, BEGIN_ARRAY=6, END_ARRAY=7,
        BEGIN_OBJECT=8, END_OBJECT=9, COMMA=10, COLON=11, WS=12, LINE_COMMENT=13,
        BLOCK_COMMENT=14, UNKNOWN=15;
    public static final int
        COMMENTS_CHANNEL=2;
    public static String[] channelNames = {
        "DEFAULT_TOKEN_CHANNEL", "HIDDEN", "COMMENTS_CHANNEL"
    };

    public static String[] modeNames = {
        "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
        "STRING", "ESC", "UNICODE", "HEX", "SAFECODEPOINT", "NUMBER", "TRUE",
        "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY", "BEGIN_OBJECT", "END_OBJECT",
        "COMMA", "COLON", "INT", "EXP", "WS", "LINE_COMMENT", "BLOCK_COMMENT",
        "UNKNOWN"
    };

    private static final String[] _LITERAL_NAMES = {
        null, null, null, "'true'", "'false'", "'null'", "'['", "']'", "'{'",
        "'}'", "','", "':'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, "STRING", "NUMBER", "TRUE", "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY",
        "BEGIN_OBJECT", "END_OBJECT", "COMMA", "COLON", "WS", "LINE_COMMENT",
        "BLOCK_COMMENT", "UNKNOWN"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    private static final String[] tokenNames;
    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    /**
     * @deprecated Use {@link #getVocabulary()} instead.
     */
    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }


    public JSONLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
    }

    @Override
    public String getGrammarFileName() { return "JSONLexer.g4"; }

    @Override
    public String[] getRuleNames() { return ruleNames; }

    @Override
    public String getSerializedATN() { return _serializedATN; }

    @Override
    public String[] getChannelNames() { return channelNames; }

    @Override
    public String[] getModeNames() { return modeNames; }

    @Override
    public ATN getATN() { return _ATN; }

    public static final String _serializedATN =
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21\u00a7\b\1\4\2"+
        "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
        "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
        "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\7\2\61\n\2\f"+
        "\2\16\2\64\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3<\n\3\3\4\3\4\3\4\3\4\3\4\3"+
        "\4\3\5\3\5\3\6\3\6\3\7\5\7I\n\7\3\7\3\7\3\7\6\7N\n\7\r\7\16\7O\5\7R\n"+
        "\7\3\7\5\7U\n\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3"+
        "\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21"+
        "\3\21\3\21\7\21v\n\21\f\21\16\21y\13\21\5\21{\n\21\3\22\3\22\5\22\177"+
        "\n\22\3\22\3\22\3\23\6\23\u0084\n\23\r\23\16\23\u0085\3\23\3\23\3\24\3"+
        "\24\3\24\3\24\7\24\u008e\n\24\f\24\16\24\u0091\13\24\3\24\3\24\3\25\3"+
        "\25\3\25\3\25\7\25\u0099\n\25\f\25\16\25\u009c\13\25\3\25\3\25\3\25\3"+
        "\25\3\25\3\26\6\26\u00a4\n\26\r\26\16\26\u00a5\3\u009a\2\27\3\3\5\2\7"+
        "\2\t\2\13\2\r\4\17\5\21\6\23\7\25\b\27\t\31\n\33\13\35\f\37\r!\2#\2%\16"+
        "\'\17)\20+\21\3\2\f\n\2$$\61\61^^ddhhppttvv\5\2\62;CHch\5\2\2!$$^^\3\2"+
        "\62;\3\2\63;\4\2GGgg\4\2--//\5\2\13\f\17\17\"\"\4\2\f\f\17\17\f\2\13\f"+
        "\17\17\"\"$$./\62<]]__}}\177\177\2\u00af\2\3\3\2\2\2\2\r\3\2\2\2\2\17"+
        "\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
        "\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
        "\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\67\3\2\2\2\7=\3\2\2\2\tC\3\2\2\2\13E"+
        "\3\2\2\2\rH\3\2\2\2\17V\3\2\2\2\21[\3\2\2\2\23a\3\2\2\2\25f\3\2\2\2\27"+
        "h\3\2\2\2\31j\3\2\2\2\33l\3\2\2\2\35n\3\2\2\2\37p\3\2\2\2!z\3\2\2\2#|"+
        "\3\2\2\2%\u0083\3\2\2\2\'\u0089\3\2\2\2)\u0094\3\2\2\2+\u00a3\3\2\2\2"+
        "-\62\7$\2\2.\61\5\5\3\2/\61\5\13\6\2\60.\3\2\2\2\60/\3\2\2\2\61\64\3\2"+
        "\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2\2\2\64\62\3\2\2\2\65\66\7$"+
        "\2\2\66\4\3\2\2\2\67;\7^\2\28<\t\2\2\29<\5\7\4\2:<\5\13\6\2;8\3\2\2\2"+
        ";9\3\2\2\2;:\3\2\2\2<\6\3\2\2\2=>\7w\2\2>?\5\t\5\2?@\5\t\5\2@A\5\t\5\2"+
        "AB\5\t\5\2B\b\3\2\2\2CD\t\3\2\2D\n\3\2\2\2EF\n\4\2\2F\f\3\2\2\2GI\7/\2"+
        "\2HG\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JQ\5!\21\2KM\7\60\2\2LN\t\5\2\2ML\3\2"+
        "\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2\2PR\3\2\2\2QK\3\2\2\2QR\3\2\2\2RT\3\2"+
        "\2\2SU\5#\22\2TS\3\2\2\2TU\3\2\2\2U\16\3\2\2\2VW\7v\2\2WX\7t\2\2XY\7w"+
        "\2\2YZ\7g\2\2Z\20\3\2\2\2[\\\7h\2\2\\]\7c\2\2]^\7n\2\2^_\7u\2\2_`\7g\2"+
        "\2`\22\3\2\2\2ab\7p\2\2bc\7w\2\2cd\7n\2\2de\7n\2\2e\24\3\2\2\2fg\7]\2"+
        "\2g\26\3\2\2\2hi\7_\2\2i\30\3\2\2\2jk\7}\2\2k\32\3\2\2\2lm\7\177\2\2m"+
        "\34\3\2\2\2no\7.\2\2o\36\3\2\2\2pq\7<\2\2q \3\2\2\2r{\7\62\2\2sw\t\6\2"+
        "\2tv\t\5\2\2ut\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x{\3\2\2\2yw\3\2\2"+
        "\2zr\3\2\2\2zs\3\2\2\2{\"\3\2\2\2|~\t\7\2\2}\177\t\b\2\2~}\3\2\2\2~\177"+
        "\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\5!\21\2\u0081$\3\2\2\2\u0082\u0084"+
        "\t\t\2\2\u0083\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0083\3\2\2\2\u0085"+
        "\u0086\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\b\23\2\2\u0088&\3\2\2\2"+
        "\u0089\u008a\7\61\2\2\u008a\u008b\7\61\2\2\u008b\u008f\3\2\2\2\u008c\u008e"+
        "\n\n\2\2\u008d\u008c\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f"+
        "\u0090\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0093\b\24"+
        "\3\2\u0093(\3\2\2\2\u0094\u0095\7\61\2\2\u0095\u0096\7,\2\2\u0096\u009a"+
        "\3\2\2\2\u0097\u0099\13\2\2\2\u0098\u0097\3\2\2\2\u0099\u009c\3\2\2\2"+
        "\u009a\u009b\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009d\3\2\2\2\u009c\u009a"+
        "\3\2\2\2\u009d\u009e\7,\2\2\u009e\u009f\7\61\2\2\u009f\u00a0\3\2\2\2\u00a0"+
        "\u00a1\b\25\3\2\u00a1*\3\2\2\2\u00a2\u00a4\n\13\2\2\u00a3\u00a2\3\2\2"+
        "\2\u00a4\u00a5\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6,"+
        "\3\2\2\2\21\2\60\62;HOQTwz~\u0085\u008f\u009a\u00a5\4\2\3\2\2\4\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
