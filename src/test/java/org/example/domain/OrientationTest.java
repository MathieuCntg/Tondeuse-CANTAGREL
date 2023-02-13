package org.example.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.domain.Orientation.*;
import static org.junit.jupiter.api.Assertions.*;

class OrientationTest {

    @ParameterizedTest
    @MethodSource("shouldRotateClockwiseSource")
    void shouldRotateClockwise(Orientation initialOrientation, Orientation expected) {
        final var actual = initialOrientation.rotateClockwise();
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> shouldRotateClockwiseSource() {
        return Stream.of(
                Arguments.of(North, East),
                Arguments.of(East, South),
                Arguments.of(South, West),
                Arguments.of(West, North)
        );
    }

    @ParameterizedTest
    @MethodSource("shouldRotateCounterClockwiseSource")
    void shouldRotateCounterClockwise(Orientation initialOrientation, Orientation expected) {
        final var actual = initialOrientation.rotateCounterClockwise();
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> shouldRotateCounterClockwiseSource() {
        return Stream.of(
                Arguments.of(North, West),
                Arguments.of(East, North),
                Arguments.of(South, East),
                Arguments.of(West, South)
        );
    }
}