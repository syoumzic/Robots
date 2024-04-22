package log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Структура данных для хранения логов
 */
public class LogWindowSource{
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final CircleQueue<LogEntry> messages;
    
    public LogWindowSource(int iQueueLength){
        messages = new CircleQueue<>(iQueueLength);
    }

    /**
     * Добавляет слушателя
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        synchronized(support){
            support.addPropertyChangeListener(listener);
        }
    }

    /**
     * Удаляет слушателя
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        synchronized(support){
            support.removePropertyChangeListener(listener);
        }
    }

    /**
     * Добавляет сообщение в лог
     */
    public void append(LogLevel logLevel, String strMessage){
        synchronized (messages) {
            LogEntry entry = new LogEntry(logLevel, strMessage);
            messages.append(entry);
            support.firePropertyChange("logChange", null, null);
        }
    }

    /**
     * Возвращает итерируемый объект логов в промежутке от leftIndex до rightIndex, не включая rightIndex
     */
    public Iterable<LogEntry> range(int leftIndex, int rightIndex){
        synchronized (messages) {
            return messages.range(leftIndex, rightIndex);
        }
    }

    /**
     * Возвращает итерируемый объект всех логов
     */
    public Iterable<LogEntry> all(){
        synchronized (messages) {
            return messages.all();
        }
    }
}
