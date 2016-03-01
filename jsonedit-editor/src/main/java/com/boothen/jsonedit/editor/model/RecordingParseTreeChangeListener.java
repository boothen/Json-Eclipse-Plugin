package com.boothen.jsonedit.editor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Tracks changes and stores them for later retrieval.
 */
public class RecordingParseTreeChangeListener implements ParseTreeChangeListener {

    private final Map<ParseTree, ParseTree> oldToNew = new HashMap<>();
    private final Collection<ParseTree> added = new ArrayList<>();
    private final Collection<ParseTree> removed = new ArrayList<>();

    @Override
    public void nodeAdded(ParseTree node) {
        added.add(node);
    }

    @Override
    public void nodeRemoved(ParseTree node) {
        removed.add(node);
    }

    @Override
    public void sameNode(ParseTree oldNode, ParseTree newNode) {
        oldToNew.put(oldNode, newNode);
    }

    /**
     * @return an unmodifiable view on the recorded mapping from old to new elements
     */
    public Map<ParseTree, ParseTree> getMapping() {
        return Collections.unmodifiableMap(oldToNew);
    }

    /**
     * @return an unmodifiable view on the recorded changes
     */
    public Collection<ParseTree> getAdded() {
        return Collections.unmodifiableCollection(added);
    }

    /**
     * @return an unmodifiable view on the recorded changes
     */
    public Collection<ParseTree> getRemoved() {
        return Collections.unmodifiableCollection(removed);
    }
}
