package com.wasilak.battleships;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FleetPlacementGeneratorTest {

    @Mock
    private ShipPlacementGenerator shipPlacementGenerator;

    @Test
    public void shouldGenerateFleet() {
        //given
        FleetPlacementGenerator generator = new FleetPlacementGenerator(shipPlacementGenerator);
        List<Ship> shipsToPlace = getShipsToPlace();

        //when
        initializeShipPlacementGeneratorMock();
        Map<Coordinates, Ship> result = generator.placeFleet(shipsToPlace);

        //then
        assertThat(result).containsExactlyEntriesOf(getResultsMap(shipsToPlace));

    }

    private List<Ship> getShipsToPlace() {
        return Arrays.asList(
                new Ship("Battleship", 5),
                new Ship("Destroyer", 4));
    }

    private void initializeShipPlacementGeneratorMock() {
        when(shipPlacementGenerator.getRandomShipLocation(any()))
                .thenReturn(getMockBattleshipCoordinates())
                .thenReturn(getMockOverlappingDestroyerCoordinates())
                .thenReturn(getMockNonOverlappingDestroyerCoordinates());
    }

    private Set<Coordinates> getMockBattleshipCoordinates() {
        return Stream.of(new Coordinates(3, 6),
                         new Coordinates(4, 6),
                         new Coordinates(5, 6),
                         new Coordinates(6, 6),
                         new Coordinates(7, 6))
                     .collect(toSet());
    }

    private Set<Coordinates> getMockOverlappingDestroyerCoordinates() {
        return Stream.of(new Coordinates(4, 4),
                         new Coordinates(4, 5),
                         new Coordinates(4, 6),
                         new Coordinates(4, 7))
                     .collect(toSet());
    }

    private Set<Coordinates> getMockNonOverlappingDestroyerCoordinates() {
        return Stream.of(new Coordinates(9, 4),
                         new Coordinates(9, 5),
                         new Coordinates(9, 6),
                         new Coordinates(9, 7))
                     .collect(toSet());
    }

    private Set<Map.Entry<Coordinates, Ship>> getMapEntries(Set<Coordinates> shipCoordinates, Ship ship) {
        return shipCoordinates.stream().collect(toMap(Function.identity(), coordinates -> ship)).entrySet();
    }

    private Map<Coordinates, Ship> getResultsMap(List<Ship> shipsToPlace) {
        return Stream.concat(
                getMapEntries(getMockBattleshipCoordinates(), shipsToPlace.get(0)).stream(),
                getMapEntries(getMockNonOverlappingDestroyerCoordinates(), shipsToPlace.get(1)).stream())
                     .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}