package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 */
public class ParseTreeInfo {

    /**
     * @param treeNode the node to inspect
     * @return a segment describing the corresponding text range in the document
     */
    public static Segment getSegment(ParseTree treeNode) {
        if (treeNode instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext) treeNode;
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            return new Segment(start, stop);
        }
        if (treeNode instanceof TerminalNode) {
            TerminalNode t = (TerminalNode) treeNode;
            int start = t.getSymbol().getStartIndex();
            int stop = t.getSymbol().getStopIndex();
            return new Segment(start, stop);
        }

        return null;
    }
}
