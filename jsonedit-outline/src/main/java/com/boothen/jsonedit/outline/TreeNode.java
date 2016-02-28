package com.boothen.jsonedit.outline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class TreeNode<T> {

    private T content;

    private List<TreeNode<T>> children = new ArrayList<>();

    private TreeNode<T> parent;

    private NodeType type;

    public TreeNode() {
        this(null, null);
    }

    public TreeNode(T content, NodeType type) {
        this.content = content;
        this.type = type;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    /**
     * @return the content
     */
    public T getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(T content, NodeType type) {
        this.content = content;
        this.type = type;
    }

    /**
     * @return
     */
    public TreeNode<T> getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    /**
     * @return the type
     */
    public NodeType getType() {
        return type;
    }
}
