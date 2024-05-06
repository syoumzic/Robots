package gui;

import game.GameModel;
import utils.Localizable;
import utils.Savable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Окно показывающее текущие координаты робота
 */
public class RobotPositionWindow extends JInternalFrame implements PropertyChangeListener, Savable, Localizable {
    private final TextArea textArea;
    private String positionLabel, targetLabel, angleLabel;
    private GameModel gameModel;
    private final MessageFormat messageFormat;

    public RobotPositionWindow() {
        super("Позиция робота", true, true, true, true);

        textArea = new TextArea();
        messageFormat = new MessageFormat("{0}:\nx: {1}\ny: {2}\n{3}:\nx: {4}\ny: {5}\n{6}:\n{7}");

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
        Object[] args = new Object[]{
                positionLabel,
                gameModel.getRobotPositionX(),
                gameModel.getRobotPositionY(),
                targetLabel,
                gameModel.getTargetPositionX(),
                gameModel.getTargetPositionY(),
                angleLabel,
                gameModel.getRobotDirection()
        };

        textArea.setText(messageFormat.format(args));
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
        positionLabel = bundle.getString("positionLabel");
        targetLabel = bundle.getString("targetLabel");
        angleLabel = bundle.getString("angleLabel");
        updatePosition();
    }
}
