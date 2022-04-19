package com.wasilak.battleships.generator;

import com.wasilak.battleships.data.Coordinates;
import com.wasilak.battleships.data.Ship;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Used to randomly place a list of ships.
 */
public class FleetPlacementGenerator {

    private ShipPlacementGenerator shipPlacementGenerator;

    public FleetPlacementGenerator(ShipPlacementGenerator shipPlacementGenerator) {
        this.shipPlacementGenerator = shipPlacementGenerator;
    }

    /**
     * Randomly places a list of ships on the board. Ships must be provided as {@link Ship} objects. Ships are placed
     * according to game rules and do not overlap.
     *
     * @param shipsToPlace list of ships to place
     * @return map with coordinates as keys and ships as values
     */
    public Map<Coordinates, Ship> placeFleet(List<Ship> shipsToPlace) {

        Map<Coordinates, Ship> placedShipSegments = new HashMap<>();

        for (Ship ship : shipsToPlace) {
            Set<Coordinates> newShipLocation = placeShipRecursively(ship, placedShipSegments.keySet());
            placedShipSegments.putAll(newShipSegments(ship, newShipLocation));
        }
        return placedShipSegments;
    }

    private Set<Coordinates> placeShipRecursively(Ship ship, Set<Coordinates> previouslyGenerated) {
        Set<Coordinates> candidateLocation = shipPlacementGenerator.getRandomShipLocation(ship.getSize());
        if (overlapsWith(candidateLocation, previouslyGenerated)) {
            return placeShipRecursively(ship, previouslyGenerated);
        } else {
            return candidateLocation;
        }
    }

    private boolean overlapsWith(Set<Coordinates> candidateLocation, Set<Coordinates> previouslyGenerated) {
        return candidateLocation.stream().anyMatch(segment -> previouslyGenerated.contains(segment));
    }

    private Map<Coordinates, Ship> newShipSegments(Ship ship, Set<Coordinates> candidateLocation) {
        return candidateLocation.stream().collect(Collectors.toMap(Function.identity(), i -> ship));
    }
}
