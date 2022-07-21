package koyonn.sudoku.problemdomain;

import java.util.Objects;

/**
 * Класс для хранения расположения ячейки судоку в коллекции типа HashMap
 */
public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Coordinates another = (Coordinates) o;
        return this.x == another.x && this.y == another.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
