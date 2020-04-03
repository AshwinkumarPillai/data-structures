
/**
 * A Dynamic Array implementation.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {

    private T[] arr;
    private int length = 0;
    private int capacity = 0;

    public DynamicArray() {
        this(16);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity cannot be negetive");
        this.capacity = capacity;
        this.arr = (T[]) new Object[capacity];
    }

    public int length() {
        return this.length;
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    public T get(int index) {
        if (index > length - 1)
            throw new IndexOutOfBoundsException();
        return arr[index];
    }

    public void setAtIndex(int index, T value) {
        if (index > length - 1)
            throw new IndexOutOfBoundsException();
        else
            arr[index] = value;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            arr[i] = null;
        }
        length = 0;
    }

    public void add(T element) {
        if (length + 1 >= capacity) {
            if (capacity == 0)
                capacity = 1;
            else
                capacity *= 2;
            T[] temp_arr = (T[]) new Object[capacity];
            for (int i = 0; i < length; i++) {
                temp_arr[i] = arr[i];
            }
            arr = temp_arr;
        }
        arr[length++] = element;
    }

    public T removeAt(int index) {
        if (index >= length || index < 0)
            throw new IndexOutOfBoundsException();
        T value = arr[index];
        T[] temp_arr = (T[]) new Object[length - 1];
        for (int i = 0, j = 0; i < length; i++, j++) {
            if (i == index)
                j--;
            else
                temp_arr[j] = arr[i];
        }
        arr = temp_arr;
        capacity = --length;
        return value;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public int getIndex(Object obj) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(obj)) {
                return i;
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
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (length == 0)
            return "[]";
        else {
            StringBuilder sb = new StringBuilder(length).append("[");
            for (int i = 0; i < length - 1; i++) {
                sb.append(arr[i] + ",");
            }
            sb.append(arr[length - 1] + "]");
            return sb.toString();
        }
    }

}