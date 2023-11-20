package controller;

import view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    GameController gc;
    GUI gui;

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        gui = new GUI();
        gui.addRestartAgainstPlayerListener(new PlayAgainstPlayerClass());
        //gc = new GameController();
        //gc.startGame();
    }

    class PlayAgainstPlayerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gc = new GameController();
            gc.startGame("Player");
            gui.dispose();
        }
    }
}
