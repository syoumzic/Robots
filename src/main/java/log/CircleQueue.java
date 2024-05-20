package log;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Кольцевая очередь
 */
public class CircleQueue<Type> implements Iterable<Type>{
    private final Type[] storage;
    private int lastIndex;
    private int length;
    private final int capacity;
    private int shift;
    private final Lock lock = new ReentrantLock();

    public CircleQueue(int capacity){
        if(capacity <= 0)
            throw new IllegalArgumentException();

        this.capacity = capacity;
        this.storage = (Type[])new Object[capacity];
        this.length = 0;
        this.lastIndex = 0;
    }

    /**
     * Добавление элемента в очередь
     */
    public void append(Type type){
        lock.lock();
        try {
            storage[lastIndex] = type;
            lastIndex = (lastIndex + 1) % capacity;
            length = Math.min(length + 1, capacity);
            shift = (lastIndex - length + capacity) % capacity;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<Type> iterator() {
        return new CircleQueueIterator(0, length);
    }

    /**
     * Возвращение итерируемого объекта очереди от leftIndex до rightIndex, не включая rightIndex
     */
    public Iterable<Type> range(int leftIndex, int rightIndex){
        if(leftIndex > rightIndex || leftIndex < 0 || rightIndex > length)
            throw new ArrayIndexOutOfBoundsException();

        return () -> new CircleQueueIterator(leftIndex, rightIndex);
    }

    /**
     * Возвращает размер хранимой в структуре данных
     */
    public int size(){
        return length;
    }

    /**
     * Итератор для кольцевой очереди
     */
    private class CircleQueueIterator implements Iterator<Type> {
        private int index;
        private final int endIndex;

        public CircleQueueIterator(int beginIndex, int endIndex){
            this.index = beginIndex;
            this.endIndex = endIndex;
        }

        @Override
        public boolean hasNext() {
            lock.lock();
            try {
                return index != endIndex;
            }finally {
                lock.unlock();
            }
        }

        @Override
        public Type next() {
            lock.lock();
            try {
                Type type = storage[(shift + index) % capacity];
                index += 1;
                return type;
            }finally {
                lock.unlock();
            }
        }
    }
}
