package controller;

import model.*;
import view.GraphicsFrame;
import view.GraphicsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController {

    private Grid grid;
    private GraphicsPanel gPanel;
    private GraphicsFrame gFrame;
    private Player player1;
    private Player player2;
    private GameStateMachine stateMachine;

    public GameController() {
        this.grid = new Grid();
        this.gPanel = new GraphicsPanel();
        gPanel.addComponentListener(new ResizeListener());
        gPanel.addMouseListener(new ClickListener());
        gPanel.addMouseMotionListener(new HoverListener());
        this.gFrame = new GraphicsFrame(gPanel);
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
        grid.updateGridDimensions(gPanel.getWidth(), gPanel.getHeight());
        grid.resetFieldStates();
        grid.refreshFieldStates();
        gPanel.setGrid(grid);
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            grid.updateGridDimensions(gPanel.getWidth(), gPanel.getHeight());
            gPanel.setGrid(grid);
        }
    }

    class ClickListener extends MouseAdapter {

        FieldStatus filledBy;

        @Override
        public void mouseClicked(MouseEvent e) {

            if (SwingUtilities.isLeftMouseButton(e)) {
                updateFilledBy();
                boolean refresh = grid.isMouseOverValidField(e.getX(), e.getY());
                if (refresh) {
                    stateMachine.handleEvent(GameEvent.PLAYER1_MOVED);
                    grid.refreshGrid(e.getX(), e.getY(), filledBy);
                    grid.refreshFieldStates();
                    WinnerStatus winnerStatus = WinChecker.detectWinner(grid);
                    if (winnerStatus == WinnerStatus.WINNER_PLAYER1 || winnerStatus == WinnerStatus.WINNER_PLAYER2) {
                        System.out.println("Es gibt einen Gewinner!");
                    }
                    gPanel.setGrid(grid);
                    updateMyTurn();
                }
            }
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
                grid.refreshHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
            }
            else if (player2.isMyTurn()) {
                grid.refreshHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
            }
            gPanel.setGrid(grid);
        }
    }
}
