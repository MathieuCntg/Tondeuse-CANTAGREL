package org.example.domain;

import org.example.exceptions.InvalidMowerException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.example.domain.Action.*;
import static org.example.domain.Action.MoveForward;
import static org.example.domain.Orientation.*;
import static org.junit.jupiter.api.Assertions.*;

public class MowerTest {

    @ParameterizedTest
    @MethodSource("shouldMoveForwardSource")
    void shouldMoveForward(Mower actual, Mower expected){
        actual.mow(new Position(5, 5));

        assertEquals(actual, expected);
    }

    private static Stream<Arguments> shouldMoveForwardSource(){
        return Stream.of(
                Arguments.of(new Mower(new Position(1, 1), North, List.of(MoveForward)), new Mower(new Position(1, 2), North, List.of())),
                Arguments.of(new Mower(new Position(1, 1), East, List.of(MoveForward)), new Mower(new Position(2, 1), East, List.of())),
                Arguments.of(new Mower(new Position(1, 1), South, List.of(MoveForward)), new Mower(new Position(1, 0), South, List.of())),
                Arguments.of(new Mower(new Position(1, 1), West, List.of(MoveForward)), new Mower(new Position(0, 1), West, List.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldExecuteTurnActionSource")
    void shouldTurn(Mower actual, Mower expected){
        actual.mow(new Position(5, 5));
        assertEquals(actual, expected);
    }

    private static Stream<Arguments> shouldExecuteTurnActionSource(){
        return Stream.of(
                Arguments.of(new Mower(new Position(0, 0), North, List.of(TurnRight)), new Mower(new Position(0, 0), East, List.of())),
                Arguments.of(new Mower(new Position(0, 0), East, List.of(TurnRight)), new Mower(new Position(0, 0), South, List.of())),
                Arguments.of(new Mower(new Position(0, 0), South, List.of(TurnRight)), new Mower(new Position(0, 0), West, List.of())),
                Arguments.of(new Mower(new Position(0, 0), West, List.of(TurnRight)), new Mower(new Position(0, 0), North, List.of())),
                Arguments.of(new Mower(new Position(0, 0), North, List.of(TurnLeft)), new Mower(new Position(0, 0), West, List.of())),
                Arguments.of(new Mower(new Position(0, 0), West, List.of(TurnLeft)), new Mower(new Position(0, 0), South, List.of())),
                Arguments.of(new Mower(new Position(0, 0), South, List.of(TurnLeft)), new Mower(new Position(0, 0), East, List.of())),
                Arguments.of(new Mower(new Position(0, 0), East, List.of(TurnLeft)), new Mower(new Position(0, 0), North, List.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldNotMoveAndConsumeInstructionSource")
    void shouldNotMoveAndConsumeInstruction(Mower actual, Mower expected){
        actual.mow(new Position(0, 0));
        assertEquals(actual, expected);
    }

    private static Stream<Arguments> shouldNotMoveAndConsumeInstructionSource(){
        return Stream.of(
                Arguments.of(new Mower(new Position(0, 0), North, List.of(MoveForward)), new Mower(new Position(0, 0), North, List.of())),
                Arguments.of(new Mower(new Position(0, 0), East, List.of(MoveForward)), new Mower(new Position(0, 0), East, List.of())),
                Arguments.of(new Mower(new Position(0, 0), South, List.of(MoveForward)), new Mower(new Position(0, 0), South, List.of())),
                Arguments.of(new Mower(new Position(0, 0), West, List.of(MoveForward)), new Mower(new Position(0, 0), West, List.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldExecuteMultipleInstructionsSource")
    void shouldExecuteMultipleInstructions(Mower actual, Mower expected){
        actual.mow(new Position(5, 5));
        assertEquals(actual, expected);
    }

    private static Stream<Arguments> shouldExecuteMultipleInstructionsSource(){
        return Stream.of(
                Arguments.of(new Mower(new Position(0, 0), North, List.of(MoveForward, TurnRight)), new Mower(new Position(0, 1), East, List.of())),
                Arguments.of(new Mower(new Position(0, 0), South, List.of(TurnLeft, TurnLeft)), new Mower(new Position(0, 0), North, List.of())),
                Arguments.of(new Mower(new Position(0, 0), North, List.of(TurnRight, MoveForward, MoveForward)), new Mower(new Position(2, 0), East, List.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldThrowInvalidMowerExceptionGivenInvalidCoordinatesSource")
    void shouldThrowInvalidMowerExceptionGivenInvalidCoordinates(Position initialPosition){
        final String expectedMessage = "Mower out of the lawn";

        final var exception = assertThrows(InvalidMowerException.class, () -> Mower.validate(new Position(1, 1), initialPosition));

        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> shouldThrowInvalidMowerExceptionGivenInvalidCoordinatesSource(){
        return Stream.of(
                Arguments.of(new Position(2, 1)),
                Arguments.of(new Position(1, 2)),
                Arguments.of(new Position(-1, 1)),
                Arguments.of(new Position(1, -1))
        );
    }

}