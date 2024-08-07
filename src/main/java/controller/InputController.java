package controller;

import view.GridFrame;
import view.GridRenderer;

import javax.swing.*;
import java.awt.event.*;

public class InputController {

    private final GridRenderer gridRenderer;
    private final GridFrame gridFrame;
    private final GameLogic gameLogic;

    public InputController(GameLogic gameLogic, GridRenderer gridRenderer, GridFrame gridFrame) {
        this.gridRenderer = gridRenderer;
        this.gameLogic = gameLogic;
        this.gridFrame = gridFrame;
        setupMouseListener();
        setupResizeListener();
        setupMouseHoverListener();
        setupRestartGameListener();
    }

    public void setupMouseListener() {
        this.gridRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    gameLogic.handleMouseClick(e);
                    gameLogic.updateView();
                }
            }
        });
    }

    public void setupResizeListener() {
        this.gridRenderer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameLogic.updateBoardCoordinates();
                gameLogic.updateView();

            }
        });
    }

    public void setupMouseHoverListener() {
        this.gridRenderer.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                gameLogic.handleMouseHover(e);
                gameLogic.updateView();
            }
        });
    }

    public void setupRestartGameListener() {
        this.gridFrame.getRestartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.restartGame();
                gameLogic.updateView();
            }
        });
    }
}
