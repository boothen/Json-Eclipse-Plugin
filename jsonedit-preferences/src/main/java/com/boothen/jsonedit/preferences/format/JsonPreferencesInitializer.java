package com.boothen.jsonedit.preferences.format;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

/**
 * @author denis.mirochnik
 */
public class JsonPreferencesInitializer extends AbstractPreferenceInitializer
{
    public static final String PREF_SAVE_AS_ON_SAVE = "saveAsOnSave";

    //=========

    public static final String PREF_VALIDATE = "validate";
    public static final String PREF_MAX_ERROR = "maxError";

    //=========

    public static final String PREF_AUTO_FORMAT_ON_SAVE = "autoFormatOnSave";

    public static final String PREF_WRAP_ARRAY = "wrapArray";
    public static final String PREF_WRAP_OBJECT = "wrapObject";

    public static final String PREF_ARRAY_BRACKETS_NEW_LINE = "arrayBrackets";
    public static final String PREF_OBJECT_BRACKETS_NEW_LINE = "objectBrackets";

    public static final String PREF_SPACE_AFTER_COMMA = "spaceAfterComma";
    public static final String PREF_SPACE_AFTER_COLON = "spaceAfterColon";
    public static final String PREF_SPACE_BEFORE_COLON = "spaceBeforeColon";

    public static final String PREF_BLANK_LINE_AFTER_COMPLEX = "newLineAfterComplex";

    public static final String PREF_SPACE_AFTER_OBJECT_OPEN = "spaceAfterObjectOpen";
    public static final String PREF_SPACE_BEFORE_OBJECT_CLOSE = "spaceAfterObjectClose";

    public static final String PREF_SPACE_AFTER_ARRAY_OPEN = "spaceAfterArrayOpen";
    public static final String PREF_SPACE_BEFORE_ARRAY_CLOSE = "spaceAfterArrayClose";

    /*@formatter:off*/

    public static final String[] FORMAT_KEYS = {
        PREF_AUTO_FORMAT_ON_SAVE,
        PREF_WRAP_ARRAY,
        PREF_WRAP_OBJECT,
        PREF_ARRAY_BRACKETS_NEW_LINE,
        PREF_OBJECT_BRACKETS_NEW_LINE,
        PREF_SPACE_AFTER_COMMA,
        PREF_SPACE_AFTER_COLON,
        PREF_SPACE_BEFORE_COLON,
        PREF_BLANK_LINE_AFTER_COMPLEX,
        PREF_SPACE_AFTER_OBJECT_OPEN,
        PREF_SPACE_BEFORE_OBJECT_CLOSE,
        PREF_SPACE_AFTER_ARRAY_OPEN,
        PREF_SPACE_BEFORE_ARRAY_CLOSE
    };

    /*@formatter:on*/

    public static final String PREF_COLOR_MATCH_BRACKET = "colorMatchBracket";
    public static final String PREF_COLOR_BRACKET = "colorBracket";
    public static final String PREF_COLOR_OBJECT_BRACKET = "colorObjectBracket";
    public static final String PREF_COLOR_ARRAY_BRACKET = "colorArrayBracket";
    public static final String PREF_COLOR_KEY = "colorKey";
    public static final String PREF_COLOR_STRING = "colorString";
    public static final String PREF_COLOR_NUMBER = "colorNumber";
    public static final String PREF_COLOR_BOOLEAN = "colorBoolean";
    public static final String PREF_COLOR_NULL = "colorNull";
    public static final String PREF_COLOR_DEFAULT = "colorDefault";

    public static final String PREF_ENABLED_MATCH_BRACKET = "colorMatchBracketEnabled";
    public static final String PREF_ENABLED_BRACKET = "colorBracketEnabled";
    public static final String PREF_ENABLED_OBJECT_BRACKET = "colorObjectBracketEnabled";
    public static final String PREF_ENABLED_ARRAY_BRACKET = "colorArrayBracketEnabled";
    public static final String PREF_ENABLED_KEY = "colorKeyEnabled";
    public static final String PREF_ENABLED_STRING = "colorStringEnabled";
    public static final String PREF_ENABLED_NUMBER = "colorNumberEnabled";
    public static final String PREF_ENABLED_BOOLEAN = "colorBooleanEnabled";
    public static final String PREF_ENABLED_NULL = "colorNullEnabled";
    public static final String PREF_ENABLED_DEFAULT = "colorDefaultEnabled";

    public static final String PREF_MATCH_BRACKET_STYLE = "colorMatchBracketStyle";
    public static final String PREF_BRACKET_STYLE = "colorBracketStyle";
    public static final String PREF_OBJECT_BRACKET_STYLE = "colorObjectBracketStyle";
    public static final String PREF_ARRAY_BRACKET_STYLE = "colorArrayBracketStyle";
    public static final String PREF_KEY_STYLE = "colorKeyStyle";
    public static final String PREF_STRING_STYLE = "colorStringStyle";
    public static final String PREF_NUMBER_STYLE = "colorNumberStyle";
    public static final String PREF_BOOLEAN_STYLE = "colorBooleanStyle";
    public static final String PREF_NULL_STYLE = "colorNullStyle";
    public static final String PREF_DEFAULT_STYLE = "colorDefaultStyle";

