package gui;

import game.GameController;
import game.GameModel;
import game.GameVisualizer;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class GameWindow extends JInternalFrame implements Savable
{
    private WindowsManager windowsManager;

    public GameWindow(PropertyChangeListener listener, WindowsManager windowsManager)
    {
        super("Игровое поле", true, true, true, true);
        this.windowsManager = windowsManager;

        GameModel model = new GameModel(listener);
        GameVisualizer visualizer = new GameVisualizer(model);
        GameController controller = new GameController(this, visualizer, model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        try{
            loadState();
        }catch(NoSuchElementException e){
            setLocation(350, 10);
            setSize(400, 400);
        }
    }

    @Override
    public void saveState() {
        windowsManager.setWindow("gameWindow", this);
    }

    @Override
    public void loadState() throws NoSuchElementException {
        windowsManager.loadWindow("gameWindow", this);
    }
}
