package view;

import model.Field;
import model.FieldStatus;
import model.HoverStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GridRenderer extends JPanel {

    private transient Field[][] gridArray;
    private Rectangle2D.Double rect;
    private final Color red;
    private final Color yellow;
    private transient Field lastHovered;

    public GridRenderer() {
        setBackground(Color.WHITE);
        red = Color.RED;
        yellow = Color.YELLOW;
        lastHovered = new Field();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichne das Rechteck
        drawBoard(g2d);

        // Zeichne die einzelnen Felder
        drawFields(g2d);

        // Färbt die Felder je nach Status ein
        colorFields(g2d);

        // Färbt die gewinnenden Felder ein
        //colorFieldBorderIfWinning(g2d);

        // Färbt Umrandung der Felder ein, wenn sie gehoveret werden
        colorFieldBorderIfHovered(g2d);
    }

    private void drawBoard(Graphics2D g2d) {
        Color rectColor = new Color(86, 86, 86);
        g2d.setColor(rectColor);
        g2d.fill(rect);
    }

    private void drawFields(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                g2d.fill(gridArray[i][j]);
            }
        }
    }

    private void colorFields(Graphics2D g2d) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                Field currentField = gridArray[i][j];
                FieldStatus currentStatus = gridArray[i][j].getStatus();

                if (currentStatus == FieldStatus.FILLED_BY_PLAYER1) {
                    g2d.setColor(red);
                    g2d.fill(currentField);
                }
                else if (currentStatus == FieldStatus.FILLED_BY_PLAYER2) {
                    g2d.setColor(yellow);
                    g2d.fill(currentField);
                }
            }
        }
    }

    private void colorFieldBorderIfWinning(Graphics2D g2d) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                Field currentField = gridArray[i][j];
                int x = (int) currentField.getX();
                int y = (int) currentField.getY();
                int width = (int) currentField.getWidth();
                int height = (int) currentField.getHeight();

                if (currentField.isWinning()) {
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(6));
                    g2d.drawOval(x, y, width, height);
                }
            }
        }
    }

    private void colorFieldBorderIfHovered(Graphics2D g2d) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                Field currentField = gridArray[i][j];

                if (currentField.getHover() == HoverStatus.HOVERED_BY_PLAYER1 || currentField.getHover() == HoverStatus.HOVERED_BY_PLAYER2) {
                    drawCircleOverHoveredField(currentField, g2d);
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    lastHovered = currentField;
                }
                if (lastHovered.getHover() == HoverStatus.NO_HOVER) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }
    }

    // Zeichnet eine Umrandung um das Feld ein, wenn die Maus darüber hovert
    private void drawCircleOverHoveredField(Field hoveredField, Graphics2D g2d) {

        int x = (int) hoveredField.getX();
        int y = (int) hoveredField.getY();
        int width = (int) hoveredField.getWidth();
        int height = (int) hoveredField.getHeight();

        if (hoveredField.getHover() == HoverStatus.HOVERED_BY_PLAYER1) {
            g2d.setColor(red);
        } else if (hoveredField.getHover() == HoverStatus.HOVERED_BY_PLAYER2) {
            g2d.setColor(yellow);

        }
        g2d.setStroke(new BasicStroke(4));
        g2d.drawOval(x, y, width, height);
    }

    public void setGrid(Field[][] grid) {
        this.gridArray = grid;
    }

    public void setRect(Rectangle2D.Double rect) {
        this.rect = rect;
    }
}
