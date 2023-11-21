package model;

import java.awt.geom.Rectangle2D;

public class Grid {

    private Rectangle2D.Double rect;
    private Field[][] gridArray;

    public Grid() {
        rect = new Rectangle2D.Double();
        gridArray = new Field[7][6];
    }

    // Aktualisiert die Positionsdaten des Rechtecks und der Kreise
    // TODO: Verbessern und dynamisch machen
    public void updateGridPositions(int width, int height) {
        int xPos = 100;
        int yPos = 60;
        rect.setRect(xPos, yPos, width - (xPos * 2), height - (yPos * 2));

        int yCord = 500;
        int xCord = 160;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                gridArray[i][j] = new Field(xCord,yCord, 60, 60);
                //xCord += 80;
                yCord -= 80;
            }
            //yCord -= 80;
            yCord = 500;
            xCord += 80;
            //xCord = 160;
        }
    }

    // Gibt bei gültiger Benutzereingabe "True" zurück und ändert den Status des betreffenden Feldes
    public boolean refreshGrid(int x, int y, int filledBy) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (getGridArray()[i][j].contains(x,y) && getGridArray()[i][j].getStatus() == FieldConstants.UNFILLED_FILLABLE) {
                    getGridArray()[i][j].setStatus(filledBy);
                    return true;
                }
            }
        }
        return false;
    }

    public void resetFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                }
            }
        }
    }

    /*public void refreshFieldStates() {
        for (int i = 5; i > 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (getGridArray()[i][j].getStatus() == FieldConstants.FILLED_BY_PLAYER1 || getGridArray()[i][j].getStatus() == FieldConstants.FILLED_BY_PLAYER2) {
                    getGridArray()[i+1][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                }
            }
        }
    }*/

    // TODO Funktioniert noch überhaupt nicht
    public void refreshFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 5; j > 1; j--) {
                if (getGridArray()[i][j].getStatus() == FieldConstants.UNFILLED_UNFILLABLE || getGridArray()[i-1][j].getStatus() == FieldConstants.UNFILLED_FILLABLE) {
                    if (getGridArray()[i][j-1].getStatus() == FieldConstants.FILLED_BY_PLAYER2 || getGridArray()[i][j-1].getStatus() == FieldConstants.FILLED_BY_PLAYER1) {
                        getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                        break;
                    }
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
