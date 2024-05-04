package log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
        support.addPropertyChangeListener(listener);
    }

    /**
     * Удаляет слушателя
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    /**
     * Добавляет сообщение в лог
     */
    public void append(LogLevel logLevel, String strMessage){
        LogEntry entry = new LogEntry(logLevel, strMessage);
        messages.append(entry);
        support.firePropertyChange("logChange", null, null);
    }

    /**
     * Возвращает итерируемый объект логов в промежутке от leftIndex до rightIndex, не включая rightIndex
     */
    public Iterable<LogEntry> range(int leftIndex, int rightIndex){
        return messages.range(leftIndex, rightIndex);
    }

    /**
     * Возвращает итерируемый объект всех логов
     */
    public Iterable<LogEntry> all(){
        return messages;
    }

    /**
     * Возвращает размер хранимых логов
     */
    public int size(){
        return messages.size();
    }
}
