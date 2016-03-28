package com.boothen.jsonedit.editor.model;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.text.Position;

/**
 * Creates {@link Position} instances that wrap around every node in the syntax tree.
 */
class PositionVisitor extends AbstractParseTreeVisitor<Map<ParseTree, Position>> {

    private final Map<ParseTree, Position> positions = new HashMap<>();

    @Override
    public Map<ParseTree, Position> visitChildren(RuleNode node) {
        ParserRuleContext ctx = (ParserRuleContext) node;

        // Add successful rule matches only
        if (ctx.exception == null) {
            positions.put(node, createPosition(ctx.start, ctx.stop));

            for (int i=0; i<node.getChildCount(); i++) {
                node.getChild(i).accept(this);
            }
        }

        return positions;
    }

    @Override
    public Map<ParseTree, Position> visitTerminal(TerminalNode node) {
        Token symbol = node.getSymbol();
        positions.put(node, createPosition(symbol, symbol));
        return positions;
    }

    @Override
    public Map<ParseTree, Position> visitErrorNode(ErrorNode node) {
        // ignore error tokens - they have invalid positions
        return positions;
    }

    private Position createPosition(Token start, Token stop) {
        int startIndex = start.getStartIndex();
        int stopIndex = stop.getStopIndex();
        Position pos = new Position(startIndex, stopIndex - startIndex + 1);
        return pos;
    }
}

