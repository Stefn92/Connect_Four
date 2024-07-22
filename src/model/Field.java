package model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class Field extends Ellipse2D {

    private double x;
    private double y;
    private double width;
    private double height;
    private FieldStatus status;

    public Field(double width, double height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.status = FieldStatus.UNFILLED_UNFILLABLE;
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
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
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
        return java.lang.Double.compare(field.getX(), getX()) == 0 && java.lang.Double.compare(field.getY(), getY()) == 0 && java.lang.Double.compare(field.getWidth(), getWidth()) == 0 && java.lang.Double.compare(field.getHeight(), getHeight()) == 0 && getStatus() == field.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getX(), getY(), getWidth(), getHeight(), getStatus());
    }

    public FieldStatus getStatus() {
        return status;
    }

    public void setStatus(FieldStatus status) {
        this.status = status;
    }
}
