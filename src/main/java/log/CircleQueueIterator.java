package log;

import java.util.Iterator;

public class CircleQueueIterator<Type> implements Iterator<Type> {
    private final CircelQueueIterable<Type>  circleQueueIterable;
    private int index;

    CircleQueueIterator(CircelQueueIterable<Type> circelQueueIterable){
        this.circleQueueIterable = circelQueueIterable;
        this.index = circelQueueIterable.getBeginIndex();
    }

    @Override
    public boolean hasNext() {
        return index != circleQueueIterable.getEndIndex();
    }

    @Override
    public Type next() {
        Type type = circleQueueIterable.getCircleQueue().get(index);
        index += 1;
        return type;
    }
}
