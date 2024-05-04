package gui;

import game.GameController;
import game.GameModel;
import game.GameVisualizer;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.util.NoSuchElementException;

public class GameWindow extends JInternalFrame implements Savable
{

    public GameWindow(PropertyChangeListener positionListener)
    {
        super("Игровое поле", true, true, true, true);

        GameVisualizer visualizer = new GameVisualizer();

        GameModel model = new GameModel();
        model.addPropertyChangeListener(positionListener);
        model.addPropertyChangeListener(visualizer);

        GameController controller = new GameController(this, model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        setLocation(350, 20);
        setSize(400, 400);
    }

    @Override
    public String getWindowName() {
        return "gameWindow";
    }
}
