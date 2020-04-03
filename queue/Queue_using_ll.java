import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Queue implementation using linked list.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class Queue_using_ll<T> implements Iterable<T> {

    private LinkedList<T> list = new LinkedList<T>();

    public Queue_using_ll() {
    }

    public Queue_using_ll(T first) {
        enque(first);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T peek() {
        if (isEmpty())
            throw new RuntimeException("Queue is empty");
        return list.peekFirst();
    }

    // enque = offer = add
    public void enque(T data) {
        list.addLast(data);
    }

    // deque = poll
    public T deque(T data) {
        if (isEmpty())
            throw new RuntimeException("Queue is empty");
        return list.removeFirst();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}