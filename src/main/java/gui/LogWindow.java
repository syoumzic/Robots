package gui;

import data.Data;
import data.DataManager;
import data.Savable;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class LogWindow extends JInternalFrame implements LogChangeListener, Savable
{
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow()
    {
        super("Протокол работы", true, true, true, true);

        setLocation(10,10);
        setSize(300, 800);
        setMinimumSize(getSize());
        pack();
        Logger.debug("Протокол работает");

        m_logSource = Logger.getDefaultLogSource();
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    /**
     * Позволяет сохранять текущий объект
     */
    @Override
    public void setDefault() {
        setLocation(10,10);
        setSize(300, 800);
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
}
