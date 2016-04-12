package com.boothen.jsonedit.folding;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;

/**
 * Creates {@link Position} instances that wrap around inner nodes in the syntax tree.
 * The root element is excluded explicitly.
 */
class FoldingVisitor extends JSONBaseVisitor<Void> {

    private final List<Position> positions;

    /**
     * @param positions an (empty) list that will be filled during tree traversal
     */
    public FoldingVisitor(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public Void visitObject(ObjectContext ctx) {
        if (isFoldingPoint(ctx)) {
            positions.add(createPosition(ctx));
        }
        return super.visitObject(ctx);
    }

    @Override
    public Void visitArray(ArrayContext ctx) {
        if (isFoldingPoint(ctx)) {
            positions.add(createPosition(ctx));
        }
        return super.visitArray(ctx);
    }

    private Position createPosition(ParserRuleContext ctx) {
        int startIndex = ctx.start.getStartIndex();
        int stopIndex = ctx.stop.getStopIndex();
        Position pos = new Position(startIndex, stopIndex - startIndex);
        return pos;
    }

    private boolean isFoldingPoint(ParserRuleContext ctx) {
        if (ctx.exception != null) {
            return false;
        }

        // the root element is at depth 1 and not part of the traversal
        // it contains one child with identical start/stop tokens
        // we filter it out to avoid folding the entire file
        if (ctx.depth() <= 2) {
            return false;
        }

        // Skip elements that span less than two lines
        return ctx.start.getLine() < ctx.stop.getLine() - 1;
    }
}

