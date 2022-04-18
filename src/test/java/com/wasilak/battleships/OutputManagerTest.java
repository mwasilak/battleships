package com.wasilak.battleships;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OutputManagerTest {

    public static final int BOARD_SIZE = 10;

    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    void shouldDrawBoardForGivenBoardStatus() {
        //given
        OutputManager manager = new OutputManager(BOARD_SIZE);
        //when
        manager.drawBoard(getMockBoardStatus());
        //then
        assertThat(byteArrayOutputStream.toString()).isEqualTo(getMockBoardDrawing());
    }

    private Board.FieldStatus[][] getMockBoardStatus() {
        Board.FieldStatus[][] fields = new Board.FieldStatus[BOARD_SIZE][BOARD_SIZE];
        Arrays.stream(fields).forEach(field -> Arrays.fill(field, Board.FieldStatus.UNKNOWN));
        fields[5][7] = Board.FieldStatus.HIT;
        fields[4][3] = Board.FieldStatus.MISS;
        return fields;
    }

    private String getMockBoardDrawing() {
        return "  1 2 3 4 5 6 7 8 9 10\n" +
               "A . . . . . . . . . . \n" +
               "B . . . . . . . . . . \n" +
               "C . . . . . . . . . . \n" +
               "D . . . . . . . . . . \n" +
               "E . . . / . . . . . . \n" +
               "F . . . . . . . X . . \n" +
               "G . . . . . . . . . . \n" +
               "H . . . . . . . . . . \n" +
               "I . . . . . . . . . . \n" +
               "J . . . . . . . . . . \n";
    }
}