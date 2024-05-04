package gui;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import log.Logger;
import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class LogWindow extends JInternalFrame implements LogChangeListener, Savable {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow() {
        super("Протокол работы", true, true, true, true);

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
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public String getWindowName() {
        return "logWindow";
    }
}
