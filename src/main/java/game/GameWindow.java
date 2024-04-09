package game;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    public GameWindow(PropertyChangeListener robotPositionWindow){
        super("Игровое поле", true, true, true, true);
        GameEngine engine = new GameEngine(robotPositionWindow);
        GameVisualizer visualizer = new GameVisualizer(engine);
        GameController controller = new GameController(visualizer, engine);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
