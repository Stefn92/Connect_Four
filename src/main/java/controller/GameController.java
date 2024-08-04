package controller;

import model.*;
import view.GridFrame;
import view.GridRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class GameController {

    private final GameGrid gameGrid;
    private final GameBoard gameBoard;
    private final GridRenderer gridRenderer;
    private GridFrame gFrame;
    private Player player1;
    private Player player2;
    private final GameStateMachine stateMachine;

    public GameController() {
        this.gameGrid = new GameGrid();
        this.gameBoard = new GameBoard();
        this.gridRenderer = new GridRenderer();
        setupMouseHoverListener();
        setupResizeListener();
        setupMouseListener();
        this.gFrame = new GridFrame(gridRenderer);
        this.stateMachine = new GameStateMachine();
    }

    public void startGame(Player player) {
        stateMachine.handleEvent(GameEvent.START_GAME);
        player1 = new HumanPlayer("Player1", 1, Color.RED);
        player2 = player;

        calculateGrid();
    }

    public void resumeGame() {
        // TODO
    }

    public void restartGame() {
        stateMachine.handleEvent(GameEvent.RESTART_GAME);
        // TODO
    }

    public void calculateGrid() {

        updateBoardCoordinates();
        gameGrid.resetFieldStates();
        gameGrid.updateFieldStates();
        updateView();
    }

    public void updateView() {
        Field[][] gridArray = gameGrid.getFields();
        Rectangle2D.Double rect = gameBoard.getBoard();

        gridRenderer.setGrid(gridArray);
        gridRenderer.setRect(rect);

        gridRenderer.repaint();
    }

    public void updateBoardCoordinates() {
        int width = gridRenderer.getWidth();
        int height = gridRenderer.getHeight();
        Rectangle2D.Double board = gameBoard.getBoard();

        gameBoard.updateBoardCoordinates(width, height);
        gameGrid.updateFieldCoordinates(board);
    }

    public void setupMouseListener() {
        this.gridRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    handleMouseClick(e);
                }
            }
        });
    }

    public void setupResizeListener() {
        this.gridRenderer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBoardCoordinates();
                updateView();
            }
        });
    }

    public void setupMouseHoverListener() {
        this.gridRenderer.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                GameState currentState = stateMachine.getCurrentGameState();
                if (currentState == GameState.PLAYER1_TURN) {
                    gameGrid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
                }
                else if (currentState == GameState.PLAYER2_TURN) {
                    gameGrid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
                }
                updateView();
            }
        });
    }

    public void handleTurn(MouseEvent e) {
        GameState currentGameState = stateMachine.getCurrentGameState();
        int x = e.getX();
        int y = e.getY();
        switch (currentGameState) {
            case GAME_START -> {
                // Do Stuff
                stateMachine.handleEvent(GameEvent.START_GAME);
            }
            case PLAYER1_TURN -> {
                playerTakeTurn(x, y, player1);
                stateMachine.handleEvent(GameEvent.PLAYER1_MOVED);
            }
            case PLAYER2_TURN -> {
                playerTakeTurn(x, y, player2);
                stateMachine.handleEvent(GameEvent.PLAYER2_MOVED);
            }
            case GAME_OVER ->
                // Do Stuff
                stateMachine.handleEvent(GameEvent.RESTART_GAME);
        }
    }

    public void playerTakeTurn(int x, int y, Player currentPlayer) {
            gameGrid.fillField(x, y, currentPlayer);
            gameGrid.updateFieldStates();
            checkForWinner();
            updateView();
    }

    public void checkForWinner() {
        Field[][] gridArray = gameGrid.getFields();
        WinnerStatus winnerStatus = WinChecker.detectWinner(gridArray);
        if (winnerStatus == WinnerStatus.WINNER_PLAYER1 || winnerStatus == WinnerStatus.WINNER_PLAYER2) {
            System.out.println("someone has won the game: " + winnerStatus);
            stateMachine.changeState(GameState.GAME_OVER);
        }
    }

    public void handleMouseClick(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (gameGrid.isMouseOverValidField(x, y)) {
            System.out.println("valid mouseevent at x = " + x + ", y = " + y);
            handleTurn(e);
        }
        else {
            System.out.println("invalid mouseevent at x = " + x + ", y = " + y);
        }
    }
}
