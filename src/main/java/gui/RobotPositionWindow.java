package gui;

import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Окно показывающее текущие координаты робота
 */
public class RobotPositionWindow extends JInternalFrame implements PropertyChangeListener, Savable {
    private TextArea textArea;
    private WindowsManager windowsManager;
    public RobotPositionWindow(WindowsManager windowsManager) {
        super("Позиция робота", true, true, true, true);
        this.windowsManager = windowsManager;

        textArea = new TextArea();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);

        getContentPane().add(textArea);
        pack();

        try{
            loadState();
        }catch (NoSuchElementException e){
            setLocation(790, 10);
            setSize(200, 200);
        }
    }

    /**
     * Метод для обновления координат на окне
     */
    private void updateContent(double x, double y){
        textArea.setText("Координаты робота:\nx: %f\ny: %f".formatted(x, y));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        double[] position = (double[])evt.getNewValue();
        updateContent(position[0], position[1]);
    }

    @Override
    public void saveState() {
        windowsManager.setWindow("robotPositionWindow", this);
    }

    @Override
    public void loadState() throws NoSuchElementException {
        windowsManager.loadWindow("robotPositionWindow", this);
    }
}
