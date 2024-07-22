package model;

/**
 * Diese Klasse beinhaltet Methoden, um einen Sieger zu erkennen.
 */

public class WinChecker {

    private Grid grid;
    private Field[][] gridArray;
    private WinnerStatus WINNER_PLAYER1 = WinnerStatus.WINNER_PLAYER1;
    private WinnerStatus WINNER_PLAYER2 = WinnerStatus.WINNER_PLAYER2;
    private WinnerStatus NO_WINNER = WinnerStatus.NO_WINNER;
    private FieldStatus UNFILLED_UNFILLABLE = FieldStatus.UNFILLED_UNFILLABLE;
    private FieldStatus UNFILLED_FILLABLE = FieldStatus.UNFILLED_FILLABLE;
    private FieldStatus FILLED_BY_PLAYER1 = FieldStatus.FILLED_BY_PLAYER1;
    private FieldStatus FILLED_BY_PLAYER2 = FieldStatus.FILLED_BY_PLAYER2;

    public WinnerStatus detectWinner(Grid grid) {
        this.grid = grid;
        gridArray = grid.getGridArray();

        WinnerStatus result1 = detectVerticalWinner();
        WinnerStatus result2 = detectHorizontalWinner();

        if (result1 == WINNER_PLAYER1 || result2 == WINNER_PLAYER1) {
            return WINNER_PLAYER1;
        } else if (result1 == WINNER_PLAYER2 || result2 == WINNER_PLAYER2) {
            return WINNER_PLAYER2;
        }
        else {
            return NO_WINNER;
        }
    }

    private WinnerStatus detectVerticalWinner() {

        WinnerStatus winner = NO_WINNER;

        int inARowPlayer1 = 0;
        int inARowPlayer2 = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (gridArray[i][j].getStatus() == FILLED_BY_PLAYER1) {
                    inARowPlayer1++;
                }
                else if (gridArray[i][j].getStatus() == UNFILLED_UNFILLABLE || gridArray[i][j].getStatus() == FILLED_BY_PLAYER2 || gridArray[i][j].getStatus() == UNFILLED_FILLABLE) {
                    inARowPlayer1 = 0;
                }
                if (gridArray[i][j].getStatus() == FILLED_BY_PLAYER2) {
                    inARowPlayer2++;
                }
                else if (gridArray[i][j].getStatus() == UNFILLED_UNFILLABLE || gridArray[i][j].getStatus() == FILLED_BY_PLAYER1 || gridArray[i][j].getStatus() == UNFILLED_FILLABLE) {
                    inARowPlayer2 = 0;
                }
                if (inARowPlayer1 == 4) {
                    winner = WINNER_PLAYER1;
                    break;
                }
                else if (inARowPlayer2 == 4) {
                    winner = WINNER_PLAYER2;
                    break;
                }
            }
            inARowPlayer1 = 0;
            inARowPlayer2 = 0;
        }
        return winner;
    }

    private WinnerStatus detectHorizontalWinner() {

        WinnerStatus winner = NO_WINNER;

        int inARowPlayer1 = 0;
        int inARowPlayer2 = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {

                FieldStatus currentStatus = gridArray[j][i].getStatus();

                if (currentStatus == FILLED_BY_PLAYER1) {
                    inARowPlayer1++;
                    inARowPlayer2 = 0;
                }
                else if (currentStatus == FILLED_BY_PLAYER2) {
                    inARowPlayer2++;
                    inARowPlayer1 = 0;
                }

                winner = check4InARow(inARowPlayer1, inARowPlayer2);
            }
            if (winner == WINNER_PLAYER1 || winner == WINNER_PLAYER2) {
                break;
            }
            inARowPlayer1 = 0;
            inARowPlayer2 = 0;
        }
        return winner;
    }

    private WinnerStatus check4InARow(int player1, int player2) {
        if (player1 == 4) {
            return WINNER_PLAYER1;
        } else if (player2 == 4) {
            return WINNER_PLAYER2;
        }
        else {
            return NO_WINNER;
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
