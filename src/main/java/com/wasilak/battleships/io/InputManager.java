package com.wasilak.battleships.io;

import com.wasilak.battleships.data.Coordinates;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to obtain player input from keyboard and translate it to in-game data.
 */
public class InputManager {

    private static final int UPPERCASE_A_SHIFT = 65;
    private static final int LOWERCASE_A_SHIFT = 97;

    Scanner keyboard;
    Pattern inputPattern;

    public InputManager() {
        keyboard = new Scanner(System.in);
        inputPattern = Pattern.compile("^([a-jA-J])([1-9]|10)$");
    }

    /**
     * Returns coordinates of a target field selected by player. Method blocks program execution until player enters
     * their choice and confirms it with Enter key.
     *
     * @return Optional which contains {@link Coordinates} of targeted field, or empty optional if player entered
     * invalid coordinates
     */
    public Optional<Coordinates> getPlayerInput() {
        String target = keyboard.nextLine();
        Matcher matcher = inputPattern.matcher(target);

        if (matcher.matches()) {
            return Optional.of(getCoordinatesFromInput(matcher.group(1), matcher.group(2)));
        }
        return Optional.empty();
    }

    private Coordinates getCoordinatesFromInput(String letterInput, String numberInput) {
        int row, column;
        if (letterInput.equals(letterInput.toUpperCase())) {
            row = letterInput.charAt(0) - UPPERCASE_A_SHIFT;
        } else {
            row = letterInput.charAt(0) - LOWERCASE_A_SHIFT;
        }

        column = Integer.parseInt(numberInput) - 1;
        return new Coordinates(row, column);
    }
}
