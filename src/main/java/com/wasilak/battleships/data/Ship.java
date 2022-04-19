package com.wasilak.battleships.data;

/**
 * Represents a single ship for purpose of board generation and victory checks.
 */
public class Ship {

    private String name;
    private int size;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
