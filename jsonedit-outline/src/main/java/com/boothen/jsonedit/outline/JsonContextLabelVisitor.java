package com.boothen.jsonedit.outline;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.TextStyle;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.antlr.JSONParser.PairContext;
import com.boothen.jsonedit.antlr.JSONParser.ValueContext;
import com.boothen.jsonedit.preferences.JsonTokenStyler;
import com.boothen.jsonedit.preferences.NodeType;

/**
 * Visits tree nodes in the JsonContext depending on the node type. Does not recurse.
 */
class JsonContextLabelVisitor extends JSONBaseVisitor<StyledString> {

    private final JsonTokenStyler tokenStyler;

    /**
     * @param preferenceStore the preference store that defines the text style
     */
    public JsonContextLabelVisitor(IPreferenceStore preferenceStore) {
        this.tokenStyler = new JsonTokenStyler(preferenceStore);
    }

    @Override
    public StyledString visitObject(ObjectContext ctx) {
        return getStyledString(NodeType.OBJECT, "Object");
    }

    @Override
    public StyledString visitArray(ArrayContext ctx) {
        return getStyledString(NodeType.ARRAY, "Array");
    }

    @Override
    public StyledString visitErrorNode(ErrorNode node) {
        return getStyledString(NodeType.ERROR, "Error");
    }

    @Override
    public StyledString visitPair(PairContext ctx) {
        String key = ctx.STRING().getText();
        ValueContext value = ctx.value();

        TerminalNode node = value.getChild(TerminalNode.class, 0);
        if (node != null) {
            NodeType type = getType(node);
            if (type != null) {
                return getStyledString(type, key, node.getText());
            }
        }

        return new StyledString(key);
    }

    @Override
    public StyledString visitTerminal(TerminalNode node) {
        NodeType type = getType(node);
        if (type != null) {
            return getStyledString(type, node.getText());
        }
        return new StyledString();
    }

    private NodeType getType(TerminalNode node) {
        NodeType type = null;
        Token symbol = node.getSymbol();
        switch (symbol.getType()) {
            case JSONParser.STRING:
                type = NodeType.STRING;
                break;

            case JSONParser.NULL:
                type = NodeType.NULL;
                break;

            case JSONParser.NUMBER:
                type = NodeType.NUMBER;
                break;

            case JSONParser.TRUE:
            case JSONParser.FALSE:
                type = NodeType.BOOLEAN;
                break;
        }
        return type;
    }

    private StyledString getStyledString(NodeType type, String value) {
        return getStyledString(type, null, value);
    }

    private StyledString getStyledString(NodeType type, String key, String value) {
        TextAttribute attribs = tokenStyler.apply(type.getTokenStyle());

        StyledString text = new StyledString();
        if (key != null) {
            text.append(key);
            text.append(": ");
        }
        text.append(value, new TextAttributeStyler(attribs));
        return text;
    }

    /**
     * A {@link Styler} that wraps {@link TextAttribute}.
     */
    private static class TextAttributeStyler extends Styler {
        private TextAttribute textAttribute;

        public TextAttributeStyler(TextAttribute attrib) {
            this.textAttribute = attrib;
        }

        @Override
        public void applyStyles(TextStyle textStyle) {
            if (textStyle instanceof StyleRange) {
                // for some reason, this does not have any effect
                final StyleRange range = (StyleRange) textStyle;
                range.fontStyle = textAttribute.getStyle();
            }

            textStyle.foreground = textAttribute.getForeground();
            textStyle.background = textAttribute.getBackground();
        }
    }
}
