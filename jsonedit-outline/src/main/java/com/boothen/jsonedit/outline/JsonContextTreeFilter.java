package com.boothen.jsonedit.outline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.antlr.JSONParser.PairContext;
import com.boothen.jsonedit.antlr.JSONParser.ValueContext;

/**
 * Visits tree nodes in the JsonContext depending on the node type. Does not recurse.
 * Skips {@link ValueContext} instances.
 */
class JsonContextTreeFilter extends JSONBaseVisitor<List<ParseTree>> {

    @Override
    public List<ParseTree> visitJson(JsonContext ctx) {
        return getChildren(ctx);
        // Use the following to skip the root node
//        return visit(ctx.getChild(0));
    }

    @Override
    public List<ParseTree> visitObject(ObjectContext ctx) {
        return getChildren(ctx);
    }

    @Override
    public List<ParseTree> visitArray(ArrayContext ctx) {
        List<ParseTree> sum = new ArrayList<>();
        for (ValueContext child : ctx.value()) {
            if (child.exception == null) {
                sum.add(child.getChild(0));
            }
        }
        return sum;
    }

    @Override
    public List<ParseTree> visitPair(PairContext ctx) {
        if (ctx.exception == null) {
            ValueContext value = ctx.value();
            if (value.exception == null) {
                ParseTree child = value.getChild(0);
                return visit(child);
            }
        }

        return Collections.emptyList();
    }

    @Override
    public List<ParseTree> visitTerminal(TerminalNode node) {
        return Collections.emptyList();
    }

    @Override
    public List<ParseTree> visitErrorNode(ErrorNode node) {
        return Collections.emptyList();
    }

    private List<ParseTree> getChildren(ParseTree ctx) {
        List<ParseTree> children = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if (shouldAdd(child)) {
                children.add(child);
            }
        }
        return children;
    }

    private boolean shouldAdd(ParseTree child) {
        if (child instanceof TerminalNode) {
            return false;
        }
        if (child instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext) child;
            if (ctx.exception != null) {
                return false;
            }
        }
        return true;
    }
}
