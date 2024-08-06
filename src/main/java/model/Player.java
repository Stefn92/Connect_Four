package model;

import java.awt.*;

public abstract class Player {

    protected String name;
    protected final int playerNumber;
    protected Color chipsColor;
    protected byte chipsRemaining;

    protected Player(String name, int playerNumber, Color chipsColor) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.chipsColor = chipsColor;
        this.chipsRemaining = 21;
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

    public Color getChipsColor() {
        return chipsColor;
    }

    public void setChipsColor(Color chipsColor) {
        this.chipsColor = chipsColor;
    }

    public FillStatus getFieldStatus() {
        if (playerNumber == 1) {
            return FillStatus.FILLED_BY_PLAYER1;
        } else if (playerNumber == 2) {
            return FillStatus.FILLED_BY_PLAYER2;
        }
        else {
            return null;
        }
    }

    public void decrementChipsRemainingByOne() {
        this.chipsRemaining--;
    }

    public byte getChipsRemaining() {
        return chipsRemaining;
    }

    public void setChipsRemaining(byte chipsRemaining) {
        this.chipsRemaining = chipsRemaining;
    }
}
