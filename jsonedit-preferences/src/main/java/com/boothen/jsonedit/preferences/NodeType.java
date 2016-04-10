package com.boothen.jsonedit.preferences;

import com.boothen.jsonedit.core.preferences.TokenStyle;

/**
 * Maps elements in the syntax tree to image, foreground and background color
 * in the outline view.
 */
public enum NodeType {
    /**
     * An object, wrapped in curly braces
     */
    OBJECT("JsonObject.gif", TokenStyle.DEFAULT),

    /**
     * An array of elements, wrapped in square brackets
     */
    ARRAY("JsonArray.gif", TokenStyle.DEFAULT),

    /**
     * A boolean value
     */
    BOOLEAN("JsonBoolean.gif", TokenStyle.BOOLEAN),

    /**
     * The constant <code>null</code>.
     */
    NULL("JsonNull.gif", TokenStyle.NULL),

    /**
     * A number, either integer or floating point
     */
    NUMBER("JsonNumber.gif", TokenStyle.NUMBER),

    /**
     * A text string
     */
    STRING("JsonString.gif", TokenStyle.TEXT),

    /**
     * An error node
     */
    ERROR("JsonError.gif", TokenStyle.ERROR);

    private String imagePath;
    private TokenStyle tokenStyle;

    private NodeType(String imagePath, TokenStyle tokenStyle) {
        this.imagePath = imagePath;
        this.tokenStyle = tokenStyle;
    }

    /**
     * @return the relative image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @return the token style keys
     */
    public TokenStyle getTokenStyle() {
        return tokenStyle;
    }
}
