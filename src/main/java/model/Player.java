package model;

import java.awt.*;

public abstract class Player {

    protected String name;
    protected int playerNumber;
    protected Color fieldColor;

    protected Player(String name, int playerNumber, Color fieldColor) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.fieldColor = fieldColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Color getFieldColor() {
        return fieldColor;
    }

    public void setFieldColor(Color fieldColor) {
        this.fieldColor = fieldColor;
    }

    public FieldStatus getFieldStatus() {
        if (playerNumber == 1) {
            return FieldStatus.FILLED_BY_PLAYER1;
        } else if (playerNumber == 2) {
            return FieldStatus.FILLED_BY_PLAYER2;
        }
        else {
            return null;
        }
    }
}
