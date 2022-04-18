package com.wasilak.battleships;

import java.util.*;


public class Battleships {

    private static final Integer BOARD_SIZE = 10;

    private InputManager inputManager;
    private OutputManager outputManager;

    private FleetPlacementGenerator fleetPlacementGenerator;
    private Board board;

    public static void main(String[] args) {
        new Battleships().gameLoop();
    }

    public Battleships() {
        inputManager = new InputManager();
        outputManager = new OutputManager(BOARD_SIZE);
        fleetPlacementGenerator = new FleetPlacementGenerator(BOARD_SIZE);

        List<Ship> shipsToPlace = Arrays.asList(
                new Ship("Battleship", 5),
                new Ship("Destroyer", 4),
                new Ship("Destroyer", 4)
        );

        board = new Board(BOARD_SIZE, fleetPlacementGenerator.placeFleet(shipsToPlace));
    }

    private void gameLoop() {
        while (!board.isVictoryConditionFulfilled()) {
            showBoardAndPromptMessage();

            inputManager.getPlayerInput().ifPresentOrElse(this::processValidInput, this::processInvalidInput);
        }
        showVictoryMessage();
    }

    private void showBoardAndPromptMessage() {
        outputManager.drawBoard(board.getBoardStatus());
        outputManager.showMessage("Enter coordinates of your target:");
    }

    private void processValidInput(Coordinates targetCoordinates) {
        outputManager.showMessage(board.processInput(targetCoordinates));
    }

    private void processInvalidInput() {
        outputManager.showMessage("Invalid input. Please provide target coordinates as letter+number e.g \"A3\" or \"h10\"!");
    }

    private void showVictoryMessage() {
        outputManager.drawBoard(board.getBoardStatus());
        outputManager.showMessage("All enemy ships sunk. You won!");
    }
}
