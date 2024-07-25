package model;

/**
 * Diese Klasse beinhaltet Methoden, um einen Sieger zu erkennen.
 */

public class WinChecker {

    private Grid grid;
    private Field[][] gridArray;
    private static final WinnerStatus WINNER_PLAYER1 = WinnerStatus.WINNER_PLAYER1;
    private static final WinnerStatus WINNER_PLAYER2 = WinnerStatus.WINNER_PLAYER2;
    private static final WinnerStatus NO_WINNER = WinnerStatus.NO_WINNER;
    private static final FieldStatus UNFILLED_UNFILLABLE = FieldStatus.UNFILLED_UNFILLABLE;
    private static final FieldStatus UNFILLED_FILLABLE = FieldStatus.UNFILLED_FILLABLE;
    private static final FieldStatus FILLED_BY_PLAYER1 = FieldStatus.FILLED_BY_PLAYER1;
    private static final FieldStatus FILLED_BY_PLAYER2 = FieldStatus.FILLED_BY_PLAYER2;

    public WinnerStatus detectWinner(Grid grid) {
        this.grid = grid;
        gridArray = grid.getGridArray();

        WinnerStatus result1 = detectVerticalWinner();
        WinnerStatus result2 = detectHorizontalWinner();
        WinnerStatus result3 = detectDiagonalWinner();

        if (result1 == WINNER_PLAYER1 || result2 == WINNER_PLAYER1 || result3 == WinnerStatus.WINNER_PLAYER1) {
            return WINNER_PLAYER1;
        } else if (result1 == WINNER_PLAYER2 || result2 == WINNER_PLAYER2 || result3 == WinnerStatus.WINNER_PLAYER2) {
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

                FieldStatus currentStatus = gridArray[i][j].getStatus();

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

    private WinnerStatus detectDiagonalWinner() {

        WinnerStatus winner = NO_WINNER;

        int inARowPlayer1 = 0;
        int inARowPlayer2 = 0;

        int[] fieldLength = {4, 5, 6, 6, 5, 4};

        int ROWS = 6;
        int COLS = 7;
        int WIN_COUNT = 4;

        for (int row = 0; row <= ROWS - WIN_COUNT; row++) {
            for (int col = 0; col <= COLS - WIN_COUNT; col++) {
                if (gridArray[row][col].getStatus() == FILLED_BY_PLAYER1 &&
                        gridArray[row + 1][col + 1].getStatus() == FILLED_BY_PLAYER1 &&
                        gridArray[row + 2][col + 2].getStatus() == FILLED_BY_PLAYER1 &&
                        gridArray[row + 3][col + 3].getStatus() == FILLED_BY_PLAYER1) {
                    winner = WinnerStatus.WINNER_PLAYER1;
                    break;
                }
            }
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
