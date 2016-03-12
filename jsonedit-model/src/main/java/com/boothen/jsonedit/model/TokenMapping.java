package com.boothen.jsonedit.model;

/**
 * TODO: describe
 */
public interface TokenMapping {

    Object apply(int currentTokenType, int previousTokenType);

}
