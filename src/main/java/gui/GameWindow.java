package gui;

import utils.Data;
import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class GameWindow extends JInternalFrame implements Savable
{
    private final GameVisualizer m_visualizer;
    private WindowsManager windowsManager;

    public GameWindow(WindowsManager windowsManager){
        super("Игровое поле", true, true, true, true);
        this.windowsManager = windowsManager;

        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        setLocation(350, 10);
        setSize(400,  400);
    }

    @Override
    public void saveState(Data windowsState) {
        Data data = windowsManager.toData(this);
        windowsState.setData("gameWindow", data);
    }

    @Override
    public void loadState(Data windowsState) throws NoSuchElementException {
        Data data = windowsState.getData("gameWindow");
        windowsManager.loadComponent(this, data);
    }
}
