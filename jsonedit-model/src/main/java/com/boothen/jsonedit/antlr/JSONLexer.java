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
        BEGIN_OBJECT=8, END_OBJECT=9, COMMA=10, COLON=11, WS=12;
    public static String[] channelNames = {
        "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
        "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
        "STRING", "ESC", "UNICODE", "HEX", "SAFECODEPOINT", "NUMBER", "TRUE",
        "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY", "BEGIN_OBJECT", "END_OBJECT",
        "COMMA", "COLON", "INT", "EXP", "WS"
    };

    private static final String[] _LITERAL_NAMES = {
        null, null, null, "'true'", "'false'", "'null'", "'['", "']'", "'{'",
        "'}'", "','", "':'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, "STRING", "NUMBER", "TRUE", "FALSE", "NULL", "BEGIN_ARRAY", "END_ARRAY",
        "BEGIN_OBJECT", "END_OBJECT", "COMMA", "COLON", "WS"
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
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16\u0083\b\1\4\2"+
        "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
        "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
        "\t\22\4\23\t\23\3\2\3\2\3\2\7\2+\n\2\f\2\16\2.\13\2\3\2\3\2\3\3\3\3\3"+
        "\3\3\3\5\3\66\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\5\7C\n\7"+
        "\3\7\3\7\3\7\6\7H\n\7\r\7\16\7I\5\7L\n\7\3\7\5\7O\n\7\3\b\3\b\3\b\3\b"+
        "\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r"+
        "\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\7\21p\n\21\f\21\16\21"+
        "s\13\21\5\21u\n\21\3\22\3\22\5\22y\n\22\3\22\3\22\3\23\6\23~\n\23\r\23"+
        "\16\23\177\3\23\3\23\2\2\24\3\3\5\2\7\2\t\2\13\2\r\4\17\5\21\6\23\7\25"+
        "\b\27\t\31\n\33\13\35\f\37\r!\2#\2%\16\3\2\n\n\2$$\61\61^^ddhhppttvv\5"+
        "\2\62;CHch\5\2\2!$$^^\3\2\62;\3\2\63;\4\2GGgg\4\2--//\5\2\13\f\17\17\""+
        "\"\2\u0088\2\3\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
        "\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
        "\2\37\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2\5\61\3\2\2\2\7\67\3\2\2\2\t=\3\2"+
        "\2\2\13?\3\2\2\2\rB\3\2\2\2\17P\3\2\2\2\21U\3\2\2\2\23[\3\2\2\2\25`\3"+
        "\2\2\2\27b\3\2\2\2\31d\3\2\2\2\33f\3\2\2\2\35h\3\2\2\2\37j\3\2\2\2!t\3"+
        "\2\2\2#v\3\2\2\2%}\3\2\2\2\',\7$\2\2(+\5\5\3\2)+\5\13\6\2*(\3\2\2\2*)"+
        "\3\2\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-/\3\2\2\2.,\3\2\2\2/\60\7$\2\2"+
        "\60\4\3\2\2\2\61\65\7^\2\2\62\66\t\2\2\2\63\66\5\7\4\2\64\66\5\13\6\2"+
        "\65\62\3\2\2\2\65\63\3\2\2\2\65\64\3\2\2\2\66\6\3\2\2\2\678\7w\2\289\5"+
        "\t\5\29:\5\t\5\2:;\5\t\5\2;<\5\t\5\2<\b\3\2\2\2=>\t\3\2\2>\n\3\2\2\2?"+
        "@\n\4\2\2@\f\3\2\2\2AC\7/\2\2BA\3\2\2\2BC\3\2\2\2CD\3\2\2\2DK\5!\21\2"+
        "EG\7\60\2\2FH\t\5\2\2GF\3\2\2\2HI\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JL\3\2\2"+
        "\2KE\3\2\2\2KL\3\2\2\2LN\3\2\2\2MO\5#\22\2NM\3\2\2\2NO\3\2\2\2O\16\3\2"+
        "\2\2PQ\7v\2\2QR\7t\2\2RS\7w\2\2ST\7g\2\2T\20\3\2\2\2UV\7h\2\2VW\7c\2\2"+
        "WX\7n\2\2XY\7u\2\2YZ\7g\2\2Z\22\3\2\2\2[\\\7p\2\2\\]\7w\2\2]^\7n\2\2^"+
        "_\7n\2\2_\24\3\2\2\2`a\7]\2\2a\26\3\2\2\2bc\7_\2\2c\30\3\2\2\2de\7}\2"+
        "\2e\32\3\2\2\2fg\7\177\2\2g\34\3\2\2\2hi\7.\2\2i\36\3\2\2\2jk\7<\2\2k"+
        " \3\2\2\2lu\7\62\2\2mq\t\6\2\2np\t\5\2\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2"+
        "qr\3\2\2\2ru\3\2\2\2sq\3\2\2\2tl\3\2\2\2tm\3\2\2\2u\"\3\2\2\2vx\t\7\2"+
        "\2wy\t\b\2\2xw\3\2\2\2xy\3\2\2\2yz\3\2\2\2z{\5!\21\2{$\3\2\2\2|~\t\t\2"+
        "\2}|\3\2\2\2~\177\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3"+
        "\2\2\2\u0081\u0082\b\23\2\2\u0082&\3\2\2\2\16\2*,\65BIKNqtx\177\3\2\3"+
        "\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
