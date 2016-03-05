package com.boothen.jsonedit.outline;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Convert the provided tree content into a map (child -> parent)
 */
class ParentProvider {

    private final ITreeContentProvider contentProvider;

    /**
     * @param contentProvider provides the tree content
     */
    public ParentProvider(ITreeContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    /**
     * Iterate through the tree and serializes all child elements to a list (depth first).
     * @param root the tree root
     * @return the tree content
     */
    public Map<Object, Object> record(Object root) {
        Map<Object, Object> list = new HashMap<>();
        list.put(root, null);
        internalAdd(list, root);
        return list;
    }

    private void internalAdd(Map<Object, Object> list, Object root) {
        for (Object child : contentProvider.getChildren(root)) {
            list.put(child, root);
            internalAdd(list, child);
        }
    }
}
