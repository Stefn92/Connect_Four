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

            updateFilledBy();
            boolean refresh = grid.refreshGrid(e.getX(), e.getY(), filledBy);
            if (refresh) {
                grid.refreshFieldStates();
                if (winChecker.detectWinner(grid) == WinnerStatus.WINNER_PLAYER1 || winChecker.detectWinner(grid) == WinnerStatus.WINNER_PLAYER2) {
                    System.out.println("Es gibt einen Gewinner!");
                }
                gPanel.setGrid(grid);
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
}
