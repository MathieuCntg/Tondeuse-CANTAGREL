package org.example.exceptions;

public final class InvalidLawnException extends Exception {

    public static final String MAXIMUM_COORDINATES = "Invalid lawn maximum coordinates";

    private InvalidLawnException(String message) {
        super(message);
    }

    public static InvalidLawnException invalidMaximumCoordinates() {
        return new InvalidLawnException(MAXIMUM_COORDINATES);
    }
}
