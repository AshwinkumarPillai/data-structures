import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * A Stack implementation using Static Array.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

@SuppressWarnings("unchecked")
public class Stack<T> implements Iterable<T> {

    private T[] arr;
    private int head = -1;
    public int size = 0;

    public Stack() {
        this(10);
    }

    public Stack(int size) {
        arr = (T[]) new Object[size];
        this.size = size;
    }

    public int size() {
        int index = head;
        return index;
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public void push(T data) {
        if (head + 1 == size)
            throw new StackOverflowError();
        head++;
        arr[head] = data;
    }

    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        int index = head;
        head--;
        return arr[index];
    }

    public T peekElem(int index) {
        if (index < 0 || index > head)
            throw new IllegalArgumentException();
        return arr[index];
    }

    public T peek() {
        return arr[head];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index != head;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < head; i++)
            sb.append(arr[i] + ", ");
        sb.append(arr[head] + "]");
        return sb.toString();
    }

}
