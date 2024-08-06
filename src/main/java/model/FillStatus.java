package model;

public enum FillStatus {
    UNFILLED_UNFILLABLE, // Feld ist nicht gefüllt und kann gerade auch nicht gefüllt werden
    UNFILLED_FILLABLE, // Feld ist nicht gefüllt und kann gerade gefüllt werden
    FILLED_BY_PLAYER1, // Feld ist durch den Spieler 1 gefüllt
    FILLED_BY_PLAYER2 // Feld ist durch den Spieler 2 gefüllt
}
