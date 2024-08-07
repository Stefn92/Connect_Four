package controller;

import model.*;
import view.GridRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class GameLogic {

    private final GridRenderer gridRenderer;
    private final Grid grid;
    private final Board board;
    private final GameStateMachine stateMachine;
    private Player player1;
    private Player player2;

    public GameLogic(GameModelInterface gameModelInterface, GridRenderer gridRenderer) {
        this.gridRenderer = gridRenderer;
        this.grid = gameModelInterface.getGrid();
        this.board = gameModelInterface.getBoard();
        this.stateMachine = gameModelInterface.getStateMachine();
        updateBoardCoordinates();
        updateView();
    }

    public void startGame() {
        stateMachine.handleEvent(GameEvent.START_GAME);
        player1 = new HumanPlayer("Player1", 1, Color.RED);
        player2 = new HumanPlayer("Player2", 2, Color.YELLOW);

        System.out.println("Game started");
    }

    public void updateView() {
        Field[][] gridArray = grid.getFields();
        Rectangle2D.Double rect = board.getBoardAsRectangle2D();

        gridRenderer.setRect(rect);
        gridRenderer.setGrid(gridArray);

        gridRenderer.repaint();
    }

    public void updateBoardCoordinates() {
        int width = gridRenderer.getWidth();
        int height = gridRenderer.getHeight();

        board.updateBoardCoordinates(width, height);
        grid.updateFieldCoordinates(board);
    }

    public void handleMouseHover(MouseEvent e) {
        GameState currentState = stateMachine.getCurrentGameState();
        if (currentState == GameState.PLAYER1_TURN) {
            grid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
        }
        else if (currentState == GameState.PLAYER2_TURN) {
            grid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
        }
    }

    public void player1Turn(int x, int y) {
        if (stateMachine.getCurrentGameState() == GameState.PLAYER1_TURN) {
            grid.fillField(x, y, player1);
            player1.decrementChipsRemainingByOne();
            endTurn();
            stateMachine.handleEvent(GameEvent.PLAYER1_MOVED);
        }
    }

    public void player2Turn(int x, int y) {
        if (stateMachine.getCurrentGameState() == GameState.PLAYER2_TURN) {
            grid.fillField(x, y, player2);
            player2.decrementChipsRemainingByOne();
            endTurn();
            stateMachine.handleEvent(GameEvent.PLAYER2_MOVED);
        }
    }

    public void endTurn() {
        grid.updateFieldStates();
        checkForWinner();
        checkForDraw();
        updateView();
    }

    public void checkForWinner() {
        Field[][] gridArray = grid.getFields();
        WinnerStatus winnerStatus = WinDetector.detectWinner(gridArray);
        if (winnerStatus == WinnerStatus.WINNER_PLAYER1 || winnerStatus == WinnerStatus.WINNER_PLAYER2) {
            System.out.println("someone has won the game: " + winnerStatus);
            stateMachine.handleEvent(GameEvent.GAME_ENDED);
        }
    }

    public void checkForDraw() {
        if (player1.getChipsRemaining() == 0 && player2.getChipsRemaining() == 0) {
            System.out.println("the game ends on a draw");
            stateMachine.handleEvent(GameEvent.GAME_ENDED);
        }
    }

    public void restartGame() {
        resetPlayersChipsRemaining();
        grid.resetFieldStates();
        stateMachine.handleEvent(GameEvent.RESTART_GAME);
        startGame();
    }

    private void resetPlayersChipsRemaining() {
        player1.setChipsRemaining((byte) 21);
        player2.setChipsRemaining((byte) 21);
    }

    public void handleMouseClick(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (grid.isMouseOverValidField(x, y)) {
            System.out.println("valid mouseevent at x = " + x + ", y = " + y);
            GameState currentState = stateMachine.getCurrentGameState();
            if (currentState == GameState.PLAYER1_TURN) {
                player1Turn(x, y);
            }
            else if (currentState == GameState.PLAYER2_TURN) {
                player2Turn(x, y);
            }
        }
        else {
            System.out.println("invalid mouseevent at x = " + x + ", y = " + y);
        }
    }
}
