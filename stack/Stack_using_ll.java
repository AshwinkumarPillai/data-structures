import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Stack implementation using linked list.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class Stack_using_ll<T> implements Iterable<T> {

    private LinkedList<T> list = new LinkedList<T>();

    public Stack_using_ll() {
    }

    public Stack_using_ll(T first) {
        push(first);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void push(T data) {
        list.addLast(data);
    }

    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        return list.removeLast();
    }

    public T peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return list.peekLast();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

}