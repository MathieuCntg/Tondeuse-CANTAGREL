package org.example.domain;

import org.example.exceptions.InvalidLawnException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Lawn {
    private final Position maxPosition;
    private final List<Mower> mowers;

    public Lawn(Position maxPosition, List<Mower> mowers) {
        this.maxPosition = maxPosition;
        this.mowers = mowers;
    }

    public List<Mower> mowers() {
        return Collections.unmodifiableList(mowers);
    }

    public void mow() {
        for (Mower mower : mowers) {
            mower.mow(maxPosition);
        }
    }

    public static void validate(Position position) throws InvalidLawnException {
        if (position.getxPos() < 0 || position.getyPos() < 0) throw InvalidLawnException.invalidMaximumCoordinates();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lawn lawn = (Lawn) o;
        return Objects.equals(maxPosition, lawn.maxPosition) && Objects.equals(mowers, lawn.mowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPosition, mowers);
    }
}