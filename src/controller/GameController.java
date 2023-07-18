package controller;

import model.ComputerOpponent;
import model.Grid;
import model.WinChecker;
import view.GraphicsFrame;
import view.GraphicsPanel;

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
        this.gFrame = new GraphicsFrame(gPanel);
    }
}
