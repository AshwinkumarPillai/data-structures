import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A binary heap implementation using list and enhanced with Hash Map.
 *
 * This is also used for Priority Queue (hence the redirect)
 * 
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class BinaryHeap_generic_Map<T extends Comparable<T>> {
    private int capacity, size = 0;
    private List<T> heap = null;
    private Map<T, TreeSet<Integer>> map = new HashMap<>();

    public BinaryHeap_generic_Map() {
        this(1);
    }

    public BinaryHeap_generic_Map(int capacity) {
        this.capacity = capacity;
        heap = new ArrayList<>(capacity);
    }

    public BinaryHeap_generic_Map(T[] elements) {
        size = capacity = elements.length;
        heap = new ArrayList<T>(capacity);

        for (int i = 0; i < size; i++) {
            mapAdd(elements[i], i);
            heap.add(elements[i]);
        }

        for (int i = Math.max(0, size / 2 - 1); i >= 0; i++)
            sink(i);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++)
            heap.set(i, null);
        size = 0;
        map.clear();
    }

    public boolean contains(T data) {
        if (data == null)
            return false;
        return map.containsKey(data);
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

        mapAdd(data, size);
        swim(size);
        size++;
    }

    public T poll() {
        return removeAt(0);
    }

    public boolean remove(T data) {
        if (data == null)
            return false;
        Integer index = mapGet(data);
        if (index != null)
            removeAt(index);
        return index != null;
    }

    private T removeAt(int index) {
        if (isEmpty())
            return null;
        size--;
        T data = heap.get(index);
        swap(index, size);
        heap.set(size, null);
        MapRemove(data, size);
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

    // All map functions
    private void mapAdd(T value, int index) {
        TreeSet<Integer> set = map.get(value);

        if (set == null) {
            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);
        } else
            set.add(index);
    }

    private void MapRemove(T data, int index) {
        TreeSet<Integer> set = map.get(data);
        set.remove(index);
        if (set.size() == 0)
            map.remove(data);
    }

    private Integer mapGet(T data) {
        TreeSet<Integer> set = map.get(data);
        if (set != null)
            return set.last();
        return null;
    }

    private void mapSwap(T data1, T data2, int data1index, int data2index) {
        Set<Integer> set1 = map.get(data1);
        Set<Integer> set2 = map.get(data2);

        set1.remove(data1index);
        set2.remove(data2index);

        set1.add(data2index);
        set2.add(data1index);
    }

    // Helper funcitons
    private void swap(int index1, int index2) {
        T node1 = heap.get(index1);
        T node2 = heap.get(index2);

        heap.set(index1, node2);
        heap.set(index2, node1);

        mapSwap(node1, node2, index1, index2);
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
}
