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

    private JsonContext oldTree;
    private Map<ParseTree, Position> oldPositions = Collections.emptyMap();

    public void update(JsonContext syntaxTree, Map<ParseTree, Position> positions) {

        if (oldTree != null) {
            compareTrees(syntaxTree, oldTree, positions, oldPositions);
        }

        oldTree = syntaxTree;
        oldPositions = positions;
    }

    private static void compareTrees(ParseTree newTree, ParseTree oldTree,
            Map<ParseTree, Position> newPositions, Map<ParseTree, Position> oldPositions) {

        int newIdx = 0;
        int oldIdx = 0;

        int maxCount = Math.max(oldTree.getChildCount(), newTree.getChildCount());
        for (int i = 0; i < maxCount; i++) {
            ParseTree newChild = newTree.getChild(newIdx);
            ParseTree oldChild = oldTree.getChild(oldIdx);

            Position newPos = newPositions.get(newChild);
            Position oldPos = oldPositions.get(oldChild);

            if (Objects.equals(newPos, oldPos)) {
                compareTrees(newChild, oldChild, newPositions, oldPositions);
                newIdx++;
                oldIdx++;
            } else {
                if (oldPos == null || !oldPos.isDeleted && newPos.offset < oldPos.offset) {
                    nodeAdded(newChild);
                    newIdx++;
                } else {
                    nodeRemoved(oldChild);
                    oldIdx++;
                }
            }
        }
    }

    private static void nodeAdded(ParseTree tree) {
        report("Added: " + tree.getClass().getSimpleName() + tree.getText());
    }

    private static void nodeRemoved(ParseTree tree) {
        report("REMOVED: " + tree.getClass().getSimpleName() + " - " +  tree.getText());
    }

    /**
     * @param string
     */
    private static void report(String string) {
        StatusManager.getManager().handle(new Status(IStatus.INFO, Activator.PLUGIN_ID, string));
    }
}
