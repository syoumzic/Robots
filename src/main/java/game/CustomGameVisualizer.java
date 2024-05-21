package game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

//TODO javadoc
//TODO убрать лишние классы
public class CustomGameVisualizer extends GameVisualizer{

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        GameModel model = getModel();
        drawRobot(g2d, round(model.getRobotPositionX()), round(model.getRobotPositionY()), model.getRobotDirection());
        drawTarget(g2d, round(model.getTargetPositionX()), round(model.getTargetPositionY()));
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
}
