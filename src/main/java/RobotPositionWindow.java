import log.LogEntry;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RobotPositionWindow extends JInternalFrame{
    private TextArea textArea;
    RobotPositionWindow() {
        super("Позиция робота", true, true, true, true);
        textArea = new TextArea();
        updateContent(0, 0);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        textArea.setSize(200, 200);
        textArea.setEditable(false);

        getContentPane().add(textArea);
        pack();
    }

    void updateContent(double x, double y){
        textArea.setText("Координаты робота:\nx: %f\ny: %f".formatted(x, y));
        textArea.invalidate();
    }
}
