package view;

import javax.swing.*;
import java.awt.*;

public class GridFrame extends JFrame {

    GridRenderer gPanel;
    JPanel northPanel;
    JPanel mainPanel;
    JButton testButton;

    public GridFrame(GridRenderer gPanel) {

        this.gPanel = gPanel;

        mainPanel = new JPanel(new BorderLayout());

        northPanel = new JPanel();
        northPanel.setBackground(Color.LIGHT_GRAY);

        testButton = new JButton("Test123");

        northPanel.add(testButton);

        mainPanel.add(BorderLayout.NORTH, northPanel);
        mainPanel.add(BorderLayout.CENTER, gPanel);

        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
