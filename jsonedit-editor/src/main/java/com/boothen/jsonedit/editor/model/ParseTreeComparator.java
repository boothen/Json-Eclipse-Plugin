package com.boothen.jsonedit.editor.model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.statushandlers.StatusManager;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.editor.Activator;

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
                compareTrees(newChild, oldChild, newPositions, oldPositions, listener);
                newIdx++;
                oldIdx++;
            } else {
                if (oldPos == null || !oldPos.isDeleted && newPos.offset < oldPos.offset) {
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
