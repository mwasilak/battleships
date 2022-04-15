package com.wasilak.battleships;

import java.util.Objects;

class Coordinates {

    private static final int UPPERCASE_A_SHIFT = 65;
    private static final int LOWERCASE_A_SHIFT = 97;
    
    private final int row;
    private final int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinates(String letterInput, String numberInput) {
        if(letterInput.equals(letterInput.toUpperCase())) {
            this.row = letterInput.charAt(0) - UPPERCASE_A_SHIFT;
        } else {
            this.row = letterInput.charAt(0) - LOWERCASE_A_SHIFT;
        }

        this.column = Integer.parseInt(numberInput) - 1;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "com.wasilak.battleships.Coordinates{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