    public enum TokenType
    {
        MATCHED_BRACKET(PREF_COLOR_MATCH_BRACKET, PREF_ENABLED_MATCH_BRACKET, PREF_MATCH_BRACKET_STYLE, new RGB(0, 0, 0), true, SWT.NORMAL),
        BRACKETS(PREF_COLOR_BRACKET, PREF_ENABLED_BRACKET, PREF_BRACKET_STYLE, new RGB(0, 0, 0), false, SWT.NORMAL),
        OBJECT_BRACKETS(PREF_COLOR_OBJECT_BRACKET, PREF_ENABLED_OBJECT_BRACKET, PREF_OBJECT_BRACKET_STYLE, new RGB(0, 0, 0), false, SWT.NORMAL)
        {
            @Override
            public RGB getColor(IPreferenceStore store)
            {
                if (!isEnabled(store))
                {
                    return BRACKETS.getColor(store);
                }

                return super.getColor(store);
            }
        },
        ARRAY_BRACKETS(PREF_COLOR_ARRAY_BRACKET, PREF_ENABLED_ARRAY_BRACKET, PREF_ARRAY_BRACKET_STYLE, new RGB(0, 0, 0), false, SWT.NORMAL)
        {
            @Override
            public RGB getColor(IPreferenceStore store)
            {
                if (!isEnabled(store))
                {
                    return BRACKETS.getColor(store);
                }

                return super.getColor(store);
            }
        },
        KEYS(PREF_COLOR_KEY, PREF_ENABLED_KEY, PREF_KEY_STYLE, new RGB(0, 128, 0), true, SWT.NORMAL)
        {
            @Override
            public RGB getColor(IPreferenceStore store)
            {
                if (!isEnabled(store))
                {
                    return STRINGS.getColor(store);
                }

                return super.getColor(store);
            }
        },
        STRINGS(PREF_COLOR_STRING, PREF_ENABLED_STRING, PREF_STRING_STYLE, new RGB(0, 0, 255), true, SWT.NORMAL),
        NUMBERS(PREF_COLOR_NUMBER, PREF_ENABLED_NUMBER, PREF_NUMBER_STYLE, new RGB(128, 64, 64), true, SWT.NORMAL),
        BOOLEANS(PREF_COLOR_BOOLEAN, PREF_ENABLED_BOOLEAN, PREF_BOOLEAN_STYLE, new RGB(0, 0, 0), false, SWT.NORMAL),
        NULL(PREF_COLOR_NULL, PREF_ENABLED_NULL, PREF_NULL_STYLE, new RGB(0, 0, 0), false, SWT.NORMAL),
        DEFAULT(PREF_COLOR_DEFAULT, PREF_ENABLED_DEFAULT, PREF_DEFAULT_STYLE, new RGB(0, 0, 0), true, SWT.NORMAL);

        private final String mKey;
        private final RGB mDef;
        private final boolean mEnabledDef;
        private final String mEnabledKey;
        private final String mStyleKey;
        private final int mStyleDef;

        private TokenType(String key, String enabledKey, String styleKey, RGB def, boolean enabledDef, int styleDef)
        {
            mKey = key;
            mEnabledKey = enabledKey;
            mDef = def;
            mEnabledDef = enabledDef;
            mStyleDef = styleDef;
            mStyleKey = styleKey;
        }

        public int getStyle(IPreferenceStore store)
        {
            if (!isEnabled(store))
            {
                return DEFAULT.getStyle(store);
            }

            return store.getInt(mStyleKey);
        }

        public int getOwnStyle(IPreferenceStore store)
        {
            return store.getInt(mStyleKey);
        }

        public RGB getColor(IPreferenceStore store)
        {
            if (!isEnabled(store))
            {
                return DEFAULT.getColor(store);
            }

            return StringConverter.asRGB(store.getString(mKey));
        }

        public RGB getOwnColor(IPreferenceStore store)
        {
            return StringConverter.asRGB(store.getString(mKey));
        }

        public boolean isEnabled(IPreferenceStore store)
        {
            return store.getBoolean(mEnabledKey);
        }

        public String getKey()
        {
            return mKey;
        }

        public String getEnabledKey()
        {
            return mEnabledKey;
        }

        public String getStyleKey()
        {
            return mStyleKey;
        }
    }

    @Override
    public void initializeDefaultPreferences()
    {
        final IPreferenceStore store = null;

        store.setDefault(PREF_SAVE_AS_ON_SAVE, false);

        store.setDefault(PREF_VALIDATE, true);
        store.setDefault(PREF_MAX_ERROR, 0);

        store.setDefault(PREF_AUTO_FORMAT_ON_SAVE, false);

        store.setDefault(PREF_WRAP_ARRAY, true);
        store.setDefault(PREF_WRAP_OBJECT, true);

        store.setDefault(PREF_ARRAY_BRACKETS_NEW_LINE, true);
        store.setDefault(PREF_OBJECT_BRACKETS_NEW_LINE, true);

        store.setDefault(PREF_SPACE_AFTER_COMMA, false);
        store.setDefault(PREF_SPACE_AFTER_COLON, true);
        store.setDefault(PREF_SPACE_BEFORE_COLON, false);

        store.setDefault(PREF_BLANK_LINE_AFTER_COMPLEX, true);

        store.setDefault(PREF_SPACE_AFTER_ARRAY_OPEN, false);
        store.setDefault(PREF_SPACE_BEFORE_ARRAY_CLOSE, false);

        store.setDefault(PREF_SPACE_AFTER_OBJECT_OPEN, false);
        store.setDefault(PREF_SPACE_BEFORE_OBJECT_CLOSE, false);

        final TokenType[] values = TokenType.values();

        for (final TokenType colorType : values)
        {
            store.setDefault(colorType.mKey, StringConverter.asString(colorType.mDef));
            store.setDefault(colorType.mEnabledKey, colorType.mEnabledDef);
            store.setDefault(colorType.mStyleKey, colorType.mStyleDef);
        }
    }
}
