import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BST1<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size;

    // Inner class representing a node in the binary search tree
    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    // Method to insert a key-value pair into the BST
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    // Recursive helper method to insert a key-value pair into the BST
    private Node put(Node x, K key, V val) {
        if (x == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val; // Update value if key already exists
        return x;
    }

    // Method to get the value associated with a given key
    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val; // Key found, return corresponding value
        }
        return null; // Key not found
    }

    // Method to delete a node with the given key
    public void delete(K key) {
        root = delete(root, key);
    }

    // Recursive helper method to delete a node with the given key
    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        size--;
        return x;
    }

    // Method to delete the minimum node in a subtree
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    // Method to find the minimum node in a subtree
    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    // Method to get an iterable collection of keys in ascending order
    public Iterable<K> keys() {
        List<K> keys = new ArrayList<>();
        inorder(root, keys);
        return keys;
    }

    // Recursive helper method to perform inorder traversal
    private void inorder(Node x, List<K> keys) {
        if (x == null) return;
        inorder(x.left, keys);
        keys.add(x.key);
        inorder(x.right, keys);
    }

    // Method to get the size of the BST
    public int size() {
        return size;
    }

    // Method to provide an iterator for iterating over the BST entries
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BSTIterator();
    }

    // Inner class representing an iterator for the BST
    private class BSTIterator implements Iterator<Entry<K, V>> {
        private List<Entry<K, V>> entries;
        private int currentIndex;

        public BSTIterator() {
            entries = new ArrayList<>();
            inorderTraversal(root);
            currentIndex = 0;
        }

        // Method to perform inorder traversal and populate the entries list
        private void inorderTraversal(Node node) {
            if (node == null) return;
            inorderTraversal(node.left);
            entries.add(new Entry<>(node.key, node.val));
            inorderTraversal(node.right);
        }

        // Method to check if there are more entries to iterate over
        @Override
        public boolean hasNext() {
            return currentIndex < entries.size();
        }

        // Method to get the next entry in the iteration
        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return entries.get(currentIndex++);
        }
    }

    // Static nested class representing an entry in the BST
    public static class Entry<K, V> {
        private K key;
        private V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        // Method to get the key of the entry
        public K getKey() {
            return key;
        }

        // Method to get the value of the entry
        public V getValue() {
            return val;
        }
    }
}
