package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Окно показывающее текущие координаты робота
 */
public class RobotPositionWindow extends JInternalFrame implements PropertyChangeListener {
    private TextArea textArea;
    public RobotPositionWindow() {
        super("Позиция робота", true, true, true, true);
        textArea = new TextArea();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        textArea.setSize(200, 200);
        textArea.setEditable(false);

        getContentPane().add(textArea);
        pack();
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
}
