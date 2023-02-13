package org.example.domain;

import org.example.exceptions.InvalidDataFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.example.domain.Action.*;
import static org.example.domain.Orientation.East;
import static org.example.domain.Orientation.North;
import static org.junit.jupiter.api.Assertions.*;

public class DataParserTest {

    @Test
    void shouldFormatLinesToLawn() throws Exception {
        final List<String> inputString = List.of("5 5", "1 2 N", "GAGAGAAA", "3 3 E", "ADADADAAAG");
        final List<Mower> lawnMowers = List.of(
                new Mower(new Position(1, 2), North, List.of(TurnLeft, MoveForward, TurnLeft, MoveForward, TurnLeft, MoveForward, MoveForward, MoveForward)),
                new Mower(new Position(3, 3), East, List.of(MoveForward, TurnRight, MoveForward, TurnRight, MoveForward, TurnRight,  MoveForward, MoveForward, MoveForward, TurnLeft))
        );
        final Lawn expected = new Lawn(new Position(5, 5), lawnMowers);

        final Lawn actual = DataParser.linesToLawn(inputString);

        assertEquals(expected, actual);
    }

    @Test
    void shouldCreateEmptyLawn() throws Exception {
        final List<String> emptyLawn = List.of("5 5");
        final Lawn expected = new Lawn(new Position(5, 5), List.of());

        final Lawn actual = DataParser.linesToLawn(emptyLawn);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("shouldThrowInvalidDataFormatExceptionGivenInvalidLinesSource")
    void shouldThrowInvalidDataFormatExceptionGivenInvalidLines(List<String> fileLines, String expectedExceptionMessage){
        final var actual = assertThrows(InvalidDataFormatException.class, () -> DataParser.linesToLawn(fileLines));

        assertEquals(expectedExceptionMessage, actual.getMessage());
    }

    private static Stream<Arguments> shouldThrowInvalidDataFormatExceptionGivenInvalidLinesSource(){
        return Stream.of(
                Arguments.of(null, InvalidDataFormatException.NO_DATA),
                Arguments.of(List.of("5 5", "1 2 N", "GAGAGAAA", "3 3 E"), InvalidDataFormatException.DATA),
                Arguments.of(List.of(), InvalidDataFormatException.DATA),
                Arguments.of(List.of("5 A", "1 2 N", "GAGAGAAA", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.LAWN),
                Arguments.of(List.of("5", "1 2 N", "GAGAGAAA", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.LAWN),
                Arguments.of(List.of("5 5", "1 2", "GAGAGAAA", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.MOWER_PLACEMENT + 1),
                Arguments.of(List.of("5 5", "2 N", "GAGAGAAA", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.MOWER_PLACEMENT + 1),
                Arguments.of(List.of("5 5", "1 2 3 N", "GAGAGAAA", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.MOWER_PLACEMENT + 1),
                Arguments.of(List.of("5 5", "1 2 N", "ADGq", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.MOWER_INSTRUCTION + 1),
                Arguments.of(List.of("5 5", "1 2 N", "ABC1", "3 3 E", "ADADADAAAG"), InvalidDataFormatException.MOWER_INSTRUCTION + 1),
                Arguments.of(List.of("5 5", "1 2 N", "GAGAGAAA", "3 3 E", "adg"), InvalidDataFormatException.MOWER_INSTRUCTION + 2)
        );
    }
}