package model;

import java.awt.geom.Rectangle2D;

public class GameBoard {

    private Rectangle2D.Double board;

    public GameBoard() {
        board = new Rectangle2D.Double();
    }

    public void updateBoardCoordinates(int panelWidth, int panelHeight) {

        int xPos = (panelWidth * 12) / 100;
        int yPos = (panelHeight * 7) / 100;
        int fieldWidth = panelWidth - (xPos * 2);
        int fieldHeight = panelHeight - (yPos * 2);
        board.setRect(xPos, yPos, fieldWidth, fieldHeight);
        System.out.println("Rect: Heigth = " + board.getHeight() + "Width = " + board.getWidth());
    }

    public Rectangle2D.Double getBoard() {
        return board;
    }

    public void setBoard(Rectangle2D.Double board) {
        this.board = board;
    }
}
