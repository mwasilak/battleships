package com.wasilak.battleships;

import java.util.Arrays;

class Board {

    private static final int UPPERCASE_A_SHIFT = 65;

    private Integer boardSize;
    private FieldStatus[][] fields;

    public Board(Integer boardSize) {
        this.boardSize = boardSize;
        initializeFreshBoard();
    }

    public boolean hasNotBeenTargeted(Coordinates targetCoordinates) {
        return fields[targetCoordinates.getRow()][targetCoordinates.getColumn()].equals(FieldStatus.UNKNOWN);
    }

    public void markAsHit(Coordinates targetCoordinates) {
        fields[targetCoordinates.getRow()][targetCoordinates.getColumn()] = FieldStatus.HIT;
    }

    public void markAsMiss(Coordinates targetCoordinates) {
        fields[targetCoordinates.getRow()][targetCoordinates.getColumn()] = FieldStatus.MISS;
    }

    public String drawBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10\n");
        for(int i = 0; i < boardSize; i++) {
            builder.append((char)(i + UPPERCASE_A_SHIFT));
            builder.append(" ");
            for(int j = 0; j < boardSize; j++) {
                switch(fields[i][j]) {
                    case UNKNOWN ->  builder.append(". ");
                    case MISS -> builder.append("/ ");
                    case HIT -> builder.append("X ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void initializeFreshBoard() {

        fields = new FieldStatus[boardSize][boardSize];
        Arrays.stream(fields).forEach(field -> Arrays.fill(field, FieldStatus.UNKNOWN));
    }
}
