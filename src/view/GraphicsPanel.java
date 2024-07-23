package view;

import model.Field;
import model.FieldStatus;
import model.Grid;

import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JPanel {

    private transient Grid grid;

    public GraphicsPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichne das Rechteck
        Color rectColor = new Color(86, 86, 86);
        g2d.setColor(rectColor);
        g2d.fill(grid.getField());

        // Zeichnet die leeren Kreise
        g2d.setColor(Color.WHITE);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                g2d.fill(grid.getGridArray()[i][j]);
            }
        }

        // Färbt die Kreise je nach Status ein
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                Field currentField = grid.getGridArray()[i][j];
                FieldStatus currentStatus = grid.getGridArray()[i][j].getStatus();

                if (currentStatus == FieldStatus.FILLED_BY_PLAYER1) {
                    g2d.setColor(Color.RED);
                    g2d.fill(currentField);
                }
                else if (currentStatus == FieldStatus.FILLED_BY_PLAYER2) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fill(currentField);
                }
                if (currentField.getHovered()) {
                    drawHoveredCircle(currentField, g2d);
                }
            }
        }
    }

    private void drawHoveredCircle(Field field, Graphics2D g2d) {
        int x = (int) field.getX();
        int y = (int) field.getY();
        int width = (int) field.getWidth();
        int height = (int) field.getHeight();
        g2d.drawOval(x, y, width, height);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
