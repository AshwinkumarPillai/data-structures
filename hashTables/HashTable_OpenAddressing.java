import java.util.ArrayList;
import java.util.List;

/**
 * A Hash Table implementation - OpenAddressing.
 *
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */

@SuppressWarnings("unchecked")
public abstract class HashTable_OpenAddressing<K, V> implements Iterable<K> {
    protected double loadFactor;
    protected int capacity, threshold, modificationCount;

    protected int usedBuckets, keycount;

    protected K[] keys;
    protected V[] values;

    protected final K TOMBSTONE = (K) (new Object());

    private static final int DEFAULT_CAPACITY = 7;
    private static final double DEFAULT_LOAD_FACTOR = 0.65;

    protected HashTable_OpenAddressing() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    protected HashTable_OpenAddressing(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    protected HashTable_OpenAddressing(int capacity, double loadFactor) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException("Illegal Load Factor " + loadFactor);
        this.loadFactor = loadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        adjustCapacity();
        threshold = (int) (this.capacity * loadFactor);

        keys = (K[]) new Object[this.capacity];
        values = (V[]) new Object[this.capacity];
    }

    protected abstract void setUpProbing(K key);

    protected abstract int probe(int x);

    protected abstract void adjustCapacity();

    protected void increaseCapacity() {
        capacity = (2 * capacity) + 1;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            keys[i] = null;
            values[i] = null;
        }
        keycount = usedBuckets = 0;
        modificationCount++;
    }

    public int size() {
        return keycount++;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(K key) {
        return hasKey(key);
    }

    public boolean hasKey(K key) {
        if (key == null)
            throw new IllegalArgumentException("Null key");

        setUpProbing(key);
        final int offset = normaliseIndex(key.hashCode());
        for (int i = offset, j = -1, x = 1;; i = normaliseIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE) {

                if (j == -1)
                    j = i;
            } else if (keys[i] != null) {

                if (keys[i].equals(key)) {

                    if (j != -1) {
                        keys[j] = keys[i];
                        values[j] = values[i];
                        keys[i] = TOMBSTONE;
                        values[i] = null;
                    }
                    return true;
                }

            } else
                return false;
        }
    }

    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Null key");

        setUpProbing(key);
        final int offset = normaliseIndex(key.hashCode());

        for (int i = offset, j = -1, x = 1;; i = normaliseIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE) {

                if (j == -1)
                    j = i;

            } else if (keys[i] != null) {

                if (keys[i].equals(key)) {

                    if (j != -1) {
                        keys[j] = keys[i];
                        values[j] = values[i];
                        keys[i] = TOMBSTONE;
                        values[i] = null;
                        return values[j];
                    } else {
                        return values[i];
                    }
                }
            } else
                return null;
        }
    }

    public V remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Null key");

        setUpProbing(key);
        final int offset = normaliseIndex(key.hashCode());

        for (int i = offset, x = 1;; i = normaliseIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE)
                continue;

            if (keys[i] == null)
                return null;

            if (keys[i].equals(key)) {
                keycount--;
                modificationCount++;
                V oldValue = values[i];
                keys[i] = TOMBSTONE;
                values[i] = null;
                return oldValue;
            }
        }
    }

    public List<K> keys() {
        List<K> hashtableKeys = new ArrayList<>(size());
        for (int i = 0; i < capacity; i++)
            if (keys[i] != null && keys[i] != TOMBSTONE)
                hashtableKeys.add(keys[i]);
        return hashtableKeys;
    }

    public List<V> values() {
        List<V> hashTableValues = new ArrayList<>(size());
        for (int i = 0; i < capacity; i++)
            if (keys[i] != null && keys[i] != TOMBSTONE)
                hashTableValues.add(values[i]);
        return hashTableValues;
    }

    public V put(K key, V value) {
        return insert(key, value);
    }

    public V add(K key, V value) {
        return insert(key, value);
    }

    public void set(K key, V value) {
        insert(key, value);
    }

    public V insert(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException();
        if (usedBuckets >= threshold)
            resizeTable();

        setUpProbing(key);
        final int offset = normaliseIndex(key.hashCode());

        for (int i = offset, j = -1, x = 1;; i = normaliseIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE) {
                if (j == -1)
                    j = i;
            } else if (keys[i] != null) {
                V oldValue = values[i];
                if (j == -1) {
                    values[i] = value;
                } else {
                    keys[i] = TOMBSTONE;
                    values[i] = null;
                    keys[j] = key;
                    values[j] = value;
                }
                modificationCount++;
                return oldValue;
            } else {
                if (j == -1) {
                    usedBuckets++;
                    keycount++;
                    keys[i] = key;
                    values[i] = value;
                } else {
                    keycount++;
                    keys[j] = key;
                    values[j] = value;
                }
                modificationCount++;
                return null;
            }
        }
    }

    protected void resizeTable() {
        increaseCapacity();
        adjustCapacity();

        threshold = (int) (capacity * loadFactor);

        K[] oldKeysTable = (K[]) new Object[capacity];
        V[] oldValuesTable = (V[]) new Object[capacity];

        // swap Keys
        K[] tempKeyTable = keys;
        keys = oldKeysTable;
        oldKeysTable = tempKeyTable;

        // swap values
        V[] tempValueTable = values;
        values = oldValuesTable;
        oldValuesTable = tempValueTable;

        keycount = usedBuckets = 0;

        for (int i = 0; i < oldKeysTable.length; i++) {
            if (oldKeysTable[i] != null && oldKeysTable[i] != TOMBSTONE)
                insert(oldKeysTable[i], oldValuesTable[i]);
            oldKeysTable[i] = null;
            oldValuesTable[i] = null;
        }
    }

    protected final int normaliseIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    protected static final int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
}