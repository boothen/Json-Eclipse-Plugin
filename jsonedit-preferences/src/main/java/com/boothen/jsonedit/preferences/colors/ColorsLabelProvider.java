package com.boothen.jsonedit.preferences.colors;

import org.eclipse.jface.viewers.LabelProvider;

public class ColorsLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        return element.toString();
    }
}
