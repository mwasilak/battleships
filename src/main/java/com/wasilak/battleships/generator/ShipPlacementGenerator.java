package com.wasilak.battleships.generator;

import com.wasilak.battleships.data.Coordinates;

import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Used to randomly place a single ship.
 */
public class ShipPlacementGenerator {

    private int boardSize;
    private RandomGenerator random;

    private enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public ShipPlacementGenerator(int boardSize, RandomGenerator random) {
        this.boardSize = boardSize;
        this.random = random;
    }

    /**
     * Returns set of coordinates for segments of a ship. The ship is placed at random, according to game rules:
     * all segments within board, vertical or horizontal, contiguous.
     *
     * @param shipSize Number of squares occupied by ship (Battleship - 5, Destroyer - 4)
     * @return Set of coordinates of all ship segments. Size of the set is equal to shipSize
     */
    Set<Coordinates> getRandomShipLocation(int shipSize) {
        Orientation orientation = getRandomShipOrientation();
        int index = getRandomRowOrColumnIndex();
        int edgeDistance = getRandomEdgeDistance(shipSize);

        return IntStream.range(0, shipSize)
                .mapToObj(i -> generateShipSegmentCoordinates(orientation, index, edgeDistance + i))
                .collect(Collectors.toSet());
    }

    private Coordinates generateShipSegmentCoordinates(Orientation orientation, int index, int edgeDistance) {
        if (orientation.equals(Orientation.VERTICAL)) {
            return new Coordinates(edgeDistance, index);
        }
        return new Coordinates(index, edgeDistance);
    }

    private Orientation getRandomShipOrientation() {
        if (random.nextBoolean()) {
            return Orientation.VERTICAL;
        }
        return Orientation.HORIZONTAL;
    }

    private int getRandomRowOrColumnIndex() {
        return random.nextInt(boardSize);
    }

    private int getRandomEdgeDistance(int shipSize) {
        return random.nextInt(boardSize - shipSize + 1);
    }
}
