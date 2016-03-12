package com.boothen.jsonedit.core;

/**
 * Defines preference constants.
 */
public final class JsonPreferences {
    public static final String SPACES_FOR_TABS = "spaces_for_tabs"; //$NON-NLS-1$
    public static final String NUM_SPACES = "num_spaces"; //$NON-NLS-1$
    public static final String EDITOR_MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1l$
    public static final String EDITOR_MATCHING_BRACKETS_COLOR =  "matchingBracketsColor"; //$NON-NLS-1$
    public static final String AUTO_FORMAT_ON_SAVE =  "autoFormatOnSave"; //$NON-NLS-1$
    public static final String ERROR_TEXT_STYLE = "errorTextStyle"; //$NON-NLS-1$
    public static final String ERROR_INDICATION = "errorIndication"; //$NON-NLS-1$
    public static final String ERROR_INDICATION_COLOR = "errorIndicationColor"; //$NON-NLS-1$

    public static final String STYLE_TEXT = "styleText"; //$NON-NLS-1$
    public static final String STYLE_KEY = "styleKey"; //$NON-NLS-1$
    public static final String STYLE_NUMBER = "styleNumber"; //$NON-NLS-1$
    public static final String STYLE_BOOLEAN = "styleBoolean"; //$NON-NLS-1$
    public static final String STYLE_NULL = "styleNull"; //$NON-NLS-1$
    public static final String STYLE_ERROR = "styleError"; //$NON-NLS-1$

    public static final String COLOR_ENTRY = ".color";
    public static final String BOLD_ENTRY = ".isBold";
    public static final String ITALIC_ENTRY = ".isBold";

    public static final String STYLE_TEXT_COLOR = STYLE_TEXT + COLOR_ENTRY;
    public static final String STYLE_KEY_COLOR = STYLE_KEY + COLOR_ENTRY;
    public static final String STYLE_NUMBER_COLOR = STYLE_NUMBER + COLOR_ENTRY;
    public static final String STYLE_BOOLEAN_COLOR = STYLE_BOOLEAN + COLOR_ENTRY;
    public static final String STYLE_NULL_COLOR = STYLE_NULL + COLOR_ENTRY;
    public static final String STYLE_ERROR_COLOR = STYLE_ERROR + COLOR_ENTRY;

    public static final String STYLE_TEXT_BOLD = STYLE_TEXT + BOLD_ENTRY;
    public static final String STYLE_KEY_BOLD = STYLE_KEY + BOLD_ENTRY;
    public static final String STYLE_NUMBER_BOLD = STYLE_NUMBER + BOLD_ENTRY;
    public static final String STYLE_BOOLEAN_BOLD = STYLE_BOOLEAN + BOLD_ENTRY;
    public static final String STYLE_NULL_BOLD = STYLE_NULL + BOLD_ENTRY;
    public static final String STYLE_ERROR_BOLD = STYLE_ERROR + BOLD_ENTRY;

    public static final String STYLE_TEXT_ITALIC = STYLE_TEXT + ITALIC_ENTRY;
    public static final String STYLE_KEY_ITALIC = STYLE_KEY + ITALIC_ENTRY;
    public static final String STYLE_NUMBER_ITALIC = STYLE_NUMBER + ITALIC_ENTRY;
    public static final String STYLE_BOOLEAN_ITALIC = STYLE_BOOLEAN + ITALIC_ENTRY;
    public static final String STYLE_NULL_ITALIC = STYLE_NULL + ITALIC_ENTRY;
    public static final String STYLE_ERROR_ITALIC = STYLE_ERROR + ITALIC_ENTRY;

    private JsonPreferences() {
        // no instances
    }
}
