package com.boothen.jsonedit.core.preferences;

/**
 * Defines the preference keys that are used in preference stores to acquire token-related settings.
 */
public enum TokenStyle {

    /**
     * The default style (used for commas, colons, etc)
     */
    DEFAULT,

    /**
     * A key text string
     */
    KEY,

    /**
     * A text string
     */
    TEXT,

    /**
     * A number, either integer or floating point
     */
    NUMBER,

    /**
     * A boolean value
     */
    BOOLEAN,

    /**
     * The constant <code>null</code>.
     */
    NULL,

    /**
     * A text comment node
     */
    COMMENT,

    /**
     * An error node
     */
    ERROR;

    /**
     * @return style + name in PascalCase
     */
    public String base() {
        String name = name();
        return "style" + name.charAt(0) + name.substring(1).toLowerCase();
    }

    /**
     * @return the key separator
     */
    public String separator() {
        return ".";
    }

    /**
     * @return the color key
     */
    public String color() {
        return base() + separator() + "color";
    }

    /**
     * @return the key that indicates if the style requires <b>bold</b> font
     */
    public String isBold() {
        return base() + separator() + "isBold";
    }

    /**
     * @return the key that indicates if the style requires <em>italic</em> font
     */
    public String isItalic() {
        return base() + separator() + "isItalic";
    }

    /**
     * The key, written in PascalCase
     */
    @Override
    public String toString() {
        String name = name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
