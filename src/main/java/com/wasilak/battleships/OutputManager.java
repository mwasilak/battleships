package com.wasilak.battleships;

public class OutputManager {

    private static final int UPPERCASE_A_SHIFT = 65;

    private Integer boardSize;

    public OutputManager(Integer boardSize) {
        this.boardSize = boardSize;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void drawBoard(Board.FieldStatus[][] fields) {
        StringBuilder builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10\n");
        for(int row = 0; row < boardSize; row++) {
            builder.append((char)(row + UPPERCASE_A_SHIFT));
            builder.append(" ");
            appendFieldsFromRow(fields[row], builder);
            builder.append("\n");
        }
        System.out.print(builder);
    }

    private void appendFieldsFromRow(Board.FieldStatus[] fieldsRow, StringBuilder builder) {
        for(int col = 0; col < boardSize; col++) {
            switch(fieldsRow[col]) {
                case UNKNOWN ->  builder.append(". ");
                case MISS -> builder.append("/ ");
                case HIT -> builder.append("X ");
            }
        }
    }


}
