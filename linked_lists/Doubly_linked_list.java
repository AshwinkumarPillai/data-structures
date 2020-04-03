import java.util.Iterator;

/**
 * A doubly linked list implementation.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class Doubly_linked_list<T> implements Iterable<T> {

    public int size;
    public Node<T> head = null;
    public Node<T> tail = null;

    private static class Node<T> {
        private T data;
        private Node<T> next, prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public void clear() {
        Node<T> temp = head;
        while (temp != null) {
            Node<T> next = temp.next;
            temp.next = temp.prev = null;
            temp.data = null;
            temp = next;
        }
        head = tail = temp = null;
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
            head = tail = new Node<T>(null, data, null);
        } else {
            tail.next = new Node<T>(tail, data, null);
            tail = tail.next;
        }
        size++;
    }

    public void addFirst(T data) {
        if (isEmpty())
            head = tail = new Node<T>(null, data, null);
        else {
            head.prev = new Node<T>(null, data, head);
            head = head.prev;
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
        // head.prev = null;
        // head.data = null;
        head = head.next;
        --size;
        if (isEmpty())
            tail = null;
        else
            head.prev = null;
        return data;
    }

    public T removeLast() {
        if (isEmpty())
            throw new RuntimeException("Empty List");
        T data = tail.data;
        // tail.data = null;
        tail = tail.prev;
        --size;
        if (isEmpty())
            head = null;
        else
            tail.next = null;
        return data;
    }

    private T remove(Node<T> node) {
        if (node.prev == null)
            return removeFirst();
        if (node.next == null)
            return removeLast();

        node.next.prev = node.prev;
        node.prev.next = node.next;
        T data = node.data;
        node.data = null;
        node = node.next = node.prev = null;
        --size;
        return data;
    }

    public T removeAt(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException();
        int i;
        Node<T> temp;
        if (index < size / 2) {
            for (i = 0, temp = head; i != index; i++) {
                temp = temp.next;
            }
        } else {
            for (i = size - 1, temp = tail; i != index; i--) {
                temp = temp.prev;
            }
        }

        return remove(temp);
    }

    public boolean remove(Object obj) {
        Node<T> temp = head;

        if (obj == null) {
            for (; temp != null; temp = temp.next) {
                if (temp.data == null) {
                    remove(temp);
                    return true;
                }
            }
        } else {
            for (; temp != null; temp = temp.next) {
                if (temp.data.equals(obj)) {
                    remove(temp);
                    return true;
                }
            }
        }
        return false;
    }

    public int getIndex(Object obj) {
        Node<T> temp = head;
        int index = 0;
        if (obj == null) {
            for (; temp != null; temp = temp.next, index++) {
                if (temp.data == null) {
                    return index;
                }
            }
        } else {
            for (; temp != null; temp = temp.next, index++) {
                if (obj.equals(temp.data)) {
                    return index;
                }
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

            private Node<T> node = head;

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