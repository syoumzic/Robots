package gui;

import log.LogEntry;
import log.LogWindowSource;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import log.Logger;
import utils.Savable;
import utils.WindowsManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.NoSuchElementException;

public class LogWindow extends JInternalFrame implements PropertyChangeListener, Savable {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow() {
        super("Протокол работы", true, true, true, true);

        pack();
        Logger.debug("Протокол работает");

        m_logSource = Logger.getDefaultLogSource();
        m_logSource.addPropertyChangeListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

        setLocation(10, 10);
        setSize(300, 800);
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public String getWindowName() {
        return "logWindow";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
