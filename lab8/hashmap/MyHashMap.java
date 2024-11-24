package hashmap;


import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables ,The size of buckets is buckets.length*/
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /* loadFactor = N/M, where N is the number of elements in the map
       and M is the number of buckets.*/
    private double maxLoadFactor;

    /* size is the number of elements in the map.*/
    private int size;

    /**
     * Constructors
     * <p>
     * If initialSize and loadFactor aren’t given,
     * you should set defaults initialSize = 16 and loadFactor = 0.75
     */
    public MyHashMap() {
        maxLoadFactor = 0.75;
        buckets = createTable(16);
    }

    public MyHashMap(int initialSize) {
        maxLoadFactor = 0.75;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        maxLoadFactor = maxLoad;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        size = 0;
        buckets = null;
        // maxLoadFactor remains
        buckets = createTable(16);
    }

    static final int hash(Object key) {
        return (key == null) ? 0 : key.hashCode();
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        int hashCode = hash(key);
        int index = Math.floorMod(hashCode, buckets.length);

        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        for (Node node : bucket) {
            //  node.key can be null
            if (node.key == null) {
                if (key == null) {
                    return true;
                }
            } else if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hashCode = hash(key);
        int index = Math.floorMod(hashCode, buckets.length);

        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        for (Node node : bucket) {
            //  node.key can be null
            if (node.key == null) {
                return node.value;
            }
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    // double the buckets size
    private void resize() {
        Collection<Node>[] newBuckets = createTable(buckets.length * 2);
        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                int hashCode = hash(node.key);
                int index = Math.floorMod(hashCode, newBuckets.length);
                if (newBuckets[index] == null) {
                    newBuckets[index] = createBucket();
                }
                newBuckets[index].add(node);
            }
        }
        buckets = newBuckets;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     * The load factor (# items / # buckets) should always be <= loadFactor,otherwise,resize
     */
    @Override
    public void put(K key, V value) {
        // after this put,
        double loadFactor = ((double) (size + 1)) / buckets.length;
        if (loadFactor > maxLoadFactor) {
            resize();
        }
        int hashCode = hash(key);
        int index = Math.floorMod(hashCode, buckets.length);

        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            buckets[index] = createBucket();
            bucket = buckets[index];
        }
        for (Node node : bucket) {
            //  node.key can be null
            if (node.key == null) {
                if (key == null) {
                    node.value = value;
                    return;
                }
            } else if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        bucket.add(createNode(key, value));
        size += 1;
    }

/*    Note that you should implement keySet and iterator this time,
    where iterator returns an Iterator that iterates over the stored keys.
    Both of these functions may return the keys in any order.
    For these methods, we recommend you simply create a HashSet instance variable
    that holds all your keys.*/

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        if (size == 0) {
            return set;
        }
        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                set.add(node.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        return remove(key, null);
    }

    @Override
    public V remove(K key, V value) {
        int hashCode = hash(key);
        int index = Math.floorMod(hashCode, buckets.length);

        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;   // no such key, can't remove
        }
        Node nodeToRemove = null;
        for (Node node : bucket) {
            //  node.key can be null
            if (node.key == null) {
                if (key == null) {
                    nodeToRemove = node;
                    break;
                }
            } else if (node.key.equals(key)) {
                nodeToRemove = node;
                break;
            }
        }
        if (nodeToRemove == null) {
            return null;
        }
        bucket.remove(nodeToRemove);
        size -= 1;
        return nodeToRemove.value;
    }

    @Override
    public Iterator<K> iterator() {
        HashSet<K> set = new HashSet<>();
        if (size == 0) {
            return set.iterator();
        }
        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                set.add(node.key);
            }
        }
        return set.iterator();
    }

}
