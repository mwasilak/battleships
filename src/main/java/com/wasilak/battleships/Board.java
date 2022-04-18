package com.wasilak.battleships;

import java.util.Arrays;
import java.util.Map;

class Board {

    enum FieldStatus {
        UNKNOWN, MISS, HIT
    }

    private Integer boardSize;
    private FieldStatus[][] fields;
    private Map<Coordinates, Ship> shipSegments;

    public Board(Integer boardSize, Map<Coordinates, Ship> shipSegments) {
        this.boardSize = boardSize;
        this.shipSegments = shipSegments;
        initializeFreshBoard();
    }

    public boolean isVictoryConditionFulfilled() {
        return shipSegments.isEmpty();
    }

    public FieldStatus[][] getBoardStatus() {
        return fields;
    }

    public String processInput(Coordinates targetCoordinates) {
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
        if(!shipSegments.containsValue(damagedShip)) {
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
        return fields[targetCoordinates.getRow()][targetCoordinates.getColumn()].equals(FieldStatus.UNKNOWN);
    }

    private void markFieldAsHit(Coordinates targetCoordinates) {
        fields[targetCoordinates.getRow()][targetCoordinates.getColumn()] = FieldStatus.HIT;
    }

    private void markFieldAsMiss(Coordinates targetCoordinates) {
        fields[targetCoordinates.getRow()][targetCoordinates.getColumn()] = FieldStatus.MISS;
    }
}
