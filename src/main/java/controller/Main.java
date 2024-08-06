package controller;

import model.HumanPlayer;
import view.MainMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private GameController gc;
    private final MainMenu gui;

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        gui = new MainMenu();
        gui.addRestartAgainstPlayerListener(new PlayAgainstPlayerClass());
    }

    class PlayAgainstPlayerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gc = new GameController();
            HumanPlayer player = new HumanPlayer("Player1", 2, Color.YELLOW);
            gc.startGame(player);
            gui.dispose();
        }
    }
}
