package controller;

import model.ComputerOpponent;
import model.Grid;
import model.WinChecker;
import view.GraphicsFrame;
import view.GraphicsPanel;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameController {

    private Grid grid;
    private WinChecker winChecker;
    private ComputerOpponent opponent;
    private GraphicsFrame gFrame;
    private GraphicsPanel gPanel;

    public GameController() {
        this.grid = new Grid();
        this.winChecker = new WinChecker();
        this.gPanel = new GraphicsPanel();
        gPanel.addComponentListener(new ComponentAdapterClass());
        this.gFrame = new GraphicsFrame(gPanel);
        prepareGame();
    }

    public void prepareGame() {
        grid.updateGrid(gPanel.getWidth(), gPanel.getHeight());
        gPanel.setGrid(grid);
        gPanel.repaint();
    }

    class ComponentAdapterClass implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            grid.updateGrid(gPanel.getWidth(), gPanel.getHeight());
            gPanel.repaint();
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }
}
