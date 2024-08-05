package model;

import java.awt.geom.Rectangle2D;

/**
 * Diese Klasse beinhaltet das zweidimensionale Array "gridArray", welches das Spielfeld darstellt.
 * Außerdem befinden sich hier Methoden, um die Inhalte des "gridArray" zu verändern, wie z.B. die Positionsdaten
 * oder den aktuellen Status des Feldes.
 */

public class GameGrid {

    private Field[][] fields; // Array für die Felder im Spielfeld
    private static final FieldStatus UNFILLED_UNFILLABLE = FieldStatus.UNFILLED_UNFILLABLE;
    private static final FieldStatus UNFILLED_FILLABLE = FieldStatus.UNFILLED_FILLABLE;
    private static final int ROW_LENGTH = 7;
    private static final int COL_LENGTH = 6;

    public GameGrid() {
        fields = new Field[ROW_LENGTH][COL_LENGTH];
        fillFields();
    }

    public void fillFields() {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                fields[i][j] = new Field();
            }
        }
    }

    public void updateFieldCoordinates(Rectangle2D.Double board) {

        double fieldHeight = board.getHeight();
        double fieldWidth = board.getWidth();
        final double gap = 30; // Abstand zwischen den Feldern
        double yDiameter = (fieldHeight - (7 * gap)) / 6; // (Höhe des Rechtecks - (7 * Abstand)) / 6 Felder
        double xDiameter = (fieldWidth - (8 * gap)) / 7; // (Breite des Rechtecks - (8 * Abstand)) / 7 Felder
        double xCord = board.getX() + gap;
        double yCord = board.getY() + gap;

        for (int i = 6; i >= 0; i--) {
            for (int j = 5; j >= 0; j--) {
                fields[i][j].setFrame(xCord, yCord, xDiameter, yDiameter);
                yCord += yDiameter + gap;
            }
            yCord = board.getY() + gap;
            xCord += xDiameter + gap;
        }
    }

    // Prüft, ob die Maus über einem Feld ist und ob es gefüllt werden kann
    public boolean isMouseOverValidField(int x, int y) {
        boolean isValid = false;
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                Field currentField = getFields()[i][j];
                if (currentField.contains(x, y) && currentField.getStatus() == UNFILLED_FILLABLE) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    public void fillField(int xCord, int yCord, Player currentPlayer) {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                Field currentField = getFields()[i][j];
                if (currentField.contains(xCord, yCord)) {
                    currentField.setStatus(currentPlayer.getFieldStatus());
                }
            }
        }
    }

    // Aktualisiert den HoverStatus aller Felder, je nachdem wo die Maus gerade ist
    public void updateHoverStates(int x, int y, HoverStatus status) {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                Field currentField = getFields()[i][j];
                if (currentField.contains(x,y) && currentField.getStatus() == UNFILLED_FILLABLE) {
                    if (status == HoverStatus.HOVERED_BY_PLAYER1) {
                        currentField.setHover(HoverStatus.HOVERED_BY_PLAYER1);
                    }
                    else if (status == HoverStatus.HOVERED_BY_PLAYER2) {
                        currentField.setHover(HoverStatus.HOVERED_BY_PLAYER2);
                    }
                }
                else {
                    currentField.setHover(HoverStatus.NO_HOVER);
                }
            }
        }
    }

    // Setzt den Status aller Felder im Grid zurück auf Standard, alle Felder sind leer
    public void resetFieldStates() {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                if (j == 0) {
                    getFields()[i][j].setStatus(UNFILLED_FILLABLE);
                }
                else {
                    getFields()[i][j].setStatus(UNFILLED_UNFILLABLE);
                }
            }
        }
        //printFieldStates();
    }

    // Aktualisiert die Status der Felder
    public void updateFieldStates() {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 1; j < COL_LENGTH; j++) {
                if (getFields()[i][j].getStatus() == UNFILLED_UNFILLABLE) {
                    if (getFields()[i][j-1].getStatus() == FieldStatus.FILLED_BY_PLAYER2 || getFields()[i][j-1].getStatus() == FieldStatus.FILLED_BY_PLAYER1) {
                        getFields()[i][j].setStatus(UNFILLED_FILLABLE);
                    }
                }
            }
        }
        //printFieldStates();
    }

    public void printFieldStates() {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                System.out.println("ROW = " + i + " COL = " + j + " Status = " + getFields()[i][j].getStatus());
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }
}
