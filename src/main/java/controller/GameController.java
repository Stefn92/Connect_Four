package controller;

import model.*;
import view.GridFrame;
import view.GridRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class GameController {

    private final Grid grid;
    private final Board board;
    private final GridRenderer gridRenderer;
    private GridFrame gFrame;
    private Player player1;
    private Player player2;
    private final GameStateMachine stateMachine;

    public GameController() {
        this.grid = new Grid();
        this.board = new Board();
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
        grid.resetFieldStates();
        grid.updateFieldStates();
        updateView();
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
                    grid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
                }
                else if (currentState == GameState.PLAYER2_TURN) {
                    grid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
                }
                updateView();
            }
        });
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
        checkForDraw();
        checkForWinner();
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
