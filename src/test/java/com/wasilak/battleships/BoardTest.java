package com.wasilak.battleships;

import com.wasilak.battleships.data.Coordinates;
import com.wasilak.battleships.data.FieldStatus;
import com.wasilak.battleships.data.Ship;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    public static final int BOARD_SIZE = 10;

    @Test
    public void shouldGenerateEmptyBoard() {
        //given
        Board board = new Board(BOARD_SIZE, getMockGunboatSegments());
        //when
        FieldStatus[][] result = board.getBoardStatus();
        //then
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                assertThat(result[i][j].equals(FieldStatus.UNKNOWN));
            }
        }
    }

    @Test
    public void shouldMarkFieldAsMissAndReturnMissMessage() {
        //given
        Board board = new Board(BOARD_SIZE, getMockGunboatSegments());
        //when
        String resultMessage = board.processInput(new Coordinates(5,5));
        //then
        assertThat(resultMessage).isEqualTo("Miss.");
        assertThat(board.getBoardStatus()[5][5]).isEqualTo(FieldStatus.MISS);
    }

    @Test
    public void shouldMarkFieldAsHitAndReturnHitMessage() {
        //given
        Board board = new Board(BOARD_SIZE, getMockGunboatSegments());
        //when
        String resultMessage = board.processInput(new Coordinates(3,6));
        //then
        assertThat(resultMessage).isEqualTo("Gunboat hit!");
        assertThat(board.getBoardStatus()[3][6]).isEqualTo(FieldStatus.HIT);
        assertThat(board.isVictoryConditionFulfilled()).isFalse();
    }

    @Test
    public void shouldMarkFieldsAsHitAndReturnSinkMessage() {
        //given
        Board board = new Board(BOARD_SIZE, getMockGunboatSegments());
        //when
        String firstMessage = board.processInput(new Coordinates(3,6));
        String secondMessage = board.processInput(new Coordinates(4,6));
        //then
        assertThat(firstMessage).isEqualTo("Gunboat hit!");
        assertThat(secondMessage).isEqualTo("Gunboat sunk!");
        assertThat(board.getBoardStatus()[3][6]).isEqualTo(FieldStatus.HIT);
        assertThat(board.getBoardStatus()[4][6]).isEqualTo(FieldStatus.HIT);
        assertThat(board.isVictoryConditionFulfilled()).isTrue();
    }

    private Map<Coordinates, Ship> getMockGunboatSegments() {
        Ship gunboat = new Ship("Gunboat", 2);
        return Stream.of(new Coordinates(3, 6),
                        new Coordinates(4, 6))
                .collect(toMap(Function.identity(), coordinates -> gunboat));
    }

}