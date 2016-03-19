package com.boothen.jsonedit.preferences.format;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import com.boothen.jsonedit.preferences.format.JsonFormatter.Affix;

/**
 * TODO: describe
 */
class FormatChangeListener extends SelectionAdapter {

    private IPreferenceStore preferenceStore;
    private String key;
    private Affix affix;

    public FormatChangeListener(IPreferenceStore preferenceStore, String key, Affix affix) {
        this.preferenceStore = preferenceStore;
        this.key = key;
        this.affix = affix;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        Button button = (Button)e.widget;
        if (button.getSelection()) {
            preferenceStore.setValue(key, affix.name());
        }
    }

}
