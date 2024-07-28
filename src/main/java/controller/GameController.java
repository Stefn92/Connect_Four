package controller;

import model.*;
import view.GraphicsFrame;
import view.GraphicsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController {

    private Grid grid;
    private WinChecker winChecker;
    private ComputerPlayer opponent;
    private GraphicsFrame gFrame;
    private GraphicsPanel gPanel;
    private Player player1;
    private Player player2;

    public GameController() {
        this.grid = new Grid();
        this.gPanel = new GraphicsPanel();
        gPanel.addComponentListener(new ComponentAdapterClass());
        gPanel.addMouseListener(new MouseListenerClass());
        gPanel.addMouseMotionListener(new MouseMotionListener());
        this.gFrame = new GraphicsFrame(gPanel);
    }

    public void startGame(Player player) {
        player1 = new HumanPlayer("Player1", 1, Color.RED, true);
        player2 = player;

        calculateGrid();
    }

    public void resumeGame() {
        // TODO
    }

    public void restartGame() {
        // TODO
    }

    public void calculateGrid() {
        grid.fillGridArray();
        grid.updateGridDimensions(gPanel.getWidth(), gPanel.getHeight());
        grid.resetFieldStates();
        grid.refreshFieldStates();
        gPanel.setGrid(grid);
    }

    class ComponentAdapterClass implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            grid.updateGridDimensions(gPanel.getWidth(), gPanel.getHeight());
            gPanel.setGrid(grid);
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // Wird nicht gebraucht
        }

        @Override
        public void componentShown(ComponentEvent e) {
            // Wird nicht gebraucht
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            // Wird nicht gebraucht
        }
    }

    class MouseListenerClass implements MouseListener {

        FieldStatus filledBy;

        @Override
        public void mouseClicked(MouseEvent e) {

            if (SwingUtilities.isLeftMouseButton(e)) {
                updateFilledBy();
                boolean refresh = grid.isMouseOverValidField(e.getX(), e.getY());
                if (refresh) {
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

        @Override
        public void mousePressed(MouseEvent e) {
            // Wird nicht gebraucht
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Wird nicht gebraucht
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Wird nicht gebraucht
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Wird nicht gebraucht
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

    class MouseMotionListener implements java.awt.event.MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // Wird nicht gebraucht
        }

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
