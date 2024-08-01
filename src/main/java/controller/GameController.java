package controller;

import model.*;
import view.GraphicsFrame;
import view.GridRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class GameController {

    private final Grid grid;
    private GridRenderer gridRenderer;
    private GraphicsFrame gFrame;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final GameStateMachine stateMachine;
    private Point currentMouseClick;

    public GameController() {
        this.grid = new Grid();
        this.gridRenderer = new GridRenderer();
        gridRenderer.addMouseListener(new ClickListener());
        //gridRenderer.addMouseMotionListener(new HoverListener());
        setupMouseHoverListener();
        setupResizeListener();
        this.gFrame = new GraphicsFrame(gridRenderer);
        this.stateMachine = new GameStateMachine();
    }

    public void startGame(Player player) {
        stateMachine.handleEvent(GameEvent.START_GAME);
        player1 = new HumanPlayer("Player1", 1, Color.RED, true);
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
        grid.fillGridArray();
        grid.updateGridDimensions(gridRenderer.getWidth(), gridRenderer.getHeight());
        grid.resetFieldStates();
        grid.updateFieldStates();
        updateView();
    }

    public void updateView() {
        Field[][] gridArray = grid.getGridArray();
        gridRenderer.setGridAndRepaint(gridArray);
        Rectangle2D.Double rect = grid.getField();
        gridRenderer.setRectAndRepaint(rect);
    }

    public void setupMouseListener() {
        this.gridRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mausklick
            }
        });
    }

    public void setupResizeListener() {
        this.gridRenderer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = gridRenderer.getWidth();
                int height = gridRenderer.getHeight();
                grid.updateGridDimensions(width, height);
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

    public void turn() {
        GameState currentGameState = stateMachine.getCurrentGameState();
        switch (currentGameState) {
            case PLAYER1_TURN:
                // Wait For valid MouseClick
                currentPlayer = player1;
                break;
            case PLAYER2_TURN:
                // Wait for valid MouseClick
                currentPlayer = player2;
                //stateMachine.handleEvent(GameEvent.PLAYER2_MOVED);
                break;
            case GAME_OVER:
                // Do Stuff
                stateMachine.handleEvent(GameEvent.RESTART_GAME);
                break;
        }
    }

    public void player1Move() {
        if (stateMachine.getCurrentGameState() == GameState.PLAYER1_TURN) {
            // Logik für Zug


            stateMachine.handleEvent(GameEvent.PLAYER1_MOVED);
        }
    }

    public void checkForWinner() {
        Field[][] gridArray = grid.getGridArray();
        WinnerStatus winnerStatus = WinChecker.detectWinner(gridArray);
        if (winnerStatus == WinnerStatus.WINNER_PLAYER1 || winnerStatus == WinnerStatus.WINNER_PLAYER2) {
            System.out.println("Es gibt einen Gewinner!");
            stateMachine.changeState(GameState.GAME_OVER);
        }
    }

    public void handleMouseClick() {
        turn();
        int x = (int) currentMouseClick.getX();
        int y = (int) currentMouseClick.getY();
        if (grid.isMouseOverValidField(x,y)) {
            grid.makeTurn(x, y, currentPlayer);
            //grid.refreshGrid(x, y, filledBy);
            grid.updateFieldStates();
            checkForWinner();
            if (currentPlayer == player1) {
                stateMachine.handleEvent(GameEvent.PLAYER1_MOVED);
            }
            else if (currentPlayer == player2) {
                stateMachine.handleEvent(GameEvent.PLAYER2_MOVED);
            }
            updateView();
            //updateMyTurn();
        }
    }

    class ClickListener extends MouseAdapter {

        FieldStatus filledBy;

        @Override
        public void mouseClicked(MouseEvent e) {

            currentMouseClick = new Point(e.getX(), e.getY());
            handleMouseClick();
            /*boolean valid = grid.isMouseOverValidField(e.getX(), e.getY());

            if (SwingUtilities.isLeftMouseButton(e)) {
                updateFilledBy();
                if (valid) {
                    currentPlayer = player1;
                    grid.makeTurn(e.getX(), e.getY(), currentPlayer);
                    grid.refreshGrid(e.getX(), e.getY(), filledBy);
                    grid.updateFieldStates();
                    checkForWinner();
                    stateMachine.handleEvent(GameEvent.PLAYER1_MOVED);
                    updateView();
                    updateMyTurn();
                }
            }*/
        }

        public void updateFilledBy() {
            if (player1.isMyTurn()) {
                filledBy = FieldStatus.FILLED_BY_PLAYER1;
            }
            else {
                filledBy = FieldStatus.FILLED_BY_PLAYER2;
            }
        }

        public void updateMyTurn() {
            if (player1.isMyTurn()) {
                player1.setMyTurn(false);
                player2.setMyTurn(true);
            }
            else {
                player2.setMyTurn(false);
                player1.setMyTurn(true);
            }
        }
    }

    class HoverListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            if (player1.isMyTurn()) {
                grid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
            }
            else if (player2.isMyTurn()) {
                grid.updateHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
            }
            updateView();
        }
    }
}
