package com.boothen.jsonedit.model;

import java.util.Map;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;

/**
 * Recurses into tree finding the smallest node that fully contains the specified text segment.
 */
public class JsonContextTokenFinder extends JSONBaseVisitor<ParseTree> {

    private int textStart;
    private int textStop;
    private Map<ParseTree, Position> positions;

    /**
     * Takes a range of selected text
     * @param textStart the text start marker
     * @param textStop the text stop marker
     * @param positions maps tree elements to document positions
     */
    public JsonContextTokenFinder(int textStart, int textStop, Map<ParseTree, Position> positions) {
        this.textStart = textStart;
        this.textStop = textStop;
        this.positions = positions;
    }

    @Override
    public ParseTree visitChildren(RuleNode node) {
        if (!fullyInside(node)) {
            return null;
        }

        // node matches, but we try to find a child
        // element that fits even better
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree child = node.getChild(i);
            ParseTree childResult = child.accept(this);
            if (childResult != null) {
                return childResult;
            }
        }

        return node;
    }

    @Override
    public ParseTree visitTerminal(TerminalNode node) {
        return fullyInside(node) ? node : null;
    }

    @Override
    public ParseTree visitErrorNode(ErrorNode node) {
        // TODO examine what to do here
        return super.visitErrorNode(node);
    }

    private boolean fullyInside(ParseTree treeNode) {
        Position segment = positions.get(treeNode);
        if (segment == null) {
            // bail out on an erroneous and unknown nodes
            return false;
        }

        return (segment.getOffset() <= textStart && textStop <= segment.getOffset() + segment.getLength());
    }
}
