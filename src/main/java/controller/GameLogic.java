package controller;

import model.*;
import view.GridFrame;
import view.GridRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class GameLogic {

    private final GameModelInterface gameModelInterface;
    private final GridRenderer gridRenderer;
    private Player player1;
    private Player player2;

    public GameLogic(GameModelInterface gameModelInterface, GridRenderer gridRenderer) {
        this.gameModelInterface = gameModelInterface;
        this.gridRenderer = gridRenderer;
    }

    public void startGame(Player player) {
        gameModelInterface.getStateMachine().handleEvent(GameEvent.START_GAME);
        player1 = new HumanPlayer("Player1", 1, Color.RED);
        player2 = player;

        calculateGrid();
        System.out.println("Spiel gestartet");
    }

    public void calculateGrid() {
        updateBoardCoordinates();
        gameModelInterface.getGrid().resetFieldStates();
        gameModelInterface.getGrid().updateFieldStates();
        updateView();
    }

    public void updateView() {
        Field[][] gridArray = gameModelInterface.getGrid().getFields();
        Rectangle2D.Double rect = gameModelInterface.getBoard().getBoardAsRectangle2D();

        gridRenderer.setRect(rect);
        gridRenderer.setGrid(gridArray);

        gridRenderer.repaint();
    }

    public void updateBoardCoordinates() {
        int width = gridRenderer.getWidth();
        int height = gridRenderer.getHeight();

        Board board = gameModelInterface.getBoard();
        board.updateBoardCoordinates(width, height);
        gameModelInterface.getGrid().updateFieldCoordinates(board);
    }

    public void handleMouseHover(MouseEvent e) {
        GameState currentState = gameModelInterface.getStateMachine().getCurrentGameState();
        if (currentState == GameState.PLAYER1_TURN) {
            gameModelInterface.getGrid().updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
        }
        else if (currentState == GameState.PLAYER2_TURN) {
            gameModelInterface.getGrid().updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
        }
        updateView();
    }

    public void player1Turn(int x, int y) {
        if (gameModelInterface.getStateMachine().getCurrentGameState() == GameState.PLAYER1_TURN) {
            gameModelInterface.getGrid().fillField(x, y, player1);
            player1.decrementChipsRemainingByOne();
            endTurn();
            gameModelInterface.getStateMachine().handleEvent(GameEvent.PLAYER1_MOVED);
        }
    }

    public void player2Turn(int x, int y) {
        if (gameModelInterface.getStateMachine().getCurrentGameState() == GameState.PLAYER2_TURN) {
            gameModelInterface.getGrid().fillField(x, y, player2);
            player2.decrementChipsRemainingByOne();
            endTurn();
            gameModelInterface.getStateMachine().handleEvent(GameEvent.PLAYER2_MOVED);
        }
    }

    public void endTurn() {
        gameModelInterface.getGrid().updateFieldStates();
        checkForWinner();
        checkForDraw();
        updateView();
    }

    public void checkForWinner() {
        Field[][] gridArray = gameModelInterface.getGrid().getFields();
        WinnerStatus winnerStatus = WinDetector.detectWinner(gridArray);
        if (winnerStatus == WinnerStatus.WINNER_PLAYER1 || winnerStatus == WinnerStatus.WINNER_PLAYER2) {
            System.out.println("someone has won the game: " + winnerStatus);
            gameModelInterface.getStateMachine().handleEvent(GameEvent.GAME_ENDED);
        }
    }

    public void checkForDraw() {
        if (player1.getChipsRemaining() == 0 && player2.getChipsRemaining() == 0) {
            System.out.println("the game ends on a draw");
            gameModelInterface.getStateMachine().handleEvent(GameEvent.GAME_ENDED);
        }
    }

    public void handleMouseClick(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (gameModelInterface.getGrid().isMouseOverValidField(x, y)) {
            System.out.println("valid mouseevent at x = " + x + ", y = " + y);
            GameState currentState = gameModelInterface.getStateMachine().getCurrentGameState();
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
