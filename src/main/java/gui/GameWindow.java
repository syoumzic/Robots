package gui;

import data.Data;
import data.DataManager;
import data.Savable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class GameWindow extends JInternalFrame implements Savable
{
    private final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super("Игровое поле", true, true, true, true);

        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    /**
     * Устанавлеивает объект в значение по умолчанию
     */
    @Override
    public void setDefault() {
        setLocation(350, 10);
        setSize(400,  400);
    }

    /**
     * Возвращает себя как объект данных
     */
    @Override
    public Data getData() {
        Data data = new DataManager();

        Point position = getLocation();
        data.setInt("x", position.x);
        data.setInt("y", position.y);

        Dimension size = getSize();
        data.setInt("width", size.width);
        data.setInt("height", size.height);

        boolean isIcon = isIcon();
        data.setBoolean("isIcon", isIcon);

        return data;
    }

    /**
     * Позволяет загружать текущий объект
     */
    @Override
    public void loadState(Data data) {
        setLocation(data.getInt("x"), data.getInt("y"));
        setSize(data.getInt("width"), data.getInt("height"));

        try{
            setIcon(data.getBoolean("isIcon"));
        }catch(PropertyVetoException e){
            //ignore
        }
    }
}
