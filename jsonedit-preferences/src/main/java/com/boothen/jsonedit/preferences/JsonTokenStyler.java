package com.boothen.jsonedit.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.core.JsonColorProvider;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.preferences.TokenStyle;
import com.boothen.jsonedit.model.TokenStyler;


/**
 * Maps JSON token types to JFace {@link TextAttribute} instances.
 */
public class JsonTokenStyler implements TokenStyler {

    private final IPreferenceStore preferenceStore;
    private final JsonColorProvider colorProvider = JsonCorePlugin.getColorProvider();

    /**
     * @param preferenceStore the store that provides {@link TokenStyle} information.
     */
    public JsonTokenStyler(IPreferenceStore preferenceStore) {
        this.preferenceStore = preferenceStore;
    }

    @Override
    public TextAttribute apply(TokenStyle styleId) {
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
}
