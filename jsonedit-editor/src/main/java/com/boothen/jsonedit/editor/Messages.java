package com.boothen.jsonedit.editor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Provides means to access i18n resources, in particular externalized strings.
 */
public final class Messages {

    /**
     * The resource bundle for this plugin
     */
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("i18n/Messages");

    /**
     * Hidden constructor
     */
    private Messages() {
        //nothing to do here
    }

    /**
     * Get externalized text string
     * @param key the string's ID
     * @return the externalized string
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
