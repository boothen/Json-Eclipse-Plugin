package com.boothen.jsonedit.editor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Tracks changes and stores them for later retrieval.
 */
public class RecordingParseTreeChangeListener implements ParseTreeChangeListener {

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
