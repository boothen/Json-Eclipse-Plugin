package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.Token;

/**
 * Implementor will notified when duplicate entries are encountered.
 */
public interface DuplicateKeyListener {

    /**
     * @param key the duplicate key
     * @param first a previous occurrence
     * @param second the latest detected occurrence
     */
    public void reportDuplicate(String key, Token first, Token second);
}
