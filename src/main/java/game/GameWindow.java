package game;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    public GameWindow(PropertyChangeListener robotPositionWindow){
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer(robotPositionWindow);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
