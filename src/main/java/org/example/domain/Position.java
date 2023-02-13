package org.example.domain;

import java.util.Objects;

public class Position {

    private int xPos;
    private int yPos;

    public Position(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void moveUp() {
        this.yPos += 1;
    }

    public void moveDown() {
        this.yPos -= 1;
    }

    public void moveRight() {
        this.xPos += 1;
    }

    public void moveLeft() {
        this.xPos -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return xPos == position.xPos && yPos == position.yPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos);
    }
}
