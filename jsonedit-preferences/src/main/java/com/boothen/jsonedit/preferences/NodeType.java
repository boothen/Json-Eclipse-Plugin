package com.boothen.jsonedit.preferences;

/**
 * Maps elements in the syntax tree to image, foreground and background color
 * in the outline view.
 */
public enum NodeType {
    /**
     * An object, wrapped in curly braces
     */
    OBJECT("JsonObject.gif", "BLUE", "WHITE"),

    /**
     * An array of elements, wrapped in square brackets
     */
    ARRAY("JsonArray.gif", "BLUE", "WHITE"),
    /**
     * A boolean value
     */
    BOOLEAN("JsonBoolean.gif", "BLACK", "WHITE"),

    /**
     * The constant <code>null</code>.
     */
    NULL("JsonNull.gif", "BLACK", "WHITE"),

    /**
     * A number, either integer or floating point
     */
    NUMBER("JsonNumber.gif", "BLUE", "WHITE"),

    /**
     * A text string
     */
    STRING("JsonString.gif", "GREEN", "WHITE"),

    /**
     * An error node
     */
    ERROR("JsonError.gif", "RED", "WHITE");

    private String imagePath;
    private String foregroundColor;
    private String backgroundColor;

    private NodeType(String imagePath, String foregroundColor, String backgroundColor) {
        this.imagePath = imagePath;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the relative image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @return the foreground color
     */
    public String getForegroundColor() {
        return foregroundColor;
    }

    /**
     * @return the background color
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }
}
