package com.boothen.jsonedit.preferences.colors;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ColorsContentProvider implements IStructuredContentProvider {

    @Override
    public void dispose() {
        // must be implemented
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // must be implemented
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return (Object[]) inputElement;
    }
}
