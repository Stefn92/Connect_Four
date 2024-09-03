package model;

import java.awt.*;

public class WinDetector {

    private static Field[][] gridArray;
    private static final WinnerStatus WINNER_PLAYER1 = WinnerStatus.WINNER_PLAYER1;
    private static final WinnerStatus WINNER_PLAYER2 = WinnerStatus.WINNER_PLAYER2;
    private static final WinnerStatus NO_WINNER = WinnerStatus.NO_WINNER;
    private static final FillStatus UNFILLED_UNFILLABLE = FillStatus.UNFILLED_UNFILLABLE;
    private static final FillStatus UNFILLED_FILLABLE = FillStatus.UNFILLED_FILLABLE;
    private static final FillStatus FILLED_BY_PLAYER1 = FillStatus.FILLED_BY_PLAYER1;
    private static final FillStatus FILLED_BY_PLAYER2 = FillStatus.FILLED_BY_PLAYER2;
    private static final int ROW_LENGTH = 7;
    private static final int COL_LENGTH = 6;
    private static final int WIN_COUNT = 4;
    private static int fieldsInARowPlayer1;
    private static int fieldsInARowPlayer2;

    private WinDetector() {}

    public static WinnerStatus detectWinner(Field[][] grid) {
        gridArray = grid;

        WinnerStatus verticalWinnerStatus = detectVerticalWinner();
        WinnerStatus horizontalWinnerStatus = detectHorizontalWinner();
        WinnerStatus diagonalWinnerStatus = detectDiagonalWinner();

        if (verticalWinnerStatus == WINNER_PLAYER1 || horizontalWinnerStatus == WINNER_PLAYER1 || diagonalWinnerStatus == WINNER_PLAYER1) {
            return WINNER_PLAYER1;
        } else if (verticalWinnerStatus == WINNER_PLAYER2 || horizontalWinnerStatus == WINNER_PLAYER2 || diagonalWinnerStatus == WINNER_PLAYER2) {
            return WINNER_PLAYER2;
        }
        else {
            return NO_WINNER;
        }
    }

    private static WinnerStatus detectVerticalWinner() {

        WinnerStatus winner = NO_WINNER;

        resetFieldsInARowBothPlayers();

        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {

                FillStatus currentStatus = gridArray[i][j].getStatus();
                changeFieldsInARowAccordingly(currentStatus);
                winner = check4InARow();
                if (winner == WINNER_PLAYER1 || winner == WINNER_PLAYER2) {
                    return winner;
                }
            }
            resetFieldsInARowBothPlayers();
        }
        return winner;
    }

    private static WinnerStatus detectHorizontalWinner() {

        WinnerStatus winner = NO_WINNER;

        resetFieldsInARowBothPlayers();

        for (int i = 0; i < COL_LENGTH; i++) {
            for (int j = 0; j < ROW_LENGTH; j++) {

                FillStatus currentStatus = gridArray[j][i].getStatus();

                changeFieldsInARowAccordingly(currentStatus);

                winner = check4InARow();
                if (winner == WINNER_PLAYER1 || winner == WINNER_PLAYER2) {
                    return winner;
                }
            }
            resetFieldsInARowBothPlayers();
        }
        return winner;
    }

    // TODO: Ist noch nicht fertig
    private static WinnerStatus detectDiagonalWinner() {

        WinnerStatus winner = NO_WINNER;

        int inARowPlayer1 = 0;
        int inARowPlayer2 = 0;

        int[] fieldLength = {4, 5, 6};

        final int ROWS = 6;
        final int COLS = 7;
        final int WIN_COUNT = 4;
        int counter = 0;

        resetFieldsInARowBothPlayers();

        for (int i = 2; i > 0; i--) {
            for (int j = 0; j < fieldLength[i]; j++) {
                FillStatus currentStatus = gridArray[j][i].getStatus();
                changeFieldsInARowAccordingly(currentStatus);
                winner = check4InARow();
                if (winner == WINNER_PLAYER1 || winner == WINNER_PLAYER2) {
                    return winner;
                }
            }
            resetFieldsInARowBothPlayers();
        }
        return winner;


        /*for (int row = 0; row <= ROWS - WIN_COUNT; row++) {
            for (int col = 0; col <= COLS - WIN_COUNT; col++) {
                if (gridArray[row + counter][col + counter].getStatus() == FILLED_BY_PLAYER1) {
                    incrementFieldsInARowPlayer1ByOne();
                }
                if (fieldsInARowPlayer1 == 4) {
                    winner = WINNER_PLAYER1;
                    return winner;
                }
                counter++;
            }
            counter = 0;
        }*/
    }

    private static WinnerStatus check4InARow() {
        if (fieldsInARowPlayer1 == WIN_COUNT) {
            return WINNER_PLAYER1;
        }
        else if (fieldsInARowPlayer2 == WIN_COUNT) {
            return WINNER_PLAYER2;
        }
        else {
            return NO_WINNER;
        }
    }

    private static boolean isFieldNotFilled(FillStatus currentStatus) {
        return currentStatus == UNFILLED_UNFILLABLE || currentStatus == UNFILLED_FILLABLE;
    }

    private static boolean isFieldFilledByPlayer1(FillStatus currentStatus) {
        return currentStatus == FILLED_BY_PLAYER1;
    }

    private static boolean isFieldFilledByPlayer2(FillStatus currentStatus) {
        return currentStatus == FILLED_BY_PLAYER2;
    }

    private static void changeFieldsInARowAccordingly(FillStatus currentStatus) {
        if (isFieldFilledByPlayer1(currentStatus)) {
            incrementFieldsInARowPlayer1ByOne();
            resetFieldsInARowPLayer2();
        }
        else if (isFieldFilledByPlayer2(currentStatus)) {
            incrementFieldsInARowPlayer2ByOne();
            resetFieldsInARowPlayer1();
        }
        else if (isFieldNotFilled(currentStatus)) {
            resetFieldsInARowPlayer1();
            resetFieldsInARowPLayer2();
        }
    }

    private static void incrementFieldsInARowPlayer1ByOne() {
        fieldsInARowPlayer1++;
    }

    private static void incrementFieldsInARowPlayer2ByOne() {
        fieldsInARowPlayer2++;
    }

    private static void resetFieldsInARowPlayer1() {
        fieldsInARowPlayer1 = 0;
    }

    private static void resetFieldsInARowPLayer2() {
        fieldsInARowPlayer2 = 0;
    }

    private static void resetFieldsInARowBothPlayers() {
        resetFieldsInARowPlayer1();
        resetFieldsInARowPLayer2();
    }
}
