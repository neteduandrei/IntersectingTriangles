package model;

import java.util.Objects;

public class Rectangle {
    private final Point topLeftCorner, downRightCorner;

    public Rectangle(int x, int y, int delta_x, int delta_y) {
        this.topLeftCorner = new Point(x, y + delta_y);
        this.downRightCorner = new Point(x + delta_x, y);
    }

    public Rectangle(Point topLeftCorner, Point downRightCorner) {
        this.topLeftCorner = topLeftCorner;
        this.downRightCorner = downRightCorner;
    }

    public Point getTopLeftCorner() {
        return topLeftCorner;
    }

    public Point getDownRightCorner() {
        return downRightCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(topLeftCorner, rectangle.topLeftCorner) &&
                Objects.equals(downRightCorner, rectangle.downRightCorner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeftCorner, downRightCorner);
    }

    @Override
    public String toString() {
        int x = topLeftCorner.getX();
        int y = downRightCorner.getY();
        int delta_x = downRightCorner.getX() - topLeftCorner.getX();
        int delta_y = topLeftCorner.getY() - downRightCorner.getY();

        return String.format("(%d,%d), delta_x=%d, delta_y=%d", x, y, delta_x, delta_y);
    }
}
