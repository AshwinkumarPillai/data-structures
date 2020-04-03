import java.util.ArrayList;
import java.util.List;

/**
 * A binary heap implementation using list.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */
public class BinaryHeap_generic<T extends Comparable<T>> {
    private int capacity, size = 0;
    private List<T> heap = null;

    public BinaryHeap_generic() {
        this(1);
    }

    public BinaryHeap_generic(int capacity) {
        this.capacity = capacity;
        heap = new ArrayList<>(capacity);
    }

    public BinaryHeap_generic(T[] elements) {
        size = capacity = elements.length;
        heap = new ArrayList<T>(capacity);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T data) {
        for (int i = 0; i < size; i++)
            if (heap.get(i).equals(data))
                return true;
        return false;
    }

    private boolean hasParent(int index) {
        return (index - 1) / 2 >= 0;
    }

    private boolean hasLefChild(int index) {
        return (2 * index) + 1 < size;
    }

    private boolean hasRightChild(int index) {
        return (2 * index) + 2 < size;
    }

    public T peek() {
        if (isEmpty())
            return null;
        return heap.get(0);
    }

    public void add(T data) {
        if (data == null)
            throw new IllegalArgumentException();
        if (size < capacity) {
            heap.set(size, data);
        } else {
            heap.add(data);
            capacity++;
        }

        swim(size);
        size++;
    }

    public T poll() {
        return removeAt(0);
    }

    public T removeAt(int index) {
        if (isEmpty())
            return null;
        size--;
        T data = heap.get(index);
        swap(index, size);
        heap.set(size, null);
        // Check if the data that had to be removed was at the last index.
        if (index == size)
            return data;

        // Now we need to sink or swim to heapify before that save the element to check
        // if swim was needed.
        T element = heap.get(index);

        sink(index);

        if (heap.get(index).equals(element)) {
            swim(index);
        }

        return data;
    }

    // Heapify Up
    private void swim(int index) {
        int parentIndex = (index - 1) / 2;
        while (hasParent(index) && isLess(index, parentIndex)) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    // Heapify Down
    private void sink(int index) {
        while (true) {
            int LCindex = (2 * index) + 1;
            int RCindex = (2 * index) + 2;
            int smallestIndex = LCindex;

            if (hasRightChild(index) && isLess(RCindex, LCindex)) {
                smallestIndex = RCindex;
            }

            if (isLess(index, smallestIndex))
                break;
            swap(index, smallestIndex);
            index = smallestIndex;
        }
    }

    private void swap(int index1, int index2) {
        T node1 = heap.get(index1);
        T node2 = heap.get(index2);

        heap.set(index1, node2);
        heap.set(index2, node1);
    }

    private boolean isLess(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    // Testing code - to test if the heap is still a valid min heap i.e. Heap
    // property is satisfied.

    public boolean isMinHeapValid(int index) {
        if (index >= size)
            return true;

        int LCindex = (index * 2) + 1;
        int RCindex = (index * 2) + 2;

        if (hasLefChild(index) && !isLess(index, LCindex))
            return false;
        if (hasRightChild(index) && !isLess(index, RCindex))
            return false;

        return isMinHeapValid(LCindex) && isMinHeapValid(RCindex);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    // private T getLeftChild(int index) {
    // int left_index = 2 * index + 1;
    // return heap.get(left_index);
    // }

    // private T getRightChild(int index) {
    // int right_index = 2 * index + 2;
    // return heap.get(right_index);
    // }

    // private T getParent(int index) {
    // return heap.get((index - 1) / 2);
    // }
}