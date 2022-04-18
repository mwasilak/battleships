package com.wasilak.battleships;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipPlacementGeneratorTest {

    @Mock
    RandomGenerator mockRandom;

    @Test
    public void shouldGenerateShipSegments() {

        //given
        ShipPlacementGenerator generator = new ShipPlacementGenerator(10, mockRandom);
        Ship testShip = new Ship("Battleship", 5);

        //when
        initializeMockRandomGenerator();

        Set<Coordinates> result = generator.getRandomShipLocation(testShip.getSize());

        //then
        assertThat(result).containsExactlyInAnyOrder(new Coordinates(3, 6),
                                                     new Coordinates(4, 6),
                                                     new Coordinates(5, 6),
                                                     new Coordinates(6, 6),
                                                     new Coordinates(7, 6));
    }

    private void initializeMockRandomGenerator() {
        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(10)).thenReturn(6);
        when(mockRandom.nextInt(6)).thenReturn(3);
    }


}