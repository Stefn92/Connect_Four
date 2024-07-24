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
    private HumanPlayer humanPlayer1;
    private HumanPlayer humanPlayer2;

    public GameController() {
        this.grid = new Grid();
        this.winChecker = new WinChecker();
        this.gPanel = new GraphicsPanel();
        gPanel.addComponentListener(new ComponentAdapterClass());
        gPanel.addMouseListener(new MouseListenerClass());
        gPanel.addMouseMotionListener(new MouseMotionListener());
        this.gFrame = new GraphicsFrame(gPanel);
    }

    public void startGame(String versus) {
        humanPlayer1 = new HumanPlayer("Player1", 1, Color.RED, true);
        if (versus.equals("Player")) {
            humanPlayer2 = new HumanPlayer("Player2", 2, Color.YELLOW, false);
        }
        else if (versus.equals("Computer")) {
            opponent = new ComputerPlayer("Computer1", 2, Color.BLUE, false);
        }
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
        gPanel.repaint();
    }

    class ComponentAdapterClass implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            grid.updateGridDimensions(gPanel.getWidth(), gPanel.getHeight());
            gPanel.repaint();
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
                    if (winChecker.detectWinner(grid) == WinnerStatus.WINNER_PLAYER1 || winChecker.detectWinner(grid) == WinnerStatus.WINNER_PLAYER2) {
                        System.out.println("Es gibt einen Gewinner!");
                    }
                    gPanel.setGrid(grid);
                    updateMyTurn();
                    gPanel.repaint();
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
            if (humanPlayer1.isMyTurn()) {
                filledBy = FieldStatus.FILLED_BY_PLAYER1;
            }
            else {
                filledBy = FieldStatus.FILLED_BY_PLAYER2;
            }
        }

        public void updateMyTurn() {
            if (humanPlayer1.isMyTurn()) {
                humanPlayer1.setMyTurn(false);
                humanPlayer2.setMyTurn(true);
            }
            else {
                humanPlayer2.setMyTurn(false);
                humanPlayer1.setMyTurn(true);
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
            if (humanPlayer1.isMyTurn()) {
                grid.refreshHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER1);
            }
            else if (humanPlayer2.isMyTurn()) {
                grid.refreshHoverStates(e.getX(), e.getY(), HoverStatus.HOVERED_BY_PLAYER2);
            }
            gPanel.repaint();
        }
    }
}
