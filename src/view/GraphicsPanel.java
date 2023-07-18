package view;

import model.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.geom.Ellipse2D;

public class GraphicsPanel extends JPanel {

    private Grid grid;

    public GraphicsPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichne das Rechteck
        g2d.setColor(new Color(86, 86, 86));
        g2d.fill(grid.getRect());

        // Zeichne die Kreise
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                g2d.fill(grid.getGridArray()[i][j]);
            }
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void addComponentListener(ComponentAdapter ca) {
        addComponentListener(ca);
    }
}
