package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import sk.stuba.fei.uim.oop.controls.GameLogic;
import sk.stuba.fei.uim.oop.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board extends JPanel {
    private GameLogic logic;
    private Tile[][] board;
    private ArrayList<Tile> path;
    private ArrayList<Tile> finalPath;
    private int startYCor;
    private int endYCor;
    private int dimension;

    public Board(int dimension, GameLogic logic) {
        this.logic = logic;
        path = new ArrayList<Tile>();
        finalPath = new ArrayList<Tile>();
        this.dimension = dimension;
        this.initializeBoard(dimension);
        this.initializeNeighbors(dimension);
        this.createStartAndEnd();
        this.createPath();
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.YELLOW);
    }

    private void createStartAndEnd() {
        Random random = new Random();
        this.startYCor = random.nextInt(dimension);
        this.endYCor = random.nextInt(dimension);

    }

    private void createPath() {
        Tile startTile = board[0][startYCor];
        findPath(startTile);
        for (Tile tile : finalPath) {
            tile.initializeStartDirection();
        }
    }

    private void findPath(Tile tile) {
        tile.setVisited(true);
        if (tile == board[dimension - 1][endYCor] && finalPath.size() == 0) {
            this.finalPath.addAll(path);
            finalPath.add(board[dimension - 1][endYCor]);
        }
        this.path.add(tile);
        Tile nextTile;
        if (tile.checkUnivisitedNeighbors() != null) {
            nextTile = tile.checkUnivisitedNeighbors().get(0);
        } else {
            nextTile = null;
            path.remove(path.size() - 1);
        }
        while (nextTile != null) {
            findPath(nextTile);
            if (tile.checkUnivisitedNeighbors() != null) {
                nextTile = tile.checkUnivisitedNeighbors().get(0);
            } else {
                nextTile = null;
                path.remove(path.size() - 1);
            }
        }
    }

    public void checkPath() {
        Tile currentTile = board[0][startYCor];
        currentTile.setHighlightPipe(true);
        while (true) {
            DirectionOfPipe directionOfStart = currentTile.getDirectionOfStart();
            DirectionOfPipe directionOfEnd = currentTile.getDirectionOfEnd();

            if (currentTile == board[dimension - 1][endYCor]) {
                this.logic.setLevel(this.logic.getLevel() + 1);
                this.logic.updateLevelLabel();
                this.logic.gameRestart();
            }
            int x = currentTile.getXCor();
            int y = currentTile.getYCor();

            if (x != dimension - 1 && (directionOfEnd.equals(DirectionOfPipe.RIGHT) || directionOfStart.equals(DirectionOfPipe.RIGHT)) && (board[x + 1][y].getDirectionOfStart() != null || board[x + 1][y].getDirectionOfEnd() != null) && !board[x + 1][y].isHighlightPipe()) {
                if (board[x + 1][y].getDirectionOfStart().equals(DirectionOfPipe.LEFT) || board[x + 1][y].getDirectionOfEnd().equals(DirectionOfPipe.LEFT)) {
                    board[x + 1][y].setHighlightPipe(true);
                    currentTile = board[x + 1][y];
                } else {
                    return;
                }
            } else if (x != 0 && (directionOfEnd.equals(DirectionOfPipe.LEFT) || directionOfStart.equals(DirectionOfPipe.LEFT)) && (board[x - 1][y].getDirectionOfStart() != null || board[x - 1][y].getDirectionOfEnd() != null) && !board[x - 1][y].isHighlightPipe()) {
                if (board[x - 1][y].getDirectionOfStart().equals(DirectionOfPipe.RIGHT) || board[x - 1][y].getDirectionOfEnd().equals(DirectionOfPipe.RIGHT)) {
                    board[x - 1][y].setHighlightPipe(true);
                    currentTile = board[x - 1][y];
                } else {
                    return;
                }
            } else if (y != 0 && (directionOfEnd.equals(DirectionOfPipe.TOP) || directionOfStart.equals(DirectionOfPipe.TOP)) && (board[x][y - 1].getDirectionOfStart() != null || board[x][y - 1].getDirectionOfEnd() != null) && !board[x][y - 1].isHighlightPipe()) {
                if (board[x][y - 1].getDirectionOfStart().equals(DirectionOfPipe.BOTTOM) || board[x][y - 1].getDirectionOfEnd().equals(DirectionOfPipe.BOTTOM)) {
                    board[x][y - 1].setHighlightPipe(true);
                    currentTile = board[x][y - 1];
                } else {
                    return;
                }
            } else if (y != dimension - 1 && (directionOfEnd.equals(DirectionOfPipe.BOTTOM) || directionOfStart.equals(DirectionOfPipe.BOTTOM)) && (board[x][y + 1].getDirectionOfStart() != null || board[x][y + 1].getDirectionOfEnd() != null) && !board[x][y + 1].isHighlightPipe()) {
                if (board[x][y + 1].getDirectionOfStart().equals(DirectionOfPipe.TOP) || board[x][y + 1].getDirectionOfEnd().equals(DirectionOfPipe.TOP)) {
                    board[x][y + 1].setHighlightPipe(true);
                    currentTile = board[x][y + 1];
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void initializeBoard(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[j][i] = new Tile(j, i, finalPath, this);
                this.add(this.board[j][i]);
            }
        }
    }


    private void initializeNeighbors(int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i > 0) {
                    Tile leftNeighbor = board[i - 1][j];
                    this.board[i][j].getNeighbors().add(leftNeighbor);
                }

                if (i < dimension - 1) {
                    Tile rightNeighbor = board[i + 1][j];
                    this.board[i][j].getNeighbors().add(rightNeighbor);
                }

                if (j > 0) {
                    Tile topNeighbor = board[i][j - 1];
                    this.board[i][j].getNeighbors().add(topNeighbor);
                }

                if (j < dimension - 1) {
                    Tile bottomNeighbor = board[i][j + 1];
                    this.board[i][j].getNeighbors().add(bottomNeighbor);
                }
                Collections.shuffle(this.board[i][j].getNeighbors());
            }
        }
    }
}
