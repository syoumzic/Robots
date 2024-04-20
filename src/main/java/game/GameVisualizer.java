package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

/**
 * Представление робота
 */
public class GameVisualizer extends JPanel{
    private GameModel model;

    public GameVisualizer(GameModel model) {
        this.model = model;
        setDoubleBuffered(true);
    }

    /**
     * Действие для отрисовки окна
     */
    public void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    /**
     * Отрисовка окна
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(model.getRobotPositionX()), round(model.getRobotPositionY()), model.getRobotDirection());
        drawTarget(g2d, model.getTargetPositionX(), model.getTargetPositionY());
    }

    /**
     * Округление числа
     */
    private int round(double value) {
        return (int) (value + 0.5);
    }

    /**
     * Отрисовка овала
     */
    private void fillOval(Graphics g, int centerX, int centerY, int width, int height) {
        g.fillOval(centerX - width / 2, centerY - height / 2, width, height);
    }

    /**
     * Отрисовка контура овала
     */
    private void drawOval(Graphics g, int centerX, int centerY, int width, int height) {
        g.drawOval(centerX - width / 2, centerY - height / 2, width, height);
    }

    /**
     * Отрисовка робота
     */
    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(model.getRobotPositionX());
        int robotCenterY = round(model.getRobotPositionY());
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

    /**
     * Отрисовка цели
     */
    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
