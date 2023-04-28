package sk.stuba.fei.uim.oop.tile;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.DirectionOfPipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Tile extends JPanel implements MouseListener {

    @Getter
    final private int xCor;
    @Getter
    final private int yCor;
    @Getter
    @Setter
    private boolean visited;
    @Getter
    @Setter
    private ArrayList<Tile> neighbors;
    @Setter
    @Getter
    private boolean highlightPipe;
    private Board board;
    private boolean rotate;
    private int angle;
    private ArrayList<Tile> finalPath;
    private DirectionOfPipe startStraight;
    private DirectionOfPipe endStraight;
    private DirectionOfPipe startBent;
    private DirectionOfPipe endBent;
    private DirectionOfPipe endStartEnd;
    @Getter
    private DirectionOfPipe directionOfStart;
    @Getter
    private DirectionOfPipe directionOfEnd;
    private int position;
    private Color color;


    public Tile(int x, int y, ArrayList<Tile> finalPath, Board board) {
        this.xCor = x;
        this.yCor = y;
        this.angle = 0;
        this.finalPath = finalPath;
        this.visited = false;
        this.highlightPipe = false;
        this.neighbors = new ArrayList<Tile>();
        this.setBackground(Color.ORANGE);
        this.addMouseListener(this);
        this.board = board;
        startStraight = DirectionOfPipe.LEFT;
        endStraight = DirectionOfPipe.RIGHT;
        startBent = DirectionOfPipe.LEFT;
        endBent = DirectionOfPipe.BOTTOM;
        endStartEnd = DirectionOfPipe.RIGHT;
    }

    public void initializeStartDirection() {
        int randomNumber = (int) (Math.random() * 4);
        this.position = this.finalPath.indexOf(this);
        if ((position != 0 && position != finalPath.size() - 1) && finalPath.contains(this)) {
            if (this.finalPath.get(position - 1).getXCor() == this.finalPath.get(position + 1).getXCor() || this.finalPath.get(position - 1).getYCor() == this.finalPath.get(position + 1).getYCor()) {
                this.angle = (360 / 4) * (randomNumber + 1);
                for (int i = 0; i < this.angle / 90; i++) {
                    startStraight =startStraight.rotate();
                    endStraight = endStraight.rotate();
                }
            } else {
                this.angle = (360 / 4) * (randomNumber + 1);
                for (int i = 0; i < this.angle / 90; i++) {
                    startBent = startBent.rotate();
                    endBent = endBent.rotate();
                }
            }
        }
        else if (this.position == 0 || this.position == this.finalPath.size() -1){
            this.angle = (360 / 4) * (randomNumber + 1);
            for (int i = 0; i < this.angle / 90; i++) {
                endStartEnd = endStartEnd.rotate();
            }
        }
    }

    public ArrayList<Tile> checkUnivisitedNeighbors() {
        neighbors.removeIf(node1 -> node1.visited);
        if (neighbors.size() == 0) {
            return null;
        } else {
            return neighbors;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.rotate) {
            angle += 90;
        }
        this.position = this.finalPath.indexOf(this);
        if ((position != 0 && position != finalPath.size() - 1) && finalPath.contains(this)) {
            if (this.finalPath.get(position - 1).getXCor() == this.finalPath.get(position + 1).getXCor() || this.finalPath.get(position - 1).getYCor() == this.finalPath.get(position + 1).getYCor()) {
                if (this.rotate) {
                    startStraight = startStraight.rotate();
                    endStraight = endStraight.rotate();
                    this.directionOfStart = startStraight;
                    this.directionOfEnd = endStraight;
                    this.rotate = false;
                } else {
                    this.directionOfStart = startStraight;
                    this.directionOfEnd = endStraight;
                }
                if (this.highlightPipe) {
                    color = Color.BLUE;
                    this.highlightPipe = false;
                } else {
                    color = Color.BLACK;
                    repaint();
                }
                StraightPipe pipe = new StraightPipe(angle, this, color);
                pipe.paintComponent(g);
            } else {
                if (this.rotate) {
                    startBent = startBent.rotate();
                    endBent = endBent.rotate();
                    this.directionOfStart = startBent;
                    this.directionOfEnd = endBent;
                    this.rotate = false;
                } else {
                    this.directionOfStart = startBent;
                    this.directionOfEnd = endBent;
                }
                if (this.highlightPipe) {
                    color = Color.BLUE;
                    this.highlightPipe = false;
                } else {
                    color = Color.BLACK;
                }
                BentPipe pipe = new BentPipe(angle, this, color);
                pipe.paintComponent(g);
            }
        } else if (position == 0) {
            if (this.rotate) {
                endStartEnd = endStartEnd.rotate();
                this.directionOfEnd = endStartEnd;
                this.directionOfStart = endStartEnd;
                this.rotate = false;
            } else {
                this.directionOfEnd = endStartEnd;
                this.directionOfStart = endStartEnd;
            }
            StartEnd startPipe = new StartEnd(angle, this, Color.GREEN, Color.BLUE);
            startPipe.paintComponent(g);

        } else if (position == (finalPath.size() - 1)) {
            if (this.rotate) {
                endStartEnd = endStartEnd.rotate();
                this.directionOfEnd = endStartEnd;
                this.directionOfStart = endStartEnd;
                this.rotate = false;
            } else {
                this.directionOfEnd = endStartEnd;
                this.directionOfStart = endStartEnd;
            }
            StartEnd endPipe = new StartEnd(angle, this, Color.RED, Color.black);
            endPipe.paintComponent(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.rotate = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(Color.RED);
        this.board.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(Color.ORANGE);
    }

}
