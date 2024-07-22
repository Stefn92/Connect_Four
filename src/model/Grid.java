package model;

import java.awt.geom.Rectangle2D;

public class Grid {

    private Rectangle2D.Double field; // Das Rechteck für das Spielfeld
    private Field[][] gridArray; // Array für die Felder im Spielfeld

    public Grid() {
        field = new Rectangle2D.Double();
        gridArray = new Field[7][6];
    }

    public void fillGridArray() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                gridArray[i][j] = new Field(60, 60);
            }
        }
    }

    // Aktualisiert die Positionsdaten des Rechtecks und der Kreise
    // TODO: Verbessern und dynamisch machen
    public void updateGridDimensions(int width, int height) {
        int xPos = 100;
        int yPos = 60;
        field.setRect(xPos, yPos, width - (xPos * 2), height - (yPos * 2));

        int yCord = 500;
        int xCord = 160;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                gridArray[i][j].setFrame(xCord, yCord, 60, 60);
                yCord -= 80;
            }
            yCord = 500;
            xCord += 80;
        }
    }

    // Gibt bei gültiger Benutzereingabe "True" zurück und ändert den Status des betreffenden Feldes
    public boolean refreshGrid(int x, int y, int filledBy) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Field currentField = getGridArray()[i][j];
                if (currentField.contains(x,y) && currentField.getStatus() == FieldConstants.UNFILLED_FILLABLE) {
                    currentField.setStatus(filledBy);
                    return true;
                }
            }
        }
        return false;
    }

    // Setzt den Status aller Felder im Grid zurück auf Standard, alle Felder sind leer
    public void resetFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                }
                else {
                    getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_UNFILLABLE);
                }
            }
        }
        printFieldStates();
    }

    // Aktualisiert die Status der Felder
    public void refreshFieldStates() {
        System.out.println("Führe refreshFieldStates aus...");
        for (int i = 0; i < 7; i++) {
            for (int j = 1; j < 6; j++) {
                if (getGridArray()[i][j].getStatus() == FieldConstants.UNFILLED_UNFILLABLE) {
                    if (getGridArray()[i][j-1].getStatus() == FieldConstants.FILLED_BY_PLAYER2 || getGridArray()[i][j-1].getStatus() == FieldConstants.FILLED_BY_PLAYER1) {
                        getGridArray()[i][j].setStatus(FieldConstants.UNFILLED_FILLABLE);
                        System.out.println("i=" + i + "j=" + j);
                    }
                }
            }
        }
        //printFieldStates();
    }

    public void printFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.println("i = " + i + " j = " + j + " Status = " + getGridArray()[i][j].getStatus());
            }
        }
    }

    public Rectangle2D.Double getField() {
        return field;
    }

    public void setField(Rectangle2D.Double field) {
        this.field = field;
    }

    public Field[][] getGridArray() {
        return gridArray;
    }

    public void setGridArray(Field[][] gridArray) {
        this.gridArray = gridArray;
    }
}
