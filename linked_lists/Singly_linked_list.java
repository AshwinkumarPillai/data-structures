import java.util.Iterator;

/**
 * A singly linked list implementation.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class Singly_linked_list<T> implements Iterable<T> {

    public int size;
    public Node<T> head = null;
    public Node<T> tail = null;

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public void clear() {
        Node<T> node = head;
        while (node != null) {
            Node<T> next = node.next;
            node = node.next = null;
            node = next;
        }

        head = tail = node = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T data) {
        addLast(data);
    }

    public void addLast(T data) {
        if (isEmpty()) {
            head = tail = new Node<T>(data, null);
        } else {
            tail.next = new Node<T>(data, null);
            tail = tail.next;
        }
        size++;
    }

    public void addFirst(T data) {
        if (isEmpty())
            head = tail = new Node<T>(data, null);
        else {
            Node<T> temp = new Node<T>(data, head);
            head = temp;
        }
        size++;
    }

    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeException("Empty List");
        return head.data;
    }

    public T peekLast() {
        if (isEmpty())
            throw new RuntimeException("Empty List");
        return tail.data;
    }

    public T removeFirst() {
        if (isEmpty())
            throw new RuntimeException("Empty List");
        T data = head.data;
        // head.data = null;
        head = head.next;
        --size;
        if (isEmpty())
            tail = null;
        return data;
    }

    public T removeLast() {
        if (isEmpty())
            throw new RuntimeException("Empty List");
        T data = tail.data;
        Node<T> node = head;
        while (node.next != tail) {
            node = node.next;
        }
        tail = node;
        --size;
        if (isEmpty())
            head = tail = null;
        else
            tail.next = null;
        return data;
    }

    private T remove(Node<T> node) {
        if (node.next == null)
            return removeLast();
        T data = node.data;
        Node<T> prev = head;
        while (prev.next != node) {
            prev = prev.next;
        }
        prev.next = node.next;
        node = node.next = null;
        return data;
    }

    public T removeAt(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException();
        Node<T> node = head;
        for (int i = 0; i != index; i++) {
            node = node.next;
        }

        return remove(node);
    }

    public boolean remove(Object obj) {
        Node<T> node = head;
        if (obj == null) {
            for (; node != null; node = node.next) {
                if (node.data == null) {
                    remove(node);
                    return true;
                }
            }
        } else {
            for (; node != null; node = node.next) {
                if (obj.equals(node.data)) {
                    remove(node);
                    return true;
                }
            }
        }
        return false;
    }

    public int getIndex(Object obj) {
        Node<T> node = head;
        int index = 0;
        if (obj == null) {
            for (; node != null; node = node.next, index++) {
                if (node.data == null)
                    return index;
            }
        } else {
            for (; node != null; node = node.next, index++) {
                if (node.data.equals(obj))
                    return index;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return getIndex(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node<T> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                T data = node.data;
                node = node.next;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        Node<T> node = head;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (node != null) {
            sb.append(node.data + ", ");
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}