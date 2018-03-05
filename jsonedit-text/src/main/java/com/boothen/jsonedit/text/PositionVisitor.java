package com.boothen.jsonedit.text;

import java.util.LinkedHashMap;

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
 * Explicitly returns a {@link LinkedHashMap} to indicate that elements are ordered as they
 * are encountered by depth first traversal.
 */
public class PositionVisitor extends AbstractParseTreeVisitor<LinkedHashMap<ParseTree, Position>> {

    private final LinkedHashMap<ParseTree, Position> positions = new LinkedHashMap<>();

    @Override
    public LinkedHashMap<ParseTree, Position> visitChildren(RuleNode node) {
        ParserRuleContext ctx = (ParserRuleContext) node;

        // Add successful rule matches only
        // TODO: maybe just skip over this element on exceptions and try to add children
        if (ctx.exception == null) {
            positions.put(node, createPosition(ctx.start, ctx.stop));

            for (int i=0; i<node.getChildCount(); i++) {
                node.getChild(i).accept(this);
            }
        }

        return positions;
    }

    @Override
    public LinkedHashMap<ParseTree, Position> visitTerminal(TerminalNode node) {
        Token symbol = node.getSymbol();
        positions.put(node, createPosition(symbol, symbol));
        return positions;
    }

    @Override
    public LinkedHashMap<ParseTree, Position> visitErrorNode(ErrorNode node) {
        // ignore error tokens - they have invalid positions
        return positions;
    }

    private Position createPosition(Token start, Token stop) {
        int startIndex = start.getStartIndex();
        // stop token is null if the whole tree is just EOF (document out of sync)
        int stopIndex = stop != null ? stop.getStopIndex() : startIndex - 1;
        Position pos = new Position(startIndex, stopIndex - startIndex + 1);
        return pos;
    }
}

