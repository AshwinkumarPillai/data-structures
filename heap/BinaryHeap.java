import java.util.Arrays;

/**
 * A binary heap implementation using array.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class BinaryHeap {
    private int capacity, size = 0;
    private int[] heap;
    private int minmax = 0; // 0 - min heap; 1- max heap;

    public BinaryHeap() {
        this(16);
    }

    public BinaryHeap(int capacity) {
        this.capacity = capacity;
        heap = new int[capacity];
    }

    public BinaryHeap(int capacity, int min_max) {
        this.capacity = capacity;
        heap = new int[capacity];
        minmax = min_max; // if user wants to choose max heap pass 1 other wise 0.
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getLeftChild(int index) {
        int left_index = 2 * index + 1;
        return heap[left_index];
    }

    private int getRightChild(int index) {
        int right_index = 2 * index + 2;
        return heap[right_index];
    }

    private int getParent(int index) {
        return heap[(index - 1) / 2];
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

    private void swap(int index1, int index2) {
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public void checkCapacity() {
        if (size == capacity) {
            heap = Arrays.copyOf(heap, capacity * 2);
            capacity = capacity * 2;
        }
    }

    private void heapifyUp_Min() {
        int index = size - 1;
        while (hasParent(index) && getParent(index) > heap[index]) {
            int parentIndex = (index - 1) / 2;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void heapifyDown_Min() {
        int index = 0;
        while (hasLefChild(index)) {
            int LCindex = (index * 2) + 1;
            int RCindex = (index * 2) + 2;
            int smallestChildIndex = LCindex;
            int LC = getLeftChild(index);
            int RC = getRightChild(index);
            if (hasRightChild(index) && RC < LC) {
                smallestChildIndex = RCindex;
            }

            if (heap[index] <= heap[smallestChildIndex]) {
                break;
            } else {
                swap(index, smallestChildIndex);
            }
            index = smallestChildIndex;
        }
    }

    private void heapifyUp_Max() {
        int index = size - 1;
        while (hasParent(index) && getParent(index) < heap[index]) {
            int parentIndex = (index - 1) / 2;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void heapifyDown_Max() {
        int index = 0;
        while (hasLefChild(index)) {
            int LCindex = (index * 2) + 1;
            int RCindex = (index * 2) + 2;
            int largestChildIndex = LCindex;
            int LC = getLeftChild(index);
            int RC = getRightChild(index);

            if (hasRightChild(index) && RC > LC) {
                largestChildIndex = RCindex;
            }

            if (heap[index] >= heap[largestChildIndex]) {
                break;
            } else {
                swap(index, largestChildIndex);
            }
            index = largestChildIndex;
        }
    }

    public void insert(int data) {
        checkCapacity();
        heap[size] = data;
        size++;
        if (minmax == 0) {
            heapifyUp_Min();
        } else {
            heapifyUp_Max();
        }
    }

    public int poll() {
        if (isEmpty())
            throw new IllegalStateException();
        int data = heap[0];
        heap[0] = heap[size];
        size--;
        if (minmax == 0) {
            heapifyDown_Min();
        } else {
            heapifyDown_Max();
        }
        return data;
    }

    public int peek() {
        if (isEmpty())
            throw new IllegalStateException();
        return heap[0];
    }

}