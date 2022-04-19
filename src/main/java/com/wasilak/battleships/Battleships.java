package com.wasilak.battleships;

import com.wasilak.battleships.data.Coordinates;
import com.wasilak.battleships.data.Ship;
import com.wasilak.battleships.generator.FleetPlacementGenerator;
import com.wasilak.battleships.generator.ShipPlacementGenerator;
import com.wasilak.battleships.io.InputManager;
import com.wasilak.battleships.io.OutputManager;

import java.util.*;

/**
 * Represents simple implementation of Battleships game.
 */
public class Battleships {

    private static final int BOARD_SIZE = 10;

    private InputManager inputManager;
    private OutputManager outputManager;

    private Board board;

    public static void main(String[] args) {
        new Battleships().gameLoop();
    }

    public Battleships() {
        inputManager = new InputManager();
        outputManager = new OutputManager(BOARD_SIZE);

        List<Ship> shipsToPlace = Arrays.asList(
                new Ship("Battleship", 5),
                new Ship("Destroyer", 4),
                new Ship("Destroyer", 4)
        );

        board = new Board(BOARD_SIZE, getFleetGenerator().placeFleet(shipsToPlace));
    }

    private FleetPlacementGenerator getFleetGenerator() {
        ShipPlacementGenerator shipPlacementGenerator = new ShipPlacementGenerator(BOARD_SIZE, new Random());
        return new FleetPlacementGenerator(shipPlacementGenerator);
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
