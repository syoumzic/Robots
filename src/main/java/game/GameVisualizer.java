package game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * Представление робота
 */
public abstract class GameVisualizer extends JPanel implements PropertyChangeListener {
    private GameModel model;

    public GameVisualizer() {
        setDoubleBuffered(true);
    }

    /**
     * Отрисовка окна
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
    }

    /**
     * Округление числа
     */
    protected final int round(double value) {
        return (int) (value + 0.5);
    }

    /**
     * Отрисовка овала
     */
    protected final void fillOval(Graphics g, int centerX, int centerY, int width, int height) {
        g.fillOval(centerX - width / 2, centerY - height / 2, width, height);
    }

    /**
     * Отрисовка контура овала
     */
    protected final void drawOval(Graphics g, int centerX, int centerY, int width, int height) {
        g.drawOval(centerX - width / 2, centerY - height / 2, width, height);
    }

    @Override
    public final void propertyChange(PropertyChangeEvent evt) {
        if("modelChange".equals(evt.getPropertyName())) {
            model = (GameModel)evt.getSource();
            repaint();
        }
    }

    protected final GameModel getModel(){
        return model;
    }
}
