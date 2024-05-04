package gui;

import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class GameWindow extends JInternalFrame implements Savable
{
    private final GameVisualizer m_visualizer;

    public GameWindow(){
        super("Игровое поле", true, true, true, true);

        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        setLocation(350, 20);
        setSize(400, 400);
    }

    @Override
    public String getWindowName() {
        return "gameWindow";
    }
}
