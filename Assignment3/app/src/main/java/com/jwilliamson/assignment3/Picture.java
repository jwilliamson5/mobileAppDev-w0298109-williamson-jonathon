package com.jwilliamson.assignment3;

public class Picture {

    public final String id;
    public final String name;
    public final String path;

    public Picture(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return name;
    }
}
