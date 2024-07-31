package model;

/**
 * Diese Klasse beinhaltet Methoden, um einen Sieger zu erkennen.
 */

public class WinChecker {

    private static Field[][] gridArray;
    private static final WinnerStatus WINNER_PLAYER1 = WinnerStatus.WINNER_PLAYER1;
    private static final WinnerStatus WINNER_PLAYER2 = WinnerStatus.WINNER_PLAYER2;
    private static final WinnerStatus NO_WINNER = WinnerStatus.NO_WINNER;
    private static final FieldStatus UNFILLED_UNFILLABLE = FieldStatus.UNFILLED_UNFILLABLE;
    private static final FieldStatus UNFILLED_FILLABLE = FieldStatus.UNFILLED_FILLABLE;
    private static final FieldStatus FILLED_BY_PLAYER1 = FieldStatus.FILLED_BY_PLAYER1;
    private static final FieldStatus FILLED_BY_PLAYER2 = FieldStatus.FILLED_BY_PLAYER2;

    private WinChecker() {}

    public static WinnerStatus detectWinner(Grid newGrid) {
        gridArray = newGrid.getGridArray();

        WinnerStatus verticalWinnerStatus = detectVerticalWinner();
        WinnerStatus horizontalWinnerStatus = detectHorizontalWinner();
        WinnerStatus diagonalWinnerStatus = detectDiagonalWinner();

        if (verticalWinnerStatus == WINNER_PLAYER1 || horizontalWinnerStatus == WINNER_PLAYER1 || diagonalWinnerStatus == WinnerStatus.WINNER_PLAYER1) {
            return WINNER_PLAYER1;
        } else if (verticalWinnerStatus == WINNER_PLAYER2 || horizontalWinnerStatus == WINNER_PLAYER2 || diagonalWinnerStatus == WinnerStatus.WINNER_PLAYER2) {
            return WINNER_PLAYER2;
        }
        else {
            return NO_WINNER;
        }
    }

    private static WinnerStatus detectVerticalWinner() {

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

    private static WinnerStatus detectHorizontalWinner() {

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

    // TODO: Ist noch nicht fertig
    private static WinnerStatus detectDiagonalWinner() {

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

    private static WinnerStatus check4InARow(int player1, int player2) {
        if (player1 == 4) {
            return WINNER_PLAYER1;
        }
        else if (player2 == 4) {
            return WINNER_PLAYER2;
        }
        else {
            return NO_WINNER;
        }
    }
}
