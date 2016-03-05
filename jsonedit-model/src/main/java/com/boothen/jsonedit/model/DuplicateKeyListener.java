package com.boothen.jsonedit.model;

import org.antlr.v4.runtime.Token;

/**
 * Implementor will notified when duplicate entries are encountered.
 */
public interface DuplicateKeyListener {

    public void reportDuplicate(String key, Token first, Token second);
}
