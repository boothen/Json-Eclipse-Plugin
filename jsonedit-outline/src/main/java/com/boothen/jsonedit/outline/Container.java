package com.boothen.jsonedit.outline;

/**
 * Looks the same to the outside, but may change on the inside
 */
public final class Container<T> {

    private T content;

    public Container() {
        this(null);
    }

    /**
     * @param content
     */
    public Container(T content) {
        this.content = content;
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
    public void setContent(T content) {
        this.content = content;
    }
}
