// Generated from JSON.g4 by ANTLR 4.5.2
package com.boothen.jsonedit.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSONLexer extends Lexer {
    static { RuntimeMetaData.checkVersion("4.5.2", RuntimeMetaData.VERSION); }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    public static final int
        STRING=1, NUMBER=2, TRUE=3, FALSE=4, NULL=5, BEGIN_ARRAY=6, END_ARRAY=7,
        BEGIN_OBJECT=8, END_OBJECT=9, COMMA=10, COLON=11, WS=12;
    public static String[] modeNames = {
        "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
        "STRING", "ESC", "UNICODE", "HEX", "NUMBER", "TRUE", "FALSE", "NULL",
        "BEGIN_ARRAY", "END_ARRAY", "BEGIN_OBJECT", "END_OBJECT", "COMMA", "COLON",
        "INT", "EXP", "WS"
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
    public String[] getModeNames() { return modeNames; }

    @Override
    public ATN getATN() { return _ATN; }

    public static final String _serializedATN =
        "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16\u0088\b\1\4\2"+
        "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
        "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
        "\t\22\3\2\3\2\3\2\7\2)\n\2\f\2\16\2,\13\2\3\2\3\2\3\3\3\3\3\3\5\3\63\n"+
        "\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\5\6>\n\6\3\6\3\6\3\6\6\6C\n\6\r"+
        "\6\16\6D\3\6\5\6H\n\6\3\6\5\6K\n\6\3\6\3\6\3\6\3\6\5\6Q\n\6\3\6\5\6T\n"+
        "\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n"+
        "\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\7\20"+
        "u\n\20\f\20\16\20x\13\20\5\20z\n\20\3\21\3\21\5\21~\n\21\3\21\3\21\3\22"+
        "\6\22\u0083\n\22\r\22\16\22\u0084\3\22\3\22\2\2\23\3\3\5\2\7\2\t\2\13"+
        "\4\r\5\17\6\21\7\23\b\25\t\27\n\31\13\33\f\35\r\37\2!\2#\16\3\2\n\4\2"+
        "$$^^\n\2$$\61\61^^ddhhppttvv\5\2\62;CHch\3\2\62;\3\2\63;\4\2GGgg\4\2-"+
        "-//\5\2\13\f\17\17\"\"\u0090\2\3\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
        "\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
        "\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5/\3\2\2\2\7\64\3"+
        "\2\2\2\t:\3\2\2\2\13S\3\2\2\2\rU\3\2\2\2\17Z\3\2\2\2\21`\3\2\2\2\23e\3"+
        "\2\2\2\25g\3\2\2\2\27i\3\2\2\2\31k\3\2\2\2\33m\3\2\2\2\35o\3\2\2\2\37"+
        "y\3\2\2\2!{\3\2\2\2#\u0082\3\2\2\2%*\7$\2\2&)\5\5\3\2\')\n\2\2\2(&\3\2"+
        "\2\2(\'\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+-\3\2\2\2,*\3\2\2\2-.\7"+
        "$\2\2.\4\3\2\2\2/\62\7^\2\2\60\63\t\3\2\2\61\63\5\7\4\2\62\60\3\2\2\2"+
        "\62\61\3\2\2\2\63\6\3\2\2\2\64\65\7w\2\2\65\66\5\t\5\2\66\67\5\t\5\2\67"+
        "8\5\t\5\289\5\t\5\29\b\3\2\2\2:;\t\4\2\2;\n\3\2\2\2<>\7/\2\2=<\3\2\2\2"+
        "=>\3\2\2\2>?\3\2\2\2?@\5\37\20\2@B\7\60\2\2AC\t\5\2\2BA\3\2\2\2CD\3\2"+
        "\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FH\5!\21\2GF\3\2\2\2GH\3\2\2\2HT\3\2"+
        "\2\2IK\7/\2\2JI\3\2\2\2JK\3\2\2\2KL\3\2\2\2LM\5\37\20\2MN\5!\21\2NT\3"+
        "\2\2\2OQ\7/\2\2PO\3\2\2\2PQ\3\2\2\2QR\3\2\2\2RT\5\37\20\2S=\3\2\2\2SJ"+
        "\3\2\2\2SP\3\2\2\2T\f\3\2\2\2UV\7v\2\2VW\7t\2\2WX\7w\2\2XY\7g\2\2Y\16"+
        "\3\2\2\2Z[\7h\2\2[\\\7c\2\2\\]\7n\2\2]^\7u\2\2^_\7g\2\2_\20\3\2\2\2`a"+
        "\7p\2\2ab\7w\2\2bc\7n\2\2cd\7n\2\2d\22\3\2\2\2ef\7]\2\2f\24\3\2\2\2gh"+
        "\7_\2\2h\26\3\2\2\2ij\7}\2\2j\30\3\2\2\2kl\7\177\2\2l\32\3\2\2\2mn\7."+
        "\2\2n\34\3\2\2\2op\7<\2\2p\36\3\2\2\2qz\7\62\2\2rv\t\6\2\2su\t\5\2\2t"+
        "s\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2wz\3\2\2\2xv\3\2\2\2yq\3\2\2\2"+
        "yr\3\2\2\2z \3\2\2\2{}\t\7\2\2|~\t\b\2\2}|\3\2\2\2}~\3\2\2\2~\177\3\2"+
        "\2\2\177\u0080\5\37\20\2\u0080\"\3\2\2\2\u0081\u0083\t\t\2\2\u0082\u0081"+
        "\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
        "\u0086\3\2\2\2\u0086\u0087\b\22\2\2\u0087$\3\2\2\2\20\2(*\62=DGJPSvy}"+
        "\u0084\3\b\2\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
