package org.example.domain;

import org.example.exceptions.InvalidDataFormatException;
import org.example.exceptions.InvalidLawnException;
import org.example.exceptions.InvalidMowerException;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.example.domain.Action.*;
import static org.example.domain.Orientation.*;
import static org.example.domain.Orientation.West;

public abstract class DataParser {

    private static final Pattern lawnRegex =  Pattern.compile("\\d+ \\d+");
    private static final Pattern mowerPlacementRegex = Pattern.compile("\\d+ \\d+ [NESW]");
    private static final Pattern mowerInstructionsRegex = Pattern.compile("[ADG]*");

    private static final Map<String, Orientation> STRING_ORIENTATION_MAP = new HashMap<>() {{
        put("N", North);
        put("E", East);
        put("S", South);
        put("W", West);
    }};

    private static final Map<String, Action> STRING_ACTIONS_MAP = new HashMap<>() {{
        put("A", MoveForward);
        put("D", TurnRight);
        put("G", TurnLeft);
    }};

    public static Lawn linesToLawn(List<String> data) throws InvalidDataFormatException, InvalidMowerException, InvalidLawnException {
        validateLines(data);

        final var lawnDimension = parseStringToPosition(data.get(0));

        Lawn.validate(lawnDimension);

        return new Lawn(lawnDimension, createMowers(data, lawnDimension));
    }

    private static Position parseStringToPosition(String data) {
        final var position = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
        return new Position(position[0], position[1]);
    }

    private static void validateLines(List<String> data) throws InvalidDataFormatException {
        if (data == null)
            throw InvalidDataFormatException.noData();

        if (data.size() % 2 == 0)
            throw InvalidDataFormatException.invalidDataFormat();

        if (!lawnRegex.matcher(data.get(0)).matches())
            throw InvalidDataFormatException.invalidLawnFormat();

        for (int i = 1; i < data.size(); i+=2) {
            final var mowerNumber = (i+1)/2;

            if (!mowerPlacementRegex.matcher(data.get(i)).matches())
                throw InvalidDataFormatException.invalidMowerPlacementFormat(mowerNumber);

            if (!mowerInstructionsRegex.matcher(data.get(i+1)).matches())
                throw InvalidDataFormatException.invalidMowerInstructionFormat(mowerNumber);
        }
    }

    private static List<Mower> createMowers(List<String> data, Position lawnDimension) throws InvalidMowerException {
        final List<Mower> mowers = new ArrayList<>();

        for (int i = 1; i < data.size()-1; i+=2) {
            final var placement = data.get(i).split(" ");

            final var position = new Position(Integer.parseInt(placement[0]), Integer.parseInt(placement[1]));

            Mower.validate(lawnDimension, position);
            mowers.add(new Mower(position, STRING_ORIENTATION_MAP.get(placement[2]), parseInstruction(data.get(i + 1))));
        }

        return mowers;
    }

    private static List<Action> parseInstruction(String instruction) {
        return Arrays.stream(instruction.split("")).map(STRING_ACTIONS_MAP::get).collect(Collectors.toList());
    }
}
