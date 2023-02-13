package org.example.exceptions;

public final class InvalidDataFormatException extends Exception {

    public final static String NO_DATA = "No data to parse";

    public final static String DATA = "Invalid data format";

    public final static String LAWN = "Invalid lawn format";

    public final static String MOWER_PLACEMENT = "Invalid mower initialization format on mower number: ";

    public final static String MOWER_INSTRUCTION = "Invalid mower instruction format on mower number: ";

    private InvalidDataFormatException(String message) {
        super(message);
    }

    public static InvalidDataFormatException noData() { return new InvalidDataFormatException(NO_DATA); }

    public static InvalidDataFormatException invalidDataFormat() {
        return new InvalidDataFormatException(DATA);
    }

    public static InvalidDataFormatException invalidLawnFormat(){
        return new InvalidDataFormatException(LAWN);
    }

    public static InvalidDataFormatException invalidMowerPlacementFormat(int mowerNumber) {
        return new InvalidDataFormatException(MOWER_PLACEMENT + mowerNumber);
    }

    public static InvalidDataFormatException invalidMowerInstructionFormat(int mowerNumber) {
        return new InvalidDataFormatException(MOWER_INSTRUCTION + mowerNumber);
    }

}
