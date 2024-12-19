import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {

    private Node root;

    // Huffman trie node
    private static class Node implements Comparable<Node>, Serializable {
        private final char c;
        private final int frequency;
        private int bit;  // 0 or 1 ,root is -1
        private final Node left, right;

        Node(char ch, int freq, int bit, Node left, Node right) {
            this.c = ch;
            this.frequency = freq;
            this.bit = bit;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.frequency - that.frequency;
        }
    }

    // build the Huffman trie given frequencies
    private static Node buildTrie(Map<Character, Integer> frequencyTable) {
        // initialize priority queue with singleton nodes
        MinPQ<Node> pq = new MinPQ<Node>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.insert(new Node(entry.getKey(), entry.getValue(), -1, null, null));
        }
        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            left.bit = 0;
            right.bit = 1;
            Node parent = new Node('\0', left.frequency + right.frequency, -1, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    /*Given a frequency table which maps symbols of type V to their relative frequencies,
     the constructor should build a Huffman decoding trie*/
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.root = buildTrie(frequencyTable);
    }

    private Match longestPrefixMatchHelper(Node node, BitSequence s, BitSequence querySequence) {
        if (node.isLeaf()) {
            s = s.appended(node.bit);
            if (querySequence.bitAt(s.length() - 1) == node.bit) {
                return new Match(s, node.c);
            } else {
                return null;   // can't find
            }
        }
        if (node.bit != -1) {
            s = s.appended(node.bit);
        }
        int length = s.length();
        if (length >= querySequence.length()) {     // can't keep looking
            return null;
        }
        int correctBit = querySequence.bitAt(length);
        if (correctBit == node.left.bit) {
            return longestPrefixMatchHelper(node.left, s, querySequence);
        } else {
            return longestPrefixMatchHelper(node.right, s, querySequence);
        }
    }

    /* find the longest prefix that matches the given querySequence
    and returns a Match object for that Match.
    For example, for the example Trie given in the introduction,
    if we call trie.longestPrefixMatch(new BitSequence("0011010001")),
    we will get back a Match object containing b as the symbol and 001 as the BitSequence.
    (001 is the longest prefix of 0011010001 that is a match inside our decoding binary trie.)
    */
    public Match longestPrefixMatch(BitSequence querySequence) {
        return longestPrefixMatchHelper(root, new BitSequence(), querySequence);
    }

    private void buildLookupTableHelper(Node node, BitSequence s, Map<Character, BitSequence> map) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            map.put(node.c, s.appended(node.bit));
        }
        if (node.bit != -1) {
            s = s.appended(node.bit);
        }
        buildLookupTableHelper(node.left, s, map);   //appended method is not destructive
        buildLookupTableHelper(node.right, s, map);
    }

    /*returns the inverse of the coding trie.*/
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> map = new HashMap<>();
        buildLookupTableHelper(root, new BitSequence(), map);
        return map;
    }


}
