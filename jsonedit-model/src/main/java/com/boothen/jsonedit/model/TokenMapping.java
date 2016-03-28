package com.boothen.jsonedit.model;

/**
 * Maps a ANTLR token type to an object
 */
public interface TokenMapping {

    /**
     * @param currentTokenType the type of the encountered token
     * @param previousTokenType the type of the previously encountered token
     * @return an implementation-dependent object
     */
    Object apply(int currentTokenType, int previousTokenType);

}
