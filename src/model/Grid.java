package model;

import java.awt.geom.Rectangle2D;

public class Grid {

    // Das Rechteck für das "Spielfeld"
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
                yCord -= 80;
            }
            yCord = 500;
            xCord += 80;
        }
    }

    // Gibt bei gültiger Benutzereingabe "True" zurück und ändert den Status des betreffenden Feldes

    /**
     *
     * @param x Die X-Koordinate des Mausklicks
     * @param y Die Y-Koordinate des Mausklicks
     * @param filledBy Der Wert welcher Spieler den Mausklick gesetzt hat
     * @return Ob der Mausklick korrekt in ein Feld gesetzt wurde.
     */
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

    // Setzt den Status aller Felder im Grid zurück auf Standard
    public void resetFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                }
            }
        }
    }

    // TODO Funktioniert noch überhaupt nicht
    public void refreshFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 5; j >= 0; j--) {
                if (j == 0) {
                    getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                    break;
                }
                else if (getGridArray()[i][j].getStatus() == FieldConstants.UNFILLED_UNFILLABLE || getGridArray()[i][j].getStatus() == FieldConstants.UNFILLED_FILLABLE) {
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
