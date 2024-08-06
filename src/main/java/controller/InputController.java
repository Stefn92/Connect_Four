package controller;

import view.GridRenderer;

import javax.swing.*;
import java.awt.event.*;

public class InputController {

    private final GridRenderer gridRenderer;
    private final GameLogic gameLogic;

    public InputController(GameLogic gameLogic, GridRenderer gridRenderer) {
        this.gridRenderer = gridRenderer;
        this.gameLogic = gameLogic;
        setupMouseListener();
        setupResizeListener();
        setupMouseHoverListener();
    }

    public void setupMouseListener() {
        this.gridRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    gameLogic.handleMouseClick(e);
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
            }
        });
    }
}
