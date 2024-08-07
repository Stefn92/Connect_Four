package model;

public class GameStateMachine {

    private GameState currentGameState;

    public GameStateMachine() {
        this.currentGameState = GameState.GAME_START;
    }

    public void changeState(GameState newState) {
        System.out.println("changing game state from " + currentGameState + " to " + newState);
        currentGameState = newState;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void handleEvent(GameEvent event) {
        switch (currentGameState) {
            case GAME_START -> {
                if (event == GameEvent.START_GAME) {
                    changeState(GameState.PLAYER1_TURN);
                }
            }
            case PLAYER1_TURN -> {
                if (event == GameEvent.PLAYER1_MOVED) {
                    changeState(GameState.PLAYER2_TURN);
                }
                else if (event == GameEvent.RESTART_GAME) {
                    changeState(GameState.GAME_START);
                }
                else if (event == GameEvent.GAME_ENDED) {
                    changeState(GameState.GAME_OVER);
                }
            }
            case PLAYER2_TURN -> {
                if (event == GameEvent.PLAYER2_MOVED) {
                    changeState(GameState.PLAYER1_TURN);
                }
                else if (event == GameEvent.RESTART_GAME) {
                    changeState(GameState.GAME_START);
                }
                else if (event == GameEvent.GAME_ENDED) {
                    changeState(GameState.GAME_OVER);
                }
            }
            case GAME_OVER -> {
                if (event == GameEvent.RESTART_GAME) {
                    changeState(GameState.GAME_START);
                }
            }
        }
    }
}
