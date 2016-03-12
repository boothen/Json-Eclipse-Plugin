package com.boothen.jsonedit.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.JsonColorProvider;
import com.boothen.jsonedit.core.JsonEditorPlugin;
import com.boothen.jsonedit.core.JsonPreferences;
import com.boothen.jsonedit.model.TokenMapping;


/**
 * TODO: describe
 */
public class JsonTokenMapping implements TokenMapping {

    private final IPreferenceStore preferenceStore = JsonEditorPlugin.getDefault().getPreferenceStore();
    private final JsonColorProvider colorProvider = JsonEditorPlugin.getColorProvider();

    @Override
    public Object apply(int currentTokenType, int previousTokenType) {
        String id = getIdentifier(currentTokenType, previousTokenType);

        boolean isBold = preferenceStore.getBoolean(id + ".isBold");
        boolean isItalic = preferenceStore.getBoolean(id + ".isItalic");
        int style = getStyle(isBold, isItalic);

        String colorText = preferenceStore.getString(id + JsonPreferences.COLOR_ENTRY);
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

    private static String getIdentifier(int currentTokenType, int previousTokenType) {
        switch (currentTokenType) {
        case JSONLexer.STRING:
            if (previousTokenType == JSONLexer.COLON) {
                return JsonPreferences.STYLE_TEXT;
            } else {
                return JsonPreferences.STYLE_KEY;
            }

        case JSONLexer.NUMBER:
            return JsonPreferences.STYLE_NUMBER;

        case JSONLexer.TRUE:
        case JSONLexer.FALSE:
            return JsonPreferences.STYLE_BOOLEAN;

        case JSONLexer.NULL:
            return JsonPreferences.STYLE_NULL;

        default:
            return null;
        }
    }
}
