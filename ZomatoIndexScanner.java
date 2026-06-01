//co-2
//BPlusTree
//RangeQuery


import java.util.Arrays;

class BPlusNode {
    boolean isLeaf;
    int[] keys;
    BPlusNode[] children; // Non-leaf child pointers
    BPlusNode next;       // Leaf-chain sequential sibling pointer

    BPlusNode(boolean isLeaf, int capacity) {
        this.isLeaf = isLeaf;
        this.keys = new int[0];
        this.children = new BPlusNode[0];
    }
}

public class ZomatoIndexScanner {

    public static int fetchRangeCount(BPlusNode root, int lo, int hi) {
        if (root == null) return 0;
        BPlusNode leaf = root;
            
        // 1) Proper Logarithmic Descent using Binary Separators
        while (!leaf.isLeaf) {
            int idx = 0;
            while (idx < leaf.keys.length && lo >= leaf.keys[idx]) {
                idx++;
            }
            leaf = leaf.children[idx];
        }
            
        // 2) Linear Leaf Walk featuring Strict Early Termination Check
        int internalAccumulator = 0;
        while (leaf != null) {
            for (int i = 0; i < leaf.keys.length; i++) {
                int key = leaf.keys[i];
                if (key > hi) {
                    return internalAccumulator; // Terminate early: out of range bounds
                }
                if (key >= lo) {
                    internalAccumulator++;
                }
            }
            leaf = leaf.next;
        }
        return internalAccumulator;
    }

    public static void main(String[] args) {
        // Constructing a sample leaf layout to verify correction mechanics
        BPlusNode leaf1 = new BPlusNode(true, 3);
        leaf1.keys = new int[]{25, 35, 45};
        
        BPlusNode leaf2 = new BPlusNode(true, 3);
        leaf2.keys = new int[]{50, 55, 60};
        
        BPlusNode leaf3 = new BPlusNode(true, 3);
        leaf3.keys = new int[]{65, 70, 75};
        
        // Link leaves sequentially
        leaf1.next = leaf2;
        leaf2.next = leaf3;
        
        // Root index separator configuration node
        BPlusNode root = new BPlusNode(false, 2);
        root.keys = new int[]{50, 65};
        root.children = new BPlusNode[]{leaf1, leaf2, leaf3};

        int lo = 35, hi = 70;
        int count = fetchRangeCount(root, lo, hi);
        System.out.println("Total matching records within price segment [" + lo + ", " + hi + "]: " + count);
    }
}