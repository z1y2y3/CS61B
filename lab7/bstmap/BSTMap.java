package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode root;   //  reprent the whole BST

    private int size;

    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }

        BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns true if and only if this dictionary contains KEY as the
     * key of some key-value pair.
     */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    private boolean containsKeyHelper(BSTNode rootNode, K key) {
        if (rootNode == null) {
            return false;
        }
        int cmp = rootNode.key.compareTo(key);
        if (cmp > 0) {
            return containsKeyHelper(rootNode.left, key);
        } else if (cmp < 0) {
            return containsKeyHelper(rootNode.right, key);
        } else {
            return true;
        }
    }

    /**
     * Returns the value corresponding to KEY or null if no such value exists.
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(BSTNode rootNode, K key) {
        if (rootNode == null) {
            return null;
        }
        int cmp = rootNode.key.compareTo(key);
        if (cmp > 0) {
            return getHelper(rootNode.left, key);
        } else if (cmp < 0) {
            return getHelper(rootNode.right, key);
        } else {
            return rootNode.value;
        }
    }

    /**
     * the size of the mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Inserts the key-value pair of KEY and VALUE into this dictionary,
     * replacing the previous value associated to KEY, if any.
     * don't forget size
     */
    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
        size += 1;
    }

    private BSTNode put(K key, V value, BSTNode rootNode) {
        if (rootNode == null) {
            return new BSTNode(key, value);
        }
        int cmp = rootNode.key.compareTo(key);
        if (cmp > 0) {
            rootNode.left = put(key, value, rootNode.left);
        } else if (cmp < 0) {
            rootNode.right = put(key, value, rootNode.right);
        } else {
            rootNode.value = value;
        }
        return rootNode;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
