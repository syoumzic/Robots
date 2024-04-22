package log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

public class LogWindowSource
{
    private int m_iQueueLength;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ArrayList<LogEntry> messages;

    
    public LogWindowSource(int iQueueLength){
        m_iQueueLength = iQueueLength;
        messages = new ArrayList<LogEntry>(iQueueLength);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener){
        synchronized(support){
            support.addPropertyChangeListener(listener);
        }
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
        synchronized(support){
            support.removePropertyChangeListener(listener);
        }
    }
    
    public void append(LogLevel logLevel, String strMessage){
        LogEntry entry = new LogEntry(logLevel, strMessage);
        messages.add(entry);
        support.firePropertyChange("logChange", null, null);
    }
    
    public int size(){
        return messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count){
        if (startFrom < 0 || startFrom >= messages.size()){
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, messages.size());
        return messages.subList(startFrom, indexTo);
    }

    public Iterable<LogEntry> all(){
        return messages;
    }
}
