package sk.stuba.fei.uim.oop.tile;


import java.awt.*;
import javax.swing.*;

public class StraightPipe extends JPanel {

    private int angle;
    private Tile tile;
    private Color color;

    public StraightPipe(int angle, Tile tile, Color color) {
        this.angle = angle;
        this.tile = tile;
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int w2 = this.tile.getWidth() / 2;
        int h2 = this.tile.getHeight() / 2;
        g2d.rotate(Math.toRadians(angle), w2, h2);
        g2d.setStroke(new BasicStroke(10));
        g2d.setColor(color);
        g2d.drawLine(0, ((int) (this.tile.getWidth() * 0.5)) - 1, 100, ((int) (this.tile.getWidth() * 0.5)) - 1);
    }
}
