package org.example.domain;

public enum Orientation {
    North,
    East,
    South,
    West;

    public Orientation rotateClockwise() {
        return switch (this) {
            case North -> East;
            case East -> South;
            case South -> West;
            case West -> North;
        };
    }

    public Orientation rotateCounterClockwise() {
        return switch (this) {
            case North -> West;
            case East -> North;
            case South -> East;
            case West -> South;
        };
    }
}
