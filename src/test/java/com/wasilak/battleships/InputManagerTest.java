package com.wasilak.battleships;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InputManagerTest {

    @Test
    public void shouldReturnValidCoordinatesForCorrectInput() {
        //given
        String input = "B8";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputManager inputManager = new InputManager();
        //when
        Optional<Coordinates> result = inputManager.getPlayerInput();
        //then
        assertThat(result).hasValue(new Coordinates(1, 7));

    }

    @Test
    public void shouldReturnEmptyOptionalForInvalidInput() {
        //given
        String input = "X32";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputManager inputManager = new InputManager();
        //when
        Optional<Coordinates> result = inputManager.getPlayerInput();
        //then
        assertThat(result).isEmpty();

    }
}