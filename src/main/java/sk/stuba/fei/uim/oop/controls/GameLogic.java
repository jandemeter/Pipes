package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeEvent;


public class GameLogic extends UniversalAdapter {
    private static final String RESTART = "RESTART";
    private static final String CHECK = "CHECK";

    private JFrame mainGame;
    private Board currentBoard;
    private int currentBoardSize;
    @Setter
    @Getter
    int level;
    @Getter
    private JLabel levelLabel;
    @Getter
    private JLabel boardSizeLabel;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.currentBoardSize = 8;
        this.level = 1;
        initializeNewBoard(currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.levelLabel = new JLabel();
        this.updateLevelLabel();
        this.boardSizeLabel = new JLabel();
        this.updateBoardSizeLabel();
    }

    public void updateLevelLabel() {
        this.levelLabel.setText("LEVEL: " + this.level);
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }


    private void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("CURRENT BOARD SIZE: " + this.currentBoardSize);
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void initializeNewBoard(int dimension) {
        this.currentBoard = new Board(dimension, this);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
    }


    public void gameRestart() {
        this.mainGame.remove(this.currentBoard);
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.mainGame.revalidate();
        this.mainGame.repaint();
        this.mainGame.setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case RESTART:
                this.level = 1;
                this.gameRestart();
                updateLevelLabel();
                break;
            case CHECK:
                currentBoard.checkPath();
                this.mainGame.revalidate();
                this.mainGame.repaint();
                break;
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        if (currentBoardSize != ((JSlider) e.getSource()).getValue()) {
            this.currentBoardSize = ((JSlider) e.getSource()).getValue();
            this.level = 1;
            this.gameRestart();
            updateBoardSizeLabel();
            updateLevelLabel();
            this.mainGame.revalidate();
            this.mainGame.repaint();
            this.mainGame.setFocusable(true);
            this.mainGame.requestFocus();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.level = 1;
                this.gameRestart();
                updateLevelLabel();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_ENTER:
                currentBoard.checkPath();
                this.mainGame.revalidate();
                this.mainGame.repaint();
                break;
        }
    }
}
