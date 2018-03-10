// Generated from JSON.g4 by ANTLR 4.7.1
package com.boothen.jsonedit.antlr;
import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSONParser extends Parser {
    static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    public static final int
        STRING=1, NUMBER=2, TRUE=3, FALSE=4, NULL=5, BEGIN_ARRAY=6, END_ARRAY=7,
        BEGIN_OBJECT=8, END_OBJECT=9, COMMA=10, COLON=11, WS=12, LINE_COMMENT=13,
        BLOCK_COMMENT=14, UNKNOWN=15;
    public static final int
        RULE_json = 0, RULE_object = 1, RULE_pair = 2, RULE_array = 3, RULE_value = 4;
    public static final String[] ruleNames = {
        "json", "object", "pair", "array", "value"
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

    @Override
    public String getGrammarFileName() { return "JSON.g4"; }

    @Override
    public String[] getRuleNames() { return ruleNames; }

    @Override
    public String getSerializedATN() { return _serializedATN; }

    @Override
    public ATN getATN() { return _ATN; }

    public JSONParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
    }
    public static class JsonContext extends ParserRuleContext {
        public ValueContext value() {
            return getRuleContext(ValueContext.class,0);
        }
        public JsonContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_json; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).enterJson(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).exitJson(this);
        }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitJson(this);
            else return visitor.visitChildren(this);
        }
    }

    public final JsonContext json() throws RecognitionException {
        JsonContext _localctx = new JsonContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_json);
        try {
            enterOuterAlt(_localctx, 1);
            {
            setState(10);
            value();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ObjectContext extends ParserRuleContext {
        public List<PairContext> pair() {
            return getRuleContexts(PairContext.class);
        }
        public PairContext pair(int i) {
            return getRuleContext(PairContext.class,i);
        }
        public ObjectContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_object; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).enterObject(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).exitObject(this);
        }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitObject(this);
            else return visitor.visitChildren(this);
        }
        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + getText() + "]";
        }
    }

    public final ObjectContext object() throws RecognitionException {
        ObjectContext _localctx = new ObjectContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_object);
        int _la;
        try {
            setState(25);
            _errHandler.sync(this);
            switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
            case 1:
                enterOuterAlt(_localctx, 1);
                {
                setState(12);
                match(BEGIN_OBJECT);
                setState(13);
                pair();
                setState(18);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la==COMMA) {
                    {
                    {
                    setState(14);
                    match(COMMA);
                    setState(15);
                    pair();
                    }
                    }
                    setState(20);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(21);
                match(END_OBJECT);
                }
                break;
            case 2:
                enterOuterAlt(_localctx, 2);
                {
                setState(23);
                match(BEGIN_OBJECT);
                setState(24);
                match(END_OBJECT);
                }
                break;
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    public static class PairContext extends ParserRuleContext {
        public TerminalNode STRING() { return getToken(JSONParser.STRING, 0); }
        public ValueContext value() {
            return getRuleContext(ValueContext.class,0);
        }
        public PairContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_pair; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).enterPair(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).exitPair(this);
        }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitPair(this);
            else return visitor.visitChildren(this);
        }
        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + getText() + "]";
        }
    }

    public final PairContext pair() throws RecognitionException {
        PairContext _localctx = new PairContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_pair);
        try {
            enterOuterAlt(_localctx, 1);
            {
            setState(27);
            match(STRING);
            setState(28);
            match(COLON);
            setState(29);
            value();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ArrayContext extends ParserRuleContext {
        public List<ValueContext> value() {
            return getRuleContexts(ValueContext.class);
        }
        public ValueContext value(int i) {
            return getRuleContext(ValueContext.class,i);
        }
        public ArrayContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_array; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).enterArray(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).exitArray(this);
        }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitArray(this);
            else return visitor.visitChildren(this);
        }
        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + getText() + "]";
        }
    }

    public final ArrayContext array() throws RecognitionException {
        ArrayContext _localctx = new ArrayContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_array);
        int _la;
        try {
            setState(44);
            _errHandler.sync(this);
            switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
            case 1:
                enterOuterAlt(_localctx, 1);
                {
                setState(31);
                match(BEGIN_ARRAY);
                setState(32);
                value();
                setState(37);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la==COMMA) {
                    {
                    {
                    setState(33);
                    match(COMMA);
                    setState(34);
                    value();
                    }
                    }
                    setState(39);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(40);
                match(END_ARRAY);
                }
                break;
            case 2:
                enterOuterAlt(_localctx, 2);
                {
                setState(42);
                match(BEGIN_ARRAY);
                setState(43);
                match(END_ARRAY);
                }
                break;
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ValueContext extends ParserRuleContext {
        public TerminalNode STRING() { return getToken(JSONParser.STRING, 0); }
        public TerminalNode NUMBER() { return getToken(JSONParser.NUMBER, 0); }
        public ObjectContext object() {
            return getRuleContext(ObjectContext.class,0);
        }
        public ArrayContext array() {
            return getRuleContext(ArrayContext.class,0);
        }
        public ValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_value; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).enterValue(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof JSONListener ) ((JSONListener)listener).exitValue(this);
        }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitValue(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ValueContext value() throws RecognitionException {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_value);
        try {
            setState(53);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
            case STRING:
                enterOuterAlt(_localctx, 1);
                {
                setState(46);
                match(STRING);
                }
                break;
            case NUMBER:
                enterOuterAlt(_localctx, 2);
                {
                setState(47);
                match(NUMBER);
                }
                break;
            case BEGIN_OBJECT:
                enterOuterAlt(_localctx, 3);
                {
                setState(48);
                object();
                }
                break;
            case BEGIN_ARRAY:
                enterOuterAlt(_localctx, 4);
                {
                setState(49);
                array();
                }
                break;
            case TRUE:
                enterOuterAlt(_localctx, 5);
                {
                setState(50);
                match(TRUE);
                }
                break;
            case FALSE:
                enterOuterAlt(_localctx, 6);
                {
                setState(51);
                match(FALSE);
                }
                break;
            case NULL:
                enterOuterAlt(_localctx, 7);
                {
                setState(52);
                match(NULL);
                }
                break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21:\4\2\t\2\4\3\t"+
        "\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\3\3\3\3\3\3\3\7\3\23\n\3\f\3\16\3"+
        "\26\13\3\3\3\3\3\3\3\3\3\5\3\34\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\7"+
        "\5&\n\5\f\5\16\5)\13\5\3\5\3\5\3\5\3\5\5\5/\n\5\3\6\3\6\3\6\3\6\3\6\3"+
        "\6\3\6\5\68\n\6\3\6\2\2\7\2\4\6\b\n\2\2\2>\2\f\3\2\2\2\4\33\3\2\2\2\6"+
        "\35\3\2\2\2\b.\3\2\2\2\n\67\3\2\2\2\f\r\5\n\6\2\r\3\3\2\2\2\16\17\7\n"+
        "\2\2\17\24\5\6\4\2\20\21\7\f\2\2\21\23\5\6\4\2\22\20\3\2\2\2\23\26\3\2"+
        "\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\27\3\2\2\2\26\24\3\2\2\2\27\30\7\13"+
        "\2\2\30\34\3\2\2\2\31\32\7\n\2\2\32\34\7\13\2\2\33\16\3\2\2\2\33\31\3"+
        "\2\2\2\34\5\3\2\2\2\35\36\7\3\2\2\36\37\7\r\2\2\37 \5\n\6\2 \7\3\2\2\2"+
        "!\"\7\b\2\2\"\'\5\n\6\2#$\7\f\2\2$&\5\n\6\2%#\3\2\2\2&)\3\2\2\2\'%\3\2"+
        "\2\2\'(\3\2\2\2(*\3\2\2\2)\'\3\2\2\2*+\7\t\2\2+/\3\2\2\2,-\7\b\2\2-/\7"+
        "\t\2\2.!\3\2\2\2.,\3\2\2\2/\t\3\2\2\2\608\7\3\2\2\618\7\4\2\2\628\5\4"+
        "\3\2\638\5\b\5\2\648\7\5\2\2\658\7\6\2\2\668\7\7\2\2\67\60\3\2\2\2\67"+
        "\61\3\2\2\2\67\62\3\2\2\2\67\63\3\2\2\2\67\64\3\2\2\2\67\65\3\2\2\2\67"+
        "\66\3\2\2\28\13\3\2\2\2\7\24\33\'.\67";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
