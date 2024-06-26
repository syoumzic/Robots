package game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * Представление робота
 */
public class GameVisualizer extends JPanel implements PropertyChangeListener {
    private GameModel model;

    public GameVisualizer() {
        setDoubleBuffered(true);
    }

    /**
     * Отрисовка окна
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(model.getRobotPositionX()), round(model.getRobotPositionY()), model.getRobotDirection());
        drawTarget(g2d, round(model.getTargetPositionX()), round(model.getTargetPositionY()));
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
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("modelChange".equals(evt.getPropertyName())) {
           model = (GameModel)evt.getSource();
           repaint();
        }
    }
}
