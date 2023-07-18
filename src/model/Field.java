package model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class Field extends Ellipse2D implements FieldConstants {

    private double x;
    private double y;
    private double width;
    private double height;
    private String id;
    private int status;

    public Field(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.status = FieldConstants.STATUS_UNFILLED;
    }
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {

    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Field field = (Field) o;
        return java.lang.Double.compare(field.getX(), getX()) == 0 && java.lang.Double.compare(field.getY(), getY()) == 0 && java.lang.Double.compare(field.getWidth(), getWidth()) == 0 && java.lang.Double.compare(field.getHeight(), getHeight()) == 0 && status == field.status && Objects.equals(id, field.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getX(), getY(), getWidth(), getHeight(), id, status);
    }
}
