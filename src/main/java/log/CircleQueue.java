package log;

import java.util.ArrayList;
import java.util.List;

/**
 * Кольцевая очередь
 */
public class CircleQueue<Type>{
    private Type[] storage;
    private int lastIndex;
    private int length;
    private int capacity;
    private int shift;

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
        storage[lastIndex] = type;
        lastIndex = (lastIndex + 1) % capacity;
        length = Math.min(length + 1, capacity);
        shift = (lastIndex - length + capacity) % capacity;
    }

    /**
     * Возвращение всех итерируемого объекта всех элементов очереди
     */
    public Iterable<Type> all(){
        List<Type> list = new ArrayList<>(length);
        for(int i = 0; i < length; i++){
            int shiftIndex = (shift + i) % capacity;
            list.add(storage[shiftIndex]);
        }
        return list;
    }

    /**
     * Возвращение итерируемого объекта очереди от leftIndex до rightIndex, не включая rightIndex
     */
    public Iterable<Type> range(int leftIndex, int rightIndex){
        if(leftIndex > rightIndex || leftIndex < 0 || rightIndex > length)
            throw new ArrayIndexOutOfBoundsException();

        List<Type> list = new ArrayList<>(rightIndex - leftIndex);
        for(int i = leftIndex; i < rightIndex; i++){
            int shiftIndex = (shift + i) % capacity;
            list.add(storage[shiftIndex]);
        }
        return list;
    }

    /**
     * Возвращает значение по индексу
     */
    public Type get(int index) throws ArrayIndexOutOfBoundsException{
        if(index < 0 || index >= length)
            throw new ArrayIndexOutOfBoundsException();
        return this.storage[(shift + index) % capacity];
    }
}
