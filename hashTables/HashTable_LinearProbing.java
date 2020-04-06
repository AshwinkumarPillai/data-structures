import java.util.Iterator;

/**
 * A Hash Table implementation - Linear Probing.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

public class HashTable_LinearProbing<K, V> extends HashTable_OpenAddressing<K, V> {

    private static final int LINEAR_CONSTANT = 17;

    public HashTable_LinearProbing() {
        super();
    }

    public HashTable_LinearProbing(int capacity) {
        super(capacity);
    }

    public HashTable_LinearProbing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    @Override
    protected void setUpProbing(K key) {
        // TODO Auto-generated method stub
    }

    @Override
    protected int probe(int x) {
        return LINEAR_CONSTANT * x;
    }

    @Override
    protected void adjustCapacity() {
        // INcrease capacity untill we get relative prime numbers
        while (gcd(LINEAR_CONSTANT, capacity) != 1) {
            capacity++;
        }
    }
}