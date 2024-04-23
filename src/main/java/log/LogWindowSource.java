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
    private final Lock lock = new ReentrantLock();
    
    public LogWindowSource(int iQueueLength){
        messages = new CircleQueue<>(iQueueLength);
    }

    /**
     * Добавляет слушателя
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        lock.lock();
        try{
            support.addPropertyChangeListener(listener);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Удаляет слушателя
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        lock.lock();
        try{
            support.removePropertyChangeListener(listener);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Добавляет сообщение в лог
     */
    public void append(LogLevel logLevel, String strMessage){
        lock.lock();
        try{
            LogEntry entry = new LogEntry(logLevel, strMessage);
            messages.append(entry);
            support.firePropertyChange("logChange", null, null);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает итерируемый объект логов в промежутке от leftIndex до rightIndex, не включая rightIndex
     */
    public Iterable<LogEntry> range(int leftIndex, int rightIndex){
        lock.lock();
        try{
            return messages.range(leftIndex, rightIndex);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает итерируемый объект всех логов
     */
    public Iterable<LogEntry> all(){
        lock.lock();
        try{
            return messages.all();
        }finally {
            lock.unlock();
        }
    }
}
