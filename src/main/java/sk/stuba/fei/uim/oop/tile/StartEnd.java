package sk.stuba.fei.uim.oop.tile;


import javax.swing.*;
import java.awt.*;

public class StartEnd extends JPanel {
    private int angle;
    private Tile tile;
    private Color colorOfPipe;

    public StartEnd(int angle, Tile tile, Color color, Color colorOfPipe) {
        this.angle = angle;
        this.tile = tile;
        this.tile.setBackground(color);
        this.colorOfPipe = colorOfPipe;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int w2 = this.tile.getWidth() / 2;
        int h2 = this.tile.getHeight() / 2;
        g2d.rotate(Math.toRadians(angle), w2, h2);
        g2d.setStroke(new BasicStroke(10));
        g2d.setColor(this.colorOfPipe);
        g2d.drawLine(w2, h2, this.tile.getWidth(), h2);
    }
}
