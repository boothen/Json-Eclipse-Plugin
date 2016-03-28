package com.boothen.jsonedit.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The (empty) root preference page. Mainly used as identifier.
 */
public class JsonRootPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    /**
     * The only constructor
     */
    public JsonRootPreferencePage() {
        setDescription("Expand the tree to edit preferences for a specific feature.");
        noDefaultAndApplyButton();
    }

    @Override
    protected final Control createContents(Composite parent) {
        Color widgetBackground = parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
        parent.setBackground(widgetBackground);

        return new Composite(parent, SWT.NULL);
    }

    @Override
    public void init(IWorkbench workbench) {
        // Do nothing
    }

}
