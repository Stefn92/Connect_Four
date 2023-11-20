package model;

import java.awt.*;

public class Player {

    String name;
    Color chipColor;
    boolean myTurn;

    public Player(String name, Color chipColor, boolean myTurn) {
        this.name = name;
        this.chipColor = chipColor;
        this.myTurn = myTurn;
    }

    public Color getChipColor() {
        return chipColor;
    }

    public void setChipColor(Color chipColor) {
        this.chipColor = chipColor;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}
