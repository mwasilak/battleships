package com.wasilak.battleships.io;

import com.wasilak.battleships.data.FieldStatus;

/**
 * Groups operations related to displaying game progress to the player.
 */
public class OutputManager {

    private static final int UPPERCASE_A_SHIFT = 65;

    private int boardSize;

    public OutputManager(int boardSize) {
        this.boardSize = boardSize;
    }

    /**
     * Displays message to the player. Newline character is added automatically.
     *
     * @param message text message to the player
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays complete board with game progress the player. Board has row designations (letters)
     * and column designations (numbers).
     *
     * @param fields 2-dimensional array of {@link FieldStatus} fields
     */
    public void drawBoard(FieldStatus[][] fields) {
        StringBuilder builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int row = 0; row < boardSize; row++) {
            builder.append((char) (row + UPPERCASE_A_SHIFT));
            builder.append(" ");
            appendFieldsFromRow(fields[row], builder);
            builder.append("\n");
        }
        System.out.print(builder);
    }

    private void appendFieldsFromRow(FieldStatus[] fieldsRow, StringBuilder builder) {
        for (int col = 0; col < boardSize; col++) {
            switch (fieldsRow[col]) {
                case UNKNOWN -> builder.append(". ");
                case MISS -> builder.append("/ ");
                case HIT -> builder.append("X ");
            }
        }
    }
}
