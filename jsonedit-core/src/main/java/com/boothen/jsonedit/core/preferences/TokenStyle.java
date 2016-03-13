package com.boothen.jsonedit.core.preferences;

/**
 * TODO: describe
 */
public enum TokenStyle {

    DEFAULT,
    KEY,
    TEXT,
    NUMBER,
    BOOLEAN,
    NULL,
    ERROR;

    public String base() {
        String name = name();
        return "style" + name.charAt(0) + name.substring(1).toLowerCase();
    }

    public String separator() {
        return ".";
    }

    public String color() {
        return base() + separator() + "color";
    }

    public String isBold() {
        return base() + separator() + "isBold";
    }

    public String isItalic() {
        return base() + separator() + "isItalic";
    }

    @Override
    public String toString() {
        String name = name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
