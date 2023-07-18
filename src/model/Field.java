package model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Field extends Ellipse2D implements FieldConstants {

    private double x;
    private double y;
    private double width;
    private double height;
    private int status;

    public Field(double x, double y) {
        this.x = x;
        this.y = y;
        this.status = FieldConstants.STATUS_UNFILLED;
    }
    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
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
}
