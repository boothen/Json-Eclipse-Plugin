package com.boothen.jsonedit.preferences;

/**
 * Maps elements in the syntax tree to image, foreground and background color
 * in the outline view.
 */
public enum NodeType {
    /**
     * An object, wrapped in curly braces
     */
    OBJECT("JsonObject.gif", "BLUE"),

    /**
     * An array of elements, wrapped in square brackets
     */
    ARRAY("JsonArray.gif", "BLUE"),
    /**
     * A boolean value
     */
    BOOLEAN("JsonBoolean.gif", "BLACK"),

    /**
     * The constant <code>null</code>.
     */
    NULL("JsonNull.gif", "BLACK"),

    /**
     * A number, either integer or floating point
     */
    NUMBER("JsonNumber.gif", "BLUE"),

    /**
     * A text string
     */
    STRING("JsonString.gif", "GREEN"),

    /**
     * An error node
     */
    ERROR("JsonError.gif", "RED");

    private String imagePath;
    private String foregroundColor;
    private String backgroundColor;

    private NodeType(String imagePath, String foregroundColor) {
        this.imagePath = imagePath;
        this.foregroundColor = foregroundColor;
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
}
