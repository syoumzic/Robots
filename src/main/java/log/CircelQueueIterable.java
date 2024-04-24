package log;

import java.util.Iterator;

public class CircelQueueIterable<Type> implements Iterable<Type>{
    private final CircleQueue<Type> circleQueue;
    private final int beginIndex;
    private final int endIndex;

    CircelQueueIterable(CircleQueue<Type>circleQueue, int beginIndex, int endIndex){
        this.circleQueue = circleQueue;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Iterator<Type> iterator() {
        return new CircleQueueIterator<>(this);
    }

    public CircleQueue<Type> getCircleQueue() {
        return circleQueue;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
