package model;

import java.awt.geom.Rectangle2D;

/**
 * Diese Klasse beinhaltet das zweidimensionale Array "gridArray", welches das Spielfeld darstellt.
 * Außerdem befinden sich hier Methoden, um die Inhalte des "gridArray" zu verändern, wie z.B. die Positionsdaten
 * oder den aktuellen Status des Feldes.
 */

public class Grid {

    private Rectangle2D.Double field; // Das Rechteck für das Spielfeld
    private Field[][] gridArray; // Array für die Felder im Spielfeld
    private static final FieldStatus UNFILLED_UNFILLABLE = FieldStatus.UNFILLED_UNFILLABLE;
    private static final FieldStatus UNFILLED_FILLABLE = FieldStatus.UNFILLED_FILLABLE;

    public Grid() {
        field = new Rectangle2D.Double();
        gridArray = new Field[7][6];
    }

    public void fillGridArray() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                gridArray[i][j] = new Field();
            }
        }
    }

    // Aktualisiert die Positionsdaten des Rechtecks und der Kreise
    public void updateGridDimensions(int panelWidth, int panelHeight) {

        updateRectCoordinates(panelWidth, panelHeight);
        updateEllipseCoordinates();
    }

    private void updateRectCoordinates(int panelWidth, int panelHeight) {

        int xPos = 50;
        int yPos = 50;
        int xPos2 = (panelWidth * 12) / 100;
        int yPos2 = (panelHeight * 7) / 100;
        int fieldWidth = panelWidth - (xPos2 * 2);
        int fieldHeight = panelHeight - (yPos2 * 2);
        field.setRect(xPos2, yPos2, fieldWidth, fieldHeight);
        System.out.println("Rechteck: Heigth=" + field.getHeight() + "Width=" + field.getWidth());
    }

    private void updateEllipseCoordinates() {

        double fieldHeight = field.getHeight();
        double fieldWidth = field.getWidth();
        double gap = 30; // Abstand zwischen den Feldern
        double yDiameter = (fieldHeight - (7 * gap)) / 6; // (Höhe des Rechtecks - (7 * Abstand)) / 6 Felder
        double xDiameter = (fieldWidth - (8 * gap)) / 7; // (Weite des Rechtecks - (8 * Abstand)) / 7 Felder
        double xCord = field.getX() + gap;
        double yCord = field.getY() + gap;

        for (int i = 6; i >= 0; i--) {
            for (int j = 5; j >= 0; j--) {
                gridArray[i][j].setFrame(xCord, yCord, xDiameter, yDiameter);
                yCord += yDiameter + gap;
            }
            yCord = field.getY() + gap;
            xCord += xDiameter + gap;
        }
    }

    // Gibt bei gültiger Benutzereingabe "True" zurück und ändert den Status des betreffenden Feldes
    public boolean refreshGrid(int x, int y, FieldStatus filledBy) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Field currentField = getGridArray()[i][j];
                if (currentField.contains(x,y) && currentField.getStatus() == UNFILLED_FILLABLE) {
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
                    getGridArray()[i][j].setStatus(UNFILLED_FILLABLE);
                }
                else {
                    getGridArray()[i][j].setStatus(UNFILLED_UNFILLABLE);
                }
            }
        }
        printFieldStates();
    }

    // Aktualisiert die Status der Felder
    public void refreshFieldStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 1; j < 6; j++) {
                if (getGridArray()[i][j].getStatus() == UNFILLED_UNFILLABLE) {
                    if (getGridArray()[i][j-1].getStatus() == FieldStatus.FILLED_BY_PLAYER2 || getGridArray()[i][j-1].getStatus() == FieldStatus.FILLED_BY_PLAYER1) {
                        getGridArray()[i][j].setStatus(UNFILLED_FILLABLE);
                    }
                }
            }
        }
        printFieldStates();
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
