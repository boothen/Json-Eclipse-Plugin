package com.boothen.jsonedit.preferences;

/**
 * Maps elements in the syntax tree to image, foreground and background color
 * in the outline view.
 */
public enum NodeType {
    ROOT(null, null, null),
    OBJECT("JsonObject.gif", "BLUE", "WHITE"),
    ARRAY("JsonArray.gif", "BLUE", "WHITE"),
    BOOLEAN("JsonBoolean.gif", "BLACK", "WHITE"),
    NULL("JsonNull.gif", "BLACK", "WHITE"),
    NUMBER("JsonNumber.gif", "BLUE", "WHITE"),
    STRING("JsonString.gif", "GREEN", "WHITE"),
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
