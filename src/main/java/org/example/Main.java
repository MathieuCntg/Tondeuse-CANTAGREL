package org.example;

import org.example.domain.DataParser;
import org.example.domain.Lawn;
import org.example.exceptions.InvalidDataFormatException;
import org.example.exceptions.InvalidLawnException;
import org.example.exceptions.InvalidMowerException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            Lawn lawn = DataParser.linesToLawn(Files.readAllLines(Path.of("src/main/resources/input.txt")));
            lawn.mow();
            Files.write(Path.of("src/main/resources/output.txt"), DataParser.lawnToLines(lawn));
        } catch (InvalidMowerException | InvalidDataFormatException | InvalidLawnException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}