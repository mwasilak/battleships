package com.wasilak.battleships;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class FleetPlacementGenerator {

    private ShipPlacementGenerator shipPlacementGenerator;

    public FleetPlacementGenerator(Integer boardSize) {
        shipPlacementGenerator = new ShipPlacementGenerator(boardSize);
    }

    public FleetPlacementGenerator(ShipPlacementGenerator shipPlacementGenerator) {
        this.shipPlacementGenerator = shipPlacementGenerator;
    }

    /**
     * Randomly places a set of ships on the board. Ships are placed according to game rules and do not overlap.
     *
     * @param shipsToPlace list of ships to place
     * @return map with coordinates as keys and ships as values
     */
    public Map<Coordinates, Ship> placeFleet(List<Ship> shipsToPlace) {

        Map<Coordinates, Ship> placedShipSegments = new HashMap<>();

        for(Ship ship : shipsToPlace) {
            Set<Coordinates> newShipLocation = placeShipRecursively(ship, placedShipSegments.keySet());
            placedShipSegments.putAll(newShipSegments(ship, newShipLocation));
        }
        return placedShipSegments;
    }

    /**
     * Recursively places ship on the board until the new ship does not overlap with previously generated ones
     *
     * @param ship ship to be placed on the board
     * @param previouslyGenerated Set of coordinates of all fields occupied by previously generated ships
     * @return set of coordinates of new ship's segments (guaranteed not to overlap with previously generated ships)
     */
    private Set<Coordinates> placeShipRecursively(Ship ship, Set<Coordinates> previouslyGenerated) {
        Set<Coordinates> candidateLocation = shipPlacementGenerator.getRandomShipLocation(ship.getSize());
        if(overlapsWith(candidateLocation, previouslyGenerated)) {
            return placeShipRecursively(ship, previouslyGenerated);
        } else {
            return candidateLocation;
        }
    }

    /**
     * Checks overlap between newly generated ship and previously generated ones
     * @param candidateLocation set of coordinates of new randomly generated ship
     * @param previouslyGenerated set of coordinates of previously generated ships
     * @return true if new randomly generated ship overlaps with previously generated ships, false otherwise
     */
    private boolean overlapsWith(Set<Coordinates> candidateLocation, Set<Coordinates> previouslyGenerated) {
        return candidateLocation.stream().anyMatch(segment -> previouslyGenerated.contains(segment));
    }

    /**
     * Transforms set of ship segment coordinates into map
     * @param ship ship object
     * @param candidateLocation set of coordinates of new randomly generated ship
     * @return map of coordinates entries pointing to ship object
     */
    private  Map<Coordinates, Ship> newShipSegments(Ship ship, Set<Coordinates> candidateLocation) {
        return candidateLocation.stream().collect(Collectors.toMap(Function.identity(), i -> ship));
    }
}
