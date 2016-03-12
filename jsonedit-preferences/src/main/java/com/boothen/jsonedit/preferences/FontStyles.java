package com.boothen.jsonedit.preferences;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;

/**
 * @author denis.mirochnik
 */
public class FontStyles
{
    public static int extractBoldItalic(int style)
    {
        return style & ~TextAttribute.STRIKETHROUGH & ~TextAttribute.UNDERLINE;
    }

    public static boolean isUnderline(int style)
    {
        return (style & TextAttribute.UNDERLINE) != 0;
    }

    public static boolean isStrike(int style)
    {
        return (style & TextAttribute.STRIKETHROUGH) != 0;
    }

    public static boolean isBold(int style)
    {
        return (style & SWT.BOLD) != 0;
    }

    public static boolean isItalic(int style)
    {
        return (style & SWT.ITALIC) != 0;
    }

    public static int merge(boolean bold, boolean italic, boolean under, boolean strike)
    {
        int style = SWT.NORMAL;

        style |= bold ? SWT.BOLD : 0;
        style |= italic ? SWT.ITALIC : 0;
        style |= under ? TextAttribute.UNDERLINE : 0;
        style |= strike ? TextAttribute.STRIKETHROUGH : 0;

        return style;
    }
}
