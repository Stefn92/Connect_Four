package model;

import java.awt.geom.Rectangle2D;

public class Grid {

    private Rectangle2D.Double rect;
    private Field[][] gridArray;

    public Grid() {
        rect = new Rectangle2D.Double();
        gridArray = new Field[6][7];
    }

    // Aktualisiert die Daten des Rechtecks und der Kreise
    public void updateGrid(int width, int height) {
        int xPos = 100;
        int yPos = 60;
        rect.setRect(xPos, yPos, width - (xPos * 2), height - (yPos * 2));

        int yCord = 500;
        int xCord = 160;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gridArray[i][j] = new Field(xCord,yCord, 60, 60);
                xCord += 80;
            }
            yCord -= 80;
            xCord = 160;
        }
    }

    public Rectangle2D.Double getRect() {
        return rect;
    }

    public void setRect(Rectangle2D.Double rect) {
        this.rect = rect;
    }

    public Field[][] getGridArray() {
        return gridArray;
    }

    public void setGridArray(Field[][] gridArray) {
        this.gridArray = gridArray;
    }
}
