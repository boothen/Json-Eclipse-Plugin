package com.boothen.jsonedit.model;

public class Segment {
    private int start;
    private int stop;

    public Segment(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    public int getLength() {
        return stop - start + 1;
    }
}
