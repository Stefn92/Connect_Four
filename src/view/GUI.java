package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    JPanel mainPanel;
    JButton resumeButton;
    JButton playAgainstPlayerButton;
    JButton playAgainstComputerButton;
    JButton statisticsButton;
    JButton endGameButton;

    public GUI() {

        mainPanel = new JPanel(new GridLayout(3, 1));
        setContentPane(mainPanel);

        playAgainstPlayerButton = new JButton("Spieler vs Spieler");
        playAgainstComputerButton = new JButton("Spieler vs Computer");
        statisticsButton = new JButton("Statistik");
        endGameButton = new JButton("Spiel verlassen");

        mainPanel.add(playAgainstPlayerButton);
        mainPanel.add(playAgainstComputerButton);
        mainPanel.add(endGameButton);

        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addRestartAgainstPlayerListener(ActionListener al) {
        playAgainstPlayerButton.addActionListener(al);
    }
}
