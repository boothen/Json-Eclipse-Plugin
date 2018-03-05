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
     * @return a segment describing the corresponding text range in the document or <code>null</code>.
     */
    public static Segment getSegment(ParseTree treeNode) {
        if (treeNode instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext) treeNode;
            if (ctx.exception == null) {
                int start = ctx.start.getStartIndex();
                // stop token is null if the whole tree is just EOF (document out of sync)
                int stop = (ctx.stop != null) ? ctx.stop.getStopIndex() : start;
                return new Segment(start, stop);
            }
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
