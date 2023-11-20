package model;

import java.awt.geom.Rectangle2D;

public class Grid {

    private Rectangle2D.Double rect;
    private Field[][] gridArray;

    public Grid() {
        rect = new Rectangle2D.Double();
        gridArray = new Field[6][7];
    }

    // Aktualisiert die Positionsdaten des Rechtecks und der Kreise
    // TODO: Verbessern und dynamisch machen
    public void updateGridPositions(int width, int height) {
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

    // Gibt bei gültiger Benutzereingabe "True" zurück und ändert den Status des betreffenden Feldes
    public boolean refreshGrid(int x, int y, int filledBy) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (getGridArray()[i][j].contains(x,y) && getGridArray()[i][j].getStatus() == FieldConstants.UNFILLED_UNFILLABLE) {
                    getGridArray()[i][j].setStatus(filledBy);
                    return true;
                }
            }
        }
        return false;
    }

    public void resetFieldStates() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0) {
                    getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                }
            }
        }
    }

    public void refreshFieldStates() {
        for (int i = 5; i > 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (getGridArray()[i][j].getStatus() == FieldConstants.FILLED_BY_PLAYER1 || getGridArray()[i][j].getStatus() == FieldConstants.FILLED_BY_PLAYER2) {
                    getGridArray()[i+1][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                }
            }
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
