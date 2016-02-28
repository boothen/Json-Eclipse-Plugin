package com.boothen.jsonedit.outline;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.antlr.JSONParser.PairContext;
import com.boothen.jsonedit.antlr.JSONParser.ValueContext;

/**
 * Visits tree nodes in the JsonContext depending on the node type. Does not recurse.
 */
class JsonNodeTypeVisitor extends JSONBaseVisitor<NodeType> {

    @Override
    public NodeType visitJson(JsonContext ctx) {
        return NodeType.ROOT;
    }

    @Override
    public NodeType visitObject(ObjectContext ctx) {
        return NodeType.OBJECT;
    }

    @Override
    public NodeType visitArray(ArrayContext ctx) {
        return NodeType.ARRAY;
    }

    @Override
    public NodeType visitErrorNode(ErrorNode node) {
        return NodeType.ERROR;
    }

    @Override
    public NodeType visitPair(PairContext ctx) {
        ValueContext value = ctx.value();

        TerminalNode node = value.getChild(TerminalNode.class, 0);
        if (node != null) {
            return visitTerminal(node);
        }

        return visit(value.getChild(0));
    }

    @Override
    public NodeType visitTerminal(TerminalNode node) {
        switch (node.getSymbol().getType()) {
        case JSONParser.NUMBER:
            return NodeType.NUMBER;
            case JSONParser.TRUE:
            case JSONParser.FALSE:
                return NodeType.BOOLEAN;
            case JSONParser.STRING:
                return NodeType.STRING;
            case JSONParser.NULL:
                return NodeType.NULL;
            default:
                return null;
        }
    }
}
