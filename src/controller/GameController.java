package controller;

import model.*;
import view.GraphicsFrame;
import view.GraphicsPanel;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameController {

    private Grid grid;
    private WinChecker winChecker;
    private ComputerOpponent opponent;
    private GraphicsFrame gFrame;
    private GraphicsPanel gPanel;
    private Player player1;
    private Player player2;

    public GameController() {
        this.grid = new Grid();
        this.winChecker = new WinChecker();
        this.gPanel = new GraphicsPanel();
        gPanel.addComponentListener(new ComponentAdapterClass());
        gPanel.addMouseListener(new MouseListenerClass());
        this.gFrame = new GraphicsFrame(gPanel);
    }

    public void startGame(String versus) {
        player1 = new Player("Player1", Color.RED, true);
        if (versus.equals("Player")) {
            player2 = new Player("Player2", Color.YELLOW, false);
        }
        else if (versus.equals("Computer")) {
            opponent = new ComputerOpponent();
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
        grid.updateGridPositions(gPanel.getWidth(), gPanel.getHeight());
        grid.resetFieldStates();
        grid.refreshFieldStates();
        gPanel.setGrid(grid);
        gPanel.repaint();
    }

    class ComponentAdapterClass implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            grid.updateGridPositions(gPanel.getWidth(), gPanel.getHeight());
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

        int filledBy;

        @Override
        public void mouseClicked(MouseEvent e) {

            updateFilledBy();
            if (grid.refreshGrid(e.getX(), e.getY(), filledBy)) {
                gPanel.setGrid(grid);
                grid.refreshFieldStates();
                updateMyTurn();
                gPanel.repaint();
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
                filledBy = FieldConstants.FILLED_BY_PLAYER1;
            }
            else {
                filledBy = FieldConstants.FILLED_BY_PLAYER2;
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
}
