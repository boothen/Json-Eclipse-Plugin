package com.boothen.jsonedit.outline;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.antlr.JSONParser.PairContext;
import com.boothen.jsonedit.antlr.JSONParser.ValueContext;

/**
 * Visits tree nodes in the JsonContext depending on the node type. Does not recurse.
 */
class JsonContextLabelVisitor extends JSONBaseVisitor<StyledString> {

    @Override
    public StyledString visitObject(ObjectContext ctx) {
        return getStyledString(NodeType.OBJECT, "Object");
    }

    @Override
    public StyledString visitArray(ArrayContext ctx) {
        return getStyledString(NodeType.ARRAY, "Array");
    }

    @Override
    public StyledString visitPair(PairContext ctx) {
        String text = ctx.STRING().getText();
        ValueContext value = ctx.value();

        TerminalNode node = value.getChild(TerminalNode.class, 0);
        if (node != null) {
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
            if (type != null) {
                return getStyledString(type, text, symbol.getText());
            }
        }

        return new StyledString(text);
    }

    @Override
    public StyledString visitValue(ValueContext ctx) {
        return getStyledString(NodeType.ARRAY, "Value");
    }

    @Override
    public StyledString visitErrorNode(ErrorNode node) {
        return getStyledString(NodeType.ERROR, "Error");
    }

    private StyledString getStyledString(NodeType type, String value) {
        String fgColor = type.getForegroundColor();
        String bkColor = type.getBackgroundColor();
        Styler styler = StyledString.createColorRegistryStyler(fgColor, bkColor);
        return new StyledString(value, styler);
    }

    private StyledString getStyledString(NodeType type, String key, String value) {
        String fgColor = type.getForegroundColor();
        String bkColor = type.getBackgroundColor();

        StyledString text = new StyledString();
        text.append(key);
        text.append(": ");
        text.append(value, StyledString.createColorRegistryStyler(fgColor, bkColor));
        return text;
    }
}
