package com.wasilak.battleships;

import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShipPlacementGenerator {

    private Integer boardSize;
    private RandomGenerator random;

    private enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public ShipPlacementGenerator(Integer boardSize) {
        this.boardSize = boardSize;
        random = new Random();
    }

    public ShipPlacementGenerator(Integer boardSize, RandomGenerator random) {
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
    public Set<Coordinates> getRandomShipLocation(Integer shipSize) {
        Orientation orientation = getRandomShipOrientation();
        int index = getRandomRowOrColumnIndex();
        int edgeDistance = getRandomEdgeDistance(shipSize);

        return IntStream.range(0, shipSize)
                .mapToObj(i -> generateShipSegmentCoordinates(orientation, index, edgeDistance + i))
                .collect(Collectors.toSet());
    }

    /**
     * Returns coordinates of a single ship segment
     *
     * @param orientation vertical or horizontal
     * @param index row or column index (all segments of a ship have the same index)
     * @param edgeDistance distance from the edge within specified row or colum (all segments of a ship have different
     *                     edge distance)
     * @return ship segment coordinates
     */
    private Coordinates generateShipSegmentCoordinates(Orientation orientation, int index, int edgeDistance) {
        if(orientation.equals(Orientation.VERTICAL)) {
            return new Coordinates(edgeDistance, index);
        }
        return new Coordinates(index, edgeDistance);
    }

    /**
     * Returns random ship orientation.
     *
     * @return Ship orientation (vertical or horizontal)
     */
    private Orientation getRandomShipOrientation() {
        if(random.nextBoolean()) {
            return Orientation.VERTICAL;
        }
        return Orientation.HORIZONTAL;
    }

    /**
     * Returns random row or column index for the defined board size (0-based indexing).
     *
     * @return row or column index
     */
    private int getRandomRowOrColumnIndex() {
        return random.nextInt(boardSize);
    }

    /**
     * Returns edge distance within row or column - how far from left or top side of the board ship will be placed.
     * Different number of valid positions exist for ships of different size e.g. for battleships there are 6 valid
     * positions, and for destroyers there are 7 valid positions.
     *
     * @param shipSize Number of squares occupied by ship (Battleship - 5, Destroyer - 4)
     * @return Distance from left or top side of the board
     */
    private int getRandomEdgeDistance(Integer shipSize) {
        return random.nextInt(boardSize - shipSize + 1);
    }
}
