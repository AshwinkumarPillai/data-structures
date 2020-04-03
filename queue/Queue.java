import java.util.Iterator;

/**
 * A Queue implementation using static array.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

@SuppressWarnings("unchecked")
public class Queue<T> implements Iterable<T> {

    private T[] arr;
    private int front, rear, capacity = 0;

    public Queue() {
        this(10);
    }

    public Queue(int size) {
        front = 0;
        rear = 0;
        capacity = size;
        arr = (T[]) new Object[size];
    }

    public int size() {
        return rear - front;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public void enque(T data) {
        if (rear == capacity)
            throw new RuntimeException("Queue limit exceeded");
        arr[rear] = data;
        rear++;
    }

    public T deque() {
        if (isEmpty())
            throw new RuntimeException("Queue is empty");
        T data = arr[front];
        for (int i = 0; i < rear; i++) {
            arr[i] = arr[i + 1];
        }
        rear--;
        return data;
    }

    public T peek(int index) {
        if (index < front || index > rear)
            throw new IllegalArgumentException();
        return arr[index];
    }

    public T peekLast() {
        if (isEmpty())
            throw new RuntimeException("Queue is empty");
        return arr[rear];
    }

    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeException("Queue is empty");
        return arr[front];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < rear;
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
        sb.append("[ ");
        for (int i = 0; i < rear; i++) {
            sb.append(arr[i] + ", ");
        }
        sb.append(arr[rear] + " ]");
        return sb.toString();
    }

}