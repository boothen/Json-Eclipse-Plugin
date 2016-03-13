package com.boothen.jsonedit.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.JsonColorProvider;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.preferences.TokenStyle;
import com.boothen.jsonedit.model.TokenMapping;


/**
 * TODO: describe
 */
public class JsonTokenMapping implements TokenMapping {

    private final IPreferenceStore preferenceStore;
    private final JsonColorProvider colorProvider = JsonCorePlugin.getColorProvider();

    public JsonTokenMapping(IPreferenceStore preferenceStore) {
        this.preferenceStore = preferenceStore;
    }

    @Override
    public Object apply(int currentTokenType, int previousTokenType) {
        TokenStyle styleId = getIdentifier(currentTokenType, previousTokenType);

        boolean isBold = preferenceStore.getBoolean(styleId.isBold());
        boolean isItalic = preferenceStore.getBoolean(styleId.isItalic());
        int style = getStyle(isBold, isItalic);

        String colorText = preferenceStore.getString(styleId.color());
        Color color = getColor(colorText);

        TextAttribute attribute = new TextAttribute(color, null, style);
        return attribute;
    }

    private Color getColor(String colorText) {
        RGB rgb = StringConverter.asRGB(colorText, null);
        return colorProvider.getColor(rgb);
    }

    private static int getStyle(boolean isBold, boolean isItalic) {
        int style = SWT.NORMAL;
        style |= isBold ? SWT.BOLD : 0;
        style |= isItalic ? SWT.ITALIC : 0;
        return style;
    }

    private static TokenStyle getIdentifier(int currentTokenType, int previousTokenType) {
        switch (currentTokenType) {
        case JSONLexer.STRING:
            if (previousTokenType == JSONLexer.COLON) {
                return TokenStyle.TEXT;
            } else {
                return TokenStyle.KEY;
            }

        case JSONLexer.NUMBER:
            return TokenStyle.NUMBER;

        case JSONLexer.TRUE:
        case JSONLexer.FALSE:
            return TokenStyle.BOOLEAN;

        case JSONLexer.NULL:
            return TokenStyle.NULL;

        default:
            return TokenStyle.DEFAULT;
        }
    }
}
