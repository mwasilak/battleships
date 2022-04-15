package com.wasilak.battleships;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Battleships {

    private static final Integer BOARD_SIZE = 10;

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        Pattern inputPattern = Pattern.compile("^([a-jA-J])([1-9]|10)$");

        Board board = new Board(BOARD_SIZE);

        List<Ship> shipsToPlace = Arrays.asList(
                new Ship("Battleship", 5),
                new Ship("Destroyer", 4),
                new Ship("Destroyer", 4)
        );

        Map<Coordinates, Ship> shipSegments = new FleetPlacementGenerator(BOARD_SIZE).placeFleet(shipsToPlace);

        System.out.println(shipSegments.keySet().stream().findFirst());

        while(true) {
            System.out.print(board.drawBoard());
            System.out.println("Enter coordinates of your target:");
            String target = keyboard.nextLine();
            Matcher matcher = inputPattern.matcher(target);
            if(matcher.matches()) {
                Coordinates targetCoordinates = new Coordinates(matcher.group(1), matcher.group(2));
                if(board.hasNotBeenTargeted(targetCoordinates)) {
                    if(shipSegments.containsKey(targetCoordinates)) {
                        board.markAsHit(targetCoordinates);
                    } else {
                        board.markAsMiss(targetCoordinates);
                    }
                } else {
                    System.out.println("This field has already been targeted. Choose different field");
                }
            } else {
                System.out.println("Invalid input. Please provide target coordinates as letter+number e.g \"A3\" or \"H10\"!");
            }

        }
    }



}
