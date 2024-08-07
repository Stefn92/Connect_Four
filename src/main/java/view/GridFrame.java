package view;

import javax.swing.*;
import java.awt.*;

public class GridFrame extends JFrame {

    GridRenderer gPanel;
    JPanel northPanel;
    JPanel mainPanel;
    JButton restartButton;

    public GridFrame(GridRenderer gPanel) {

        this.gPanel = gPanel;

        mainPanel = new JPanel(new BorderLayout());

        northPanel = new JPanel();
        northPanel.setBackground(Color.LIGHT_GRAY);

        restartButton = new JButton("Spiel neustarten");

        northPanel.add(restartButton);

        mainPanel.add(BorderLayout.NORTH, northPanel);
        mainPanel.add(BorderLayout.CENTER, gPanel);

        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getRestartButton() {
        return restartButton;
    }
}
