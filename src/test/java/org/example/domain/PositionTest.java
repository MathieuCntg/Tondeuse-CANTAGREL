package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void shouldMoveUp() {
        final var actual = new Position(1, 1);
        final var expected = new Position(1, 2);

        actual.moveUp();

        assertEquals(actual, expected);
    }

    @Test
    void shouldMoveDown() {
        final var actual = new Position(1, 1);
        final var expected = new Position(1, 0);

        actual.moveDown();

        assertEquals(actual, expected);
    }

    @Test
    void shouldMoveRight() {
        final var actual = new Position(1, 1);
        final var expected = new Position(2, 1);

        actual.moveRight();

        assertEquals(actual, expected);
    }

    @Test
    void shouldMoveLeft() {
        final var actual = new Position(1, 1);
        final var expected = new Position(0, 1);

        actual.moveLeft();

        assertEquals(actual, expected);
    }

}