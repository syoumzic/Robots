package gui;

import game.GameController;
import game.GameModel;
import game.GameVisualizer;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    public GameWindow(PropertyChangeListener listener){
        super("Игровое поле", true, true, true, true);
        GameModel model = new GameModel(listener);
        GameVisualizer visualizer = new GameVisualizer(model);
        GameController controller = new GameController(this, visualizer, model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
