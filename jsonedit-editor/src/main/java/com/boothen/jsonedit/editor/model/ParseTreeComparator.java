package com.boothen.jsonedit.editor.model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;

/**
 * Compares two parse trees.
 */
public class ParseTreeComparator {

    private JsonContext oldRoot;
    private Map<ParseTree, Position> oldPositions = Collections.emptyMap();

    public void update(JsonContext syntaxTree, Map<ParseTree, Position> positions, ParseTreeChangeListener listener) {

        if (oldRoot != null) {
            compareTrees(syntaxTree, oldRoot, positions, oldPositions, listener);
        }

        oldRoot = syntaxTree;
        oldPositions = positions;
    }

    private void compareTrees(ParseTree newTree, ParseTree oldTree,
            Map<ParseTree, Position> newPositions, Map<ParseTree, Position> oldPositions,
            ParseTreeChangeListener listener) {

        int newIdx = 0;
        int oldIdx = 0;

        int maxCount = Math.max(oldTree.getChildCount(), newTree.getChildCount());
        for (int i = 0; i < maxCount; i++) {
            ParseTree newChild = newTree.getChild(newIdx);
            ParseTree oldChild = oldTree.getChild(oldIdx);

            Position newPos = newPositions.get(newChild);
            Position oldPos = oldPositions.get(oldChild);

            if (Objects.equals(newPos, oldPos)) {
                if (newPos != null && oldPos != null) {
                    // no error nodes
                    compareTrees(newChild, oldChild, newPositions, oldPositions, listener);
                }
                listener.sameNode(oldChild, newChild);
                newIdx++;
                oldIdx++;
            } else {
                if (newPos == null) {
                    // probably a (incomplete) node with an exception and invalid position offsets
                    newIdx++;
                } else if (oldPos == null || !oldPos.isDeleted && newPos.offset < oldPos.offset) {
                    listener.nodeAdded(newChild);
                    newIdx++;
                } else {
                    listener.nodeRemoved(oldChild);
                    oldIdx++;
                }
            }
        }
    }
}
