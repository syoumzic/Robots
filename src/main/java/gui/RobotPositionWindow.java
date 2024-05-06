package gui;

import game.GameModel;
import utils.Localizable;
import utils.Savable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

/**
 * Окно показывающее текущие координаты робота
 */
public class RobotPositionWindow extends JInternalFrame implements PropertyChangeListener, Savable, Localizable {
    private TextArea textArea;
    private String positionLabel;
    private GameModel gameModel;

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
    private void updatePosition() {
        textArea.setText("%s:\nx: %f\ny: %f".formatted(positionLabel, gameModel.getRobotPositionX(), gameModel.getRobotPositionY()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("modelChange".equals(evt.getPropertyName())) {
            gameModel = (GameModel) evt.getSource();
            updatePosition();
        }
    }

    @Override
    public String getWindowName() {
        return "robotPositionWindow";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setTitle(bundle.getString("title"));
        this.positionLabel = bundle.getString("positionLabel");
        updatePosition();
    }
}
