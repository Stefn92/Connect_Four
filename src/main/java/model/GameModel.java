package model;

import controller.GameModelInterface;

public class GameModel implements GameModelInterface {

    private final Grid grid;
    private final Board board;
    private final GameStateMachine stateMachine;

    public GameModel() {
        this.grid = new Grid();
        this.board = new Board();
        this.stateMachine = new GameStateMachine();
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public GameStateMachine getStateMachine() {
        return stateMachine;
    }
}
