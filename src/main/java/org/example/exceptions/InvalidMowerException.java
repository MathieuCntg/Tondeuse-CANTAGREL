package org.example.exceptions;

public final class InvalidMowerException extends Exception {

    public static final String OUT_OF_LAWN = "Mower out of the lawn";

    private InvalidMowerException(String message) {
        super(message);
    }

    public static InvalidMowerException outOfLawn() {
        return new InvalidMowerException(OUT_OF_LAWN);
    }

}
