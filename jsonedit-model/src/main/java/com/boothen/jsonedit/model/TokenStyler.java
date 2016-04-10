package com.boothen.jsonedit.model;

import org.eclipse.jface.text.TextAttribute;

import com.boothen.jsonedit.core.preferences.TokenStyle;

/**
 * Defines and applies text representations for {@link TokenStyle} instances.
 */
public interface TokenStyler {

    /**
     * @param style the token style
     * @return a JFace text attribute that defines the styling (color, font modifiers) for the given token
     */
    TextAttribute apply(TokenStyle style);

}
