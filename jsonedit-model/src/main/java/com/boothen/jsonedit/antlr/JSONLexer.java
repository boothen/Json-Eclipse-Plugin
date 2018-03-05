// Generated from JSON.g4 by ANTLR 4.7.1
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
        BEGIN_OBJECT=8, END_OBJECT=9, COMMA=10, COLON=11, WS=12, UNKNOWN=13;
    public static String[] channelNames = {
        "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
        "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
        "STRING", "ESC", "UNICODE", "HEX", "SAFECODEPOINT", "NUMBER", "TRUE",
        "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY", "BEGIN_OBJECT", "END_OBJECT",
        "COMMA", "COLON", "INT", "EXP", "WS", "UNKNOWN"
    };

    private static final String[] _LITERAL_NAMES = {
        null, null, null, "'true'", "'false'", "'null'", "'['", "']'", "'{'",
        "'}'", "','", "':'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, "STRING", "NUMBER", "TRUE", "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY",
        "BEGIN_OBJECT", "END_OBJECT", "COMMA", "COLON", "WS", "UNKNOWN"
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
    public String getGrammarFileName() { return "JSON.g4"; }

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
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\u008a\b\1\4\2"+
        "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
        "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
        "\t\22\4\23\t\23\4\24\t\24\3\2\3\2\3\2\7\2-\n\2\f\2\16\2\60\13\2\3\2\3"+
        "\2\3\3\3\3\3\3\3\3\5\38\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3"+
        "\7\5\7E\n\7\3\7\3\7\3\7\6\7J\n\7\r\7\16\7K\5\7N\n\7\3\7\5\7Q\n\7\3\b\3"+
        "\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"+
        "\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\7\21r\n\21"+
        "\f\21\16\21u\13\21\5\21w\n\21\3\22\3\22\5\22{\n\22\3\22\3\22\3\23\6\23"+
        "\u0080\n\23\r\23\16\23\u0081\3\23\3\23\3\24\6\24\u0087\n\24\r\24\16\24"+
        "\u0088\2\2\25\3\3\5\2\7\2\t\2\13\2\r\4\17\5\21\6\23\7\25\b\27\t\31\n\33"+
        "\13\35\f\37\r!\2#\2%\16\'\17\3\2\13\n\2$$\61\61^^ddhhppttvv\5\2\62;CH"+
        "ch\5\2\2!$$^^\3\2\62;\3\2\63;\4\2GGgg\4\2--//\5\2\13\f\17\17\"\"\f\2\13"+
        "\f\17\17\"\"$$./\62<]]__}}\177\177\2\u0090\2\3\3\2\2\2\2\r\3\2\2\2\2\17"+
        "\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
        "\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)"+
        "\3\2\2\2\5\63\3\2\2\2\79\3\2\2\2\t?\3\2\2\2\13A\3\2\2\2\rD\3\2\2\2\17"+
        "R\3\2\2\2\21W\3\2\2\2\23]\3\2\2\2\25b\3\2\2\2\27d\3\2\2\2\31f\3\2\2\2"+
        "\33h\3\2\2\2\35j\3\2\2\2\37l\3\2\2\2!v\3\2\2\2#x\3\2\2\2%\177\3\2\2\2"+
        "\'\u0086\3\2\2\2).\7$\2\2*-\5\5\3\2+-\5\13\6\2,*\3\2\2\2,+\3\2\2\2-\60"+
        "\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2\61\62\7$\2\2\62\4"+
        "\3\2\2\2\63\67\7^\2\2\648\t\2\2\2\658\5\7\4\2\668\5\13\6\2\67\64\3\2\2"+
        "\2\67\65\3\2\2\2\67\66\3\2\2\28\6\3\2\2\29:\7w\2\2:;\5\t\5\2;<\5\t\5\2"+
        "<=\5\t\5\2=>\5\t\5\2>\b\3\2\2\2?@\t\3\2\2@\n\3\2\2\2AB\n\4\2\2B\f\3\2"+
        "\2\2CE\7/\2\2DC\3\2\2\2DE\3\2\2\2EF\3\2\2\2FM\5!\21\2GI\7\60\2\2HJ\t\5"+
        "\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2LN\3\2\2\2MG\3\2\2\2MN\3\2"+
        "\2\2NP\3\2\2\2OQ\5#\22\2PO\3\2\2\2PQ\3\2\2\2Q\16\3\2\2\2RS\7v\2\2ST\7"+
        "t\2\2TU\7w\2\2UV\7g\2\2V\20\3\2\2\2WX\7h\2\2XY\7c\2\2YZ\7n\2\2Z[\7u\2"+
        "\2[\\\7g\2\2\\\22\3\2\2\2]^\7p\2\2^_\7w\2\2_`\7n\2\2`a\7n\2\2a\24\3\2"+
        "\2\2bc\7]\2\2c\26\3\2\2\2de\7_\2\2e\30\3\2\2\2fg\7}\2\2g\32\3\2\2\2hi"+
        "\7\177\2\2i\34\3\2\2\2jk\7.\2\2k\36\3\2\2\2lm\7<\2\2m \3\2\2\2nw\7\62"+
        "\2\2os\t\6\2\2pr\t\5\2\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tw\3\2"+
        "\2\2us\3\2\2\2vn\3\2\2\2vo\3\2\2\2w\"\3\2\2\2xz\t\7\2\2y{\t\b\2\2zy\3"+
        "\2\2\2z{\3\2\2\2{|\3\2\2\2|}\5!\21\2}$\3\2\2\2~\u0080\t\t\2\2\177~\3\2"+
        "\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083"+
        "\3\2\2\2\u0083\u0084\b\23\2\2\u0084&\3\2\2\2\u0085\u0087\n\n\2\2\u0086"+
        "\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2"+
        "\2\2\u0089(\3\2\2\2\17\2,.\67DKMPsvz\u0081\u0088\3\2\3\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
