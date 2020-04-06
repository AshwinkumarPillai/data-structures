import java.util.Iterator;

/**
 * A Hash Table implementation - Quadratic Probing.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class HashTable_QuadraticProbing<K, V> extends HashTable_OpenAddressing<K, V> {

    public HashTable_QuadraticProbing() {
        super();
    }

    public HashTable_QuadraticProbing(int capacity) {
        super(capacity);
    }

    // Designated constructor
    public HashTable_QuadraticProbing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    @Override
    protected void setUpProbing(K key) {
    }

    @Override
    protected int probe(int x) {
        return x * x + x >> 1;
    }

    @Override
    protected void increaseCapacity() {
        capacity = nextPowerOfTwo(capacity);
    }

    private static int nextPowerOfTwo(int n) {
        return Integer.highestOneBit(n) << 1;
    }

    @Override
    protected void adjustCapacity() {
        int pow2 = Integer.highestOneBit(capacity);
        if (capacity == pow2)
            return;
        increaseCapacity();
    }

}