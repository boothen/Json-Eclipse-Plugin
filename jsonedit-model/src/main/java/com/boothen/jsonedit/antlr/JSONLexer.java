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
        UNKNOWN=14;
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
        "COMMA", "COLON", "INT", "EXP", "WS", "LINE_COMMENT", "UNKNOWN"
    };

    private static final String[] _LITERAL_NAMES = {
        null, null, null, "'true'", "'false'", "'null'", "'['", "']'", "'{'",
        "'}'", "','", "':'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, "STRING", "NUMBER", "TRUE", "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY",
        "BEGIN_OBJECT", "END_OBJECT", "COMMA", "COLON", "WS", "LINE_COMMENT",
        "UNKNOWN"
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
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20\u0097\b\1\4\2"+
        "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
        "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
        "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\7\2/\n\2\f\2\16\2\62\13"+
        "\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3:\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
        "\6\3\6\3\7\5\7G\n\7\3\7\3\7\3\7\6\7L\n\7\r\7\16\7M\5\7P\n\7\3\7\5\7S\n"+
        "\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13"+
        "\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\7\21"+
        "t\n\21\f\21\16\21w\13\21\5\21y\n\21\3\22\3\22\5\22}\n\22\3\22\3\22\3\23"+
        "\6\23\u0082\n\23\r\23\16\23\u0083\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u008c"+
        "\n\24\f\24\16\24\u008f\13\24\3\24\3\24\3\25\6\25\u0094\n\25\r\25\16\25"+
        "\u0095\2\2\26\3\3\5\2\7\2\t\2\13\2\r\4\17\5\21\6\23\7\25\b\27\t\31\n\33"+
        "\13\35\f\37\r!\2#\2%\16\'\17)\20\3\2\f\n\2$$\61\61^^ddhhppttvv\5\2\62"+
        ";CHch\5\2\2!$$^^\3\2\62;\3\2\63;\4\2GGgg\4\2--//\5\2\13\f\17\17\"\"\4"+
        "\2\f\f\17\17\f\2\13\f\17\17\"\"$$./\62<]]__}}\177\177\2\u009e\2\3\3\2"+
        "\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
        "\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2"+
        "\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5\65\3\2\2\2\7;\3\2\2\2\tA\3\2"+
        "\2\2\13C\3\2\2\2\rF\3\2\2\2\17T\3\2\2\2\21Y\3\2\2\2\23_\3\2\2\2\25d\3"+
        "\2\2\2\27f\3\2\2\2\31h\3\2\2\2\33j\3\2\2\2\35l\3\2\2\2\37n\3\2\2\2!x\3"+
        "\2\2\2#z\3\2\2\2%\u0081\3\2\2\2\'\u0087\3\2\2\2)\u0093\3\2\2\2+\60\7$"+
        "\2\2,/\5\5\3\2-/\5\13\6\2.,\3\2\2\2.-\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2"+
        "\60\61\3\2\2\2\61\63\3\2\2\2\62\60\3\2\2\2\63\64\7$\2\2\64\4\3\2\2\2\65"+
        "9\7^\2\2\66:\t\2\2\2\67:\5\7\4\28:\5\13\6\29\66\3\2\2\29\67\3\2\2\298"+
        "\3\2\2\2:\6\3\2\2\2;<\7w\2\2<=\5\t\5\2=>\5\t\5\2>?\5\t\5\2?@\5\t\5\2@"+
        "\b\3\2\2\2AB\t\3\2\2B\n\3\2\2\2CD\n\4\2\2D\f\3\2\2\2EG\7/\2\2FE\3\2\2"+
        "\2FG\3\2\2\2GH\3\2\2\2HO\5!\21\2IK\7\60\2\2JL\t\5\2\2KJ\3\2\2\2LM\3\2"+
        "\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OI\3\2\2\2OP\3\2\2\2PR\3\2\2\2QS\5#"+
        "\22\2RQ\3\2\2\2RS\3\2\2\2S\16\3\2\2\2TU\7v\2\2UV\7t\2\2VW\7w\2\2WX\7g"+
        "\2\2X\20\3\2\2\2YZ\7h\2\2Z[\7c\2\2[\\\7n\2\2\\]\7u\2\2]^\7g\2\2^\22\3"+
        "\2\2\2_`\7p\2\2`a\7w\2\2ab\7n\2\2bc\7n\2\2c\24\3\2\2\2de\7]\2\2e\26\3"+
        "\2\2\2fg\7_\2\2g\30\3\2\2\2hi\7}\2\2i\32\3\2\2\2jk\7\177\2\2k\34\3\2\2"+
        "\2lm\7.\2\2m\36\3\2\2\2no\7<\2\2o \3\2\2\2py\7\62\2\2qu\t\6\2\2rt\t\5"+
        "\2\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2\2\2vy\3\2\2\2wu\3\2\2\2xp\3\2"+
        "\2\2xq\3\2\2\2y\"\3\2\2\2z|\t\7\2\2{}\t\b\2\2|{\3\2\2\2|}\3\2\2\2}~\3"+
        "\2\2\2~\177\5!\21\2\177$\3\2\2\2\u0080\u0082\t\t\2\2\u0081\u0080\3\2\2"+
        "\2\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085"+
        "\3\2\2\2\u0085\u0086\b\23\2\2\u0086&\3\2\2\2\u0087\u0088\7\61\2\2\u0088"+
        "\u0089\7\61\2\2\u0089\u008d\3\2\2\2\u008a\u008c\n\n\2\2\u008b\u008a\3"+
        "\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e"+
        "\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\b\24\3\2\u0091(\3\2\2\2"+
        "\u0092\u0094\n\13\2\2\u0093\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093"+
        "\3\2\2\2\u0095\u0096\3\2\2\2\u0096*\3\2\2\2\20\2.\609FMORux|\u0083\u008d"+
        "\u0095\4\2\3\2\2\4\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
