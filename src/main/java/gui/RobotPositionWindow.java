package gui;

import game.GameModel;
import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.NoSuchElementException;

/**
 * Окно показывающее текущие координаты робота
 */
public class RobotPositionWindow extends JInternalFrame implements PropertyChangeListener, Savable {
    private TextArea textArea;

    public RobotPositionWindow() {
        super("Позиция робота", true, true, true, true);

        textArea = new TextArea();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);

        getContentPane().add(textArea);
        pack();

        setLocation(790, 10);
        setSize(200, 200);
    }

    /**
     * Метод для обновления координат на окне
     */
    private void updatePosition(double x, double y){
        textArea.setText("Координаты робота:\nx: %f\ny: %f".formatted(x, y));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("modelChange".equals(evt.getPropertyName())) {
            GameModel model = (GameModel)evt.getSource();
            updatePosition(model.getRobotPositionX(), model.getRobotPositionY());
        }
    }

    @Override
    public String getWindowName() {
        return "robotPositionWindow";
    }
}
