package org.example.domain;

import org.example.exceptions.InvalidMowerException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Mower {
    private final Position position;
    private Orientation orientation;
    private List<Action> instructions;

    public Mower(Position position, Orientation orientation, List<Action> instructions) {
        this.position = position;
        this.orientation = orientation;
        this.instructions = instructions;
    }

    public Position getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    private void move(Position maxPosition) {
        if (!canMove(maxPosition)) return;
        switch (this.orientation) {
            case North -> this.position.moveUp();
            case East -> this.position.moveRight();
            case South -> this.position.moveDown();
            case West -> this.position.moveLeft();
        }
    }

    public void mow(Position maxPosition) {
        for (Action instruction : instructions) {
            switch (instruction) {
                case MoveForward -> move(maxPosition);
                case TurnRight -> this.orientation = this.orientation.rotateClockwise();
                case TurnLeft -> this.orientation = this.orientation.rotateCounterClockwise();
            }
        }

        this.instructions = Collections.emptyList();
    }

    private boolean canMove(Position maxPosition) {
        return switch (this.orientation) {
            case North -> this.position.getyPos() != maxPosition.getyPos();
            case East -> this.position.getxPos() != maxPosition.getxPos();
            case South -> this.position.getyPos() != 0;
            case West -> this.position.getxPos() != 0;
        };
    }

    public static void validate(Position maxPosition, Position position) throws InvalidMowerException {
        if (position.getxPos() < 0 ||
                position.getyPos() < 0 ||
                position.getxPos() > maxPosition.getxPos() ||
                position.getyPos() > maxPosition.getyPos()
        ) throw InvalidMowerException.outOfLawn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mower mower = (Mower) o;
        return Objects.equals(position, mower.position) && orientation == mower.orientation && Objects.equals(instructions, mower.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, orientation, instructions);
    }
}
