package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * Движок для отображения робота
 */
public class GameVisualizer extends JPanel{
    private GameEngine engine;

    public GameVisualizer(GameEngine engine) {
        this.engine = engine;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                engine.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    public void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, engine.round(engine.getRobotPositionX()), engine.round(engine.getRobotPositionY()), engine.getRobotDirection());
        drawTarget(g2d, engine.getTargetPositionX(), engine.getTargetPositionY());
    }

    private void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x - width / 2, y - height / 2, width, height);
    }

    private void drawOval(Graphics g, int x, int y, int width, int height) {
        g.drawOval(x - width / 2, y - height / 2, width, height);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = engine.round(engine.getRobotPositionX());
        int robotCenterY = engine.round(engine.getRobotPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
