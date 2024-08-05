package model;

import java.awt.geom.Rectangle2D;

public class Board {

    private int x;
    private int y;
    private int width;
    private int height;

    public Board() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public void updateBoardCoordinates(int panelWidth, int panelHeight) {

        int xPos = (panelWidth * 12) / 100;
        int yPos = (panelHeight * 7) / 100;
        int fieldWidth = panelWidth - (xPos * 2);
        int fieldHeight = panelHeight - (yPos * 2);
        setFrame(xPos, yPos, fieldWidth, fieldHeight);
        System.out.println("Rect: Heigth = " + getHeight() + " Width = " + getWidth());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFrame(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle2D.Double getBoardAsRectangle2D() {
        Rectangle2D.Double rect = new Rectangle2D.Double();
        rect.setFrame(x, y, width, height);
        return rect;
    }
}
