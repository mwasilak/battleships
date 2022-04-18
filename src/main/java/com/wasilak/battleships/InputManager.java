package com.wasilak.battleships;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputManager {

    private static final int UPPERCASE_A_SHIFT = 65;
    private static final int LOWERCASE_A_SHIFT = 97;

    Scanner keyboard;
    Pattern inputPattern;

    public InputManager() {
        keyboard = new Scanner(System.in);
        inputPattern = Pattern.compile("^([a-jA-J])([1-9]|10)$");
    }

    public InputManager(Scanner scanner) {
        keyboard = scanner;
        inputPattern = Pattern.compile("^([a-jA-J])([1-9]|10)$");
    }

    public Optional<Coordinates> getPlayerInput() {
        String target = keyboard.nextLine();
        Matcher matcher = inputPattern.matcher(target);

        if(matcher.matches()) {
            return Optional.of(getCoordinatesFromInput(matcher.group(1), matcher.group(2)));
        }
        return Optional.empty();
    }

    private Coordinates getCoordinatesFromInput(String letterInput, String numberInput) {
        int row, column;
        if(letterInput.equals(letterInput.toUpperCase())) {
            row = letterInput.charAt(0) - UPPERCASE_A_SHIFT;
        } else {
            row = letterInput.charAt(0) - LOWERCASE_A_SHIFT;
        }

        column = Integer.parseInt(numberInput) - 1;
        return new Coordinates(row, column);
    }
}
