package com.boothen.jsonedit.outline;

/**
 * Looks the same to the outside, but may change on the inside
 * @param <T> the type of the contained object
 */
public final class Container<T> {

    private T content;

    /**
     * Uses <code>null</code> content.
     */
    public Container() {
        this(null);
    }

    /**
     * @param content the content to wrap
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
