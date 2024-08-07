package controller;

import model.GameModel;
import model.HumanPlayer;
import model.Player;
import view.GridFrame;
import view.GridRenderer;

import java.awt.*;


public class Main {
    Player player;

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        player = new HumanPlayer("Player1", 2, Color.YELLOW);
        //startGameNormal();
        startGameTest();
    }

    public void startGameNormal() {
        GameController gc = new GameController();
        gc.startGame(player);
    }

    public void startGameTest() {
        GameModelInterface gameModelInterface = new GameModel();
        GridRenderer gridRenderer = new GridRenderer();
        GridFrame gridFrame = new GridFrame(gridRenderer);
        GameLogic gameLogic = new GameLogic(gameModelInterface, gridRenderer);
        InputController inputController = new InputController(gameLogic, gridRenderer, gridFrame);
        gameLogic.startGame();

    }
}
