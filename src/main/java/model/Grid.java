package model;

/**
 * Diese Klasse beinhaltet das zweidimensionale Array "gridArray", welches das Spielfeld darstellt.
 * Außerdem befinden sich hier Methoden, um die Inhalte des "gridArray" zu verändern, wie z.B. die Positionsdaten
 * oder den aktuellen Status des Feldes.
 */

public class Grid {

    private Field[][] fields; // Array für die Felder im Spielfeld
    private static final FillStatus UNFILLED_UNFILLABLE = FillStatus.UNFILLED_UNFILLABLE;
    private static final FillStatus UNFILLED_FILLABLE = FillStatus.UNFILLED_FILLABLE;
    private static final int ROW_LENGTH = 7;
    private static final int COL_LENGTH = 6;

    public Grid() {
        fields = new Field[ROW_LENGTH][COL_LENGTH];
        fillFields();
        resetFieldStates();
    }

    private void fillFields() {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                fields[i][j] = new Field();
            }
        }
    }

    public void updateFieldCoordinates(Board board) {

        double fieldHeight = board.getHeight();
        double fieldWidth = board.getWidth();
        final double GAP = 30; // Abstand zwischen den Feldern
        double yDiameter = (fieldHeight - (7 * GAP)) / 6; // (Höhe des Rechtecks - (7 * Abstand)) / 6 Felder
        double xDiameter = (fieldWidth - (8 * GAP)) / 7; // (Breite des Rechtecks - (8 * Abstand)) / 7 Felder
        double xCord = board.getX() + GAP;
        double yCord = board.getY() + GAP;

        for (int i = 6; i >= 0; i--) {
            for (int j = 5; j >= 0; j--) {
                fields[i][j].setFrame(xCord, yCord, xDiameter, yDiameter);
                yCord += yDiameter + GAP;
            }
            yCord = board.getY() + GAP;
            xCord += xDiameter + GAP;
        }
    }

    // Prüft, ob die Maus über einem Feld ist und ob es gefüllt werden kann
    public boolean isMouseOverValidField(int x, int y) {
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                Field currentField = getFields()[i][j];
                if (currentField.contains(x, y) && currentField.getStatus() == UNFILLED_FILLABLE) {
                    return true;
                }
            }
        }
        return false;
    }

    //
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
                    if (getFields()[i][j-1].getStatus() == FillStatus.FILLED_BY_PLAYER2 || getFields()[i][j-1].getStatus() == FillStatus.FILLED_BY_PLAYER1) {
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
