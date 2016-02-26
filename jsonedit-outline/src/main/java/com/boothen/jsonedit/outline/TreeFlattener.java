package com.boothen.jsonedit.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Convert the provided tree into a flat list.
 */
class TreeFlattener {

    private ITreeContentProvider contentProvider;

    /**
     * @param contentProvider provides the tree content
     */
    public TreeFlattener(ITreeContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    /**
     * Iterate through the tree and serializes all child elements to a list (depth first).
     * @param root the tree root
     * @return the tree content
     */
    public List<Object> flatten(Object root) {
        List<Object> list = new ArrayList<>();
        internalAdd(list, root);
        return list;
    }

    private void internalAdd(List<Object> list, Object root) {
        list.add(root);
        for (Object child : contentProvider.getChildren(root)) {
            internalAdd(list, child);
        }
    }
}
