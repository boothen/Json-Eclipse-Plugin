package com.boothen.jsonedit.editor.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.text.Position;

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;

/**
 * Compares two parse trees.
 */
public class ParseTreeComparator {

    private JsonContext oldRoot;
    private Map<ParseTree, Position> oldPositions = Collections.emptyMap();

    /**
     * Requires the positions map to by ordered as they are found in the tree.
     * An alternative implementation could iterate over the new syntrax tree to avoid that.
     * @param syntaxTree the syntax tree
     * @param positions an <b>ordered</b> map.
     * @return a mapping from old to new tree elements
     */
    public Map<ParseTree, ParseTree> update(JsonContext syntaxTree, LinkedHashMap<ParseTree, Position> positions) {

        Map<ParseTree, ParseTree> oldToNew = new LinkedHashMap<>();
        if (oldRoot != null) {
            compareTrees(positions, oldPositions, oldToNew);
        }

        oldRoot = syntaxTree;
        oldPositions = positions;

        return oldToNew;
    }


    private void compareTrees(LinkedHashMap<ParseTree, Position> newPositions,
            Map<ParseTree, Position> oldPositions, Map<ParseTree, ParseTree> oldToNew) {

        Map<ParseTree, Position> copyNew = new LinkedHashMap<>(newPositions);
        Map<ParseTree, Position> copyOld = new LinkedHashMap<>(oldPositions);

        // matching elements are removed from the corresponding map to avoid that
        // an element is matched twice.
        // Attention must be paid to ValueContext instances, since they have identical segment bounds
        // as their only child.
        Iterator<Entry<ParseTree, Position>> itNew = copyNew.entrySet().iterator();
        while (itNew.hasNext()) {
            Entry<ParseTree, Position> entryNew = itNew.next();

            Iterator<Entry<ParseTree, Position>> itOld = copyOld.entrySet().iterator();
            while (itOld.hasNext()) {
                Entry<ParseTree, Position> entryOld = itOld.next();
                if (samePosition(entryNew.getValue(), entryOld.getValue())) {
                    oldToNew.put(entryOld.getKey(), entryNew.getKey());
                    itOld.remove();
                    itNew.remove();
                    break;
                }
            }
        }
    }

    private static boolean samePosition(Position a, Position b) {
        return a.getOffset() + a.getLength() == b.getOffset() + b.getLength();
    }
}
