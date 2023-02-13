package org.example.domain;

import org.example.exceptions.InvalidLawnException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.example.domain.Action.*;
import static org.example.domain.Orientation.*;
import static org.junit.jupiter.api.Assertions.*;

public class LawnTest {

    @ParameterizedTest
    @MethodSource("shouldMowWithOneMowerOrMultipleMowersSource")
    void shouldMowWithOneMowerOrMultipleMowers(Lawn lawn, Lawn expectedLawn){
        lawn.mow();

        assertEquals(expectedLawn, lawn);
    }

    private static Stream<Arguments> shouldMowWithOneMowerOrMultipleMowersSource() {
        return Stream.of(
                Arguments.of(
                        new Lawn(
                                new Position(5, 5),
                                List.of(
                                        new Mower(
                                                new Position(1, 2), North,
                                                List.of(TurnLeft, MoveForward, TurnLeft, MoveForward, TurnLeft, MoveForward, MoveForward, MoveForward)
                                        )
                                )
                        ),
                        new Lawn(
                                new Position(5, 5),
                                List.of(
                                        new Mower(new Position(3, 1), East,
                                                List.of()
                                        )
                                )
                        )
                ),
                Arguments.of(
                        new Lawn(
                                new Position(5, 5),
                                List.of(
                                        new Mower(
                                                new Position(1, 2), North,
                                                List.of(TurnLeft, MoveForward, TurnLeft, MoveForward, TurnLeft, MoveForward, MoveForward, MoveForward)
                                        ),
                                        new Mower(
                                                new Position(3, 3), East,
                                                List.of(MoveForward, TurnRight, MoveForward, TurnRight, MoveForward, TurnRight,  MoveForward, MoveForward, MoveForward, TurnLeft)
                                        )
                                )
                        ),
                        new Lawn(
                                new Position(5, 5),
                                List.of(
                                        new Mower(
                                                new Position(3, 1), East,
                                                List.of()
                                        ),
                                        new Mower(
                                                new Position(3, 5), West,
                                                List.of()
                                        )
                                )
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldInvalidateLawnGivenBadCoordinatesSource")
    void shouldThrowInvalidLawnExceptionGivenInvalidCoordinates(Position position) {
        final String expectedMessage = "Invalid lawn maximum coordinates";

        final var exception = assertThrows(InvalidLawnException.class, () -> Lawn.validate(position));

        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> shouldInvalidateLawnGivenBadCoordinatesSource() {
        return Stream.of(
                Arguments.of(new Position(-1, 1)),
                Arguments.of(new Position(1, -1)),
                Arguments.of(new Position(-1, -1))
        );
    }

}