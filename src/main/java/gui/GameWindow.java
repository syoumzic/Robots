package gui;

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

        try{
            loadState();
        }catch(NoSuchElementException e){
            setLocation(350, 20);
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
