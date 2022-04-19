package com.wasilak.battleships;

import com.wasilak.battleships.data.Coordinates;
import com.wasilak.battleships.data.FieldStatus;
import com.wasilak.battleships.data.Ship;

import java.util.Arrays;
import java.util.Map;

/**
 * Stores all in-game state and implements methods required to record game progress.
 */
class Board {

    private int boardSize;
    private FieldStatus[][] fields;
    private Map<Coordinates, Ship> shipSegments;

    Board(int boardSize, Map<Coordinates, Ship> shipSegments) {
        this.boardSize = boardSize;
        this.shipSegments = shipSegments;
        initializeFreshBoard();
    }

    /**
     * Used for victory condition check
     *
     * @return true if victory condition is fulfilled (all ships are sunk), false otherwise
     */
    boolean isVictoryConditionFulfilled() {
        return shipSegments.isEmpty();
    }

    /**
     * Returns an array of board field statuses.
     *
     * @return 2-dimensional array of {@link FieldStatus} field statuses
     */
    FieldStatus[][] getBoardStatus() {
        return fields;
    }

    /**
     * Processes input (shot coordinates) obtained from player and returns results of a shot
     *
     * @param targetCoordinates {@link Coordinates} of a square targeted by a shot
     * @return message telling whether a shot is a hit or miss
     */
    String processInput(Coordinates targetCoordinates) {
        if (hasFieldNotBeenTargeted(targetCoordinates)) {
            return processShot(targetCoordinates);
        } else {
            return "This field has already been targeted. Choose different field";
        }
    }

    private String processShot(Coordinates targetCoordinates) {
        if (shipSegments.containsKey(targetCoordinates)) {
            return this.processHit(targetCoordinates);
        } else {
            this.markFieldAsMiss(targetCoordinates);
            return "Miss.";
        }
    }

    private String processHit(Coordinates targetCoordinates) {
        this.markFieldAsHit(targetCoordinates);
        Ship damagedShip = shipSegments.get(targetCoordinates);
        shipSegments.remove(targetCoordinates);
        return hitOrSinkMessage(damagedShip);
    }

    private String hitOrSinkMessage(Ship damagedShip) {
        if (!shipSegments.containsValue(damagedShip)) {
            return damagedShip.getName() + " sunk!";
        } else {
            return damagedShip.getName() + " hit!";
        }
    }

    private void initializeFreshBoard() {
        fields = new FieldStatus[boardSize][boardSize];
        Arrays.stream(fields).forEach(field -> Arrays.fill(field, FieldStatus.UNKNOWN));
    }

    private boolean hasFieldNotBeenTargeted(Coordinates targetCoordinates) {
        return fields[targetCoordinates.row()][targetCoordinates.column()].equals(FieldStatus.UNKNOWN);
    }

    private void markFieldAsHit(Coordinates targetCoordinates) {
        fields[targetCoordinates.row()][targetCoordinates.column()] = FieldStatus.HIT;
    }

    private void markFieldAsMiss(Coordinates targetCoordinates) {
        fields[targetCoordinates.row()][targetCoordinates.column()] = FieldStatus.MISS;
    }
}
