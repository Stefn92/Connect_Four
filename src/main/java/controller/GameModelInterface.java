package controller;

import model.Board;
import model.GameStateMachine;
import model.Grid;

public interface GameModelInterface {

    Grid getGrid();
    Board getBoard();
    GameStateMachine getStateMachine();
}
