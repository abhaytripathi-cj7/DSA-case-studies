//co1
//BSTImplementation
//AVLTree

class AVLNode {
    int key;
    int height = 1;
    AVLNode left, right;

    AVLNode(int key) {
        this.key = key;
    }
}

public class TelemetryAVL {
    private AVLNode root;

    public static int height(AVLNode n) { 
        return n == null ? 0 : n.height; 
    }

    public static int getBalance(AVLNode n) { 
        return n == null ? 0 : height(n.left) - height(n.right); 
    }

    public static void updateHeight(AVLNode n) { 
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right)); 
        }
    }

    public static AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left; 
        AVLNode T2 = x.right;
        
        x.right = y; 
        y.left = T2;
        
        updateHeight(y); 
        updateHeight(x); 
        return x;
    }

    public static AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right; 
        AVLNode T2 = y.left;
        
        y.left = x; 
        x.right = T2;
        
        updateHeight(x); 
        updateHeight(y); 
        return y;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private AVLNode insert(AVLNode node, int key) {
        if (node == null) return new AVLNode(key);
        
        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        updateHeight(node); 
        int bf = getBalance(node);

        // LL Case
        if (bf > 1 && key < node.left.key) return rotateRight(node);
        // RR Case
        if (bf < -1 && key > node.right.key) return rotateLeft(node);
        // LR Case
        if (bf > 1 && key > node.left.key) { 
            node.left = rotateLeft(node.left); 
            return rotateRight(node); 
        }
        // RL Case
        if (bf < -1 && key < node.right.key) { 
            node.right = rotateRight(node.right); 
            return rotateLeft(node); 
        }
        
        return node;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private AVLNode delete(AVLNode node, int key) {
        if (node == null) return node;

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // Node with only one child or no child
            if ((node.left == null) || (node.right == null)) {
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp; // Copy the contents of the non-empty child
                }
            } else {
                // Node with two children: Get the inorder successor
                AVLNode temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = delete(node.right, temp.key);
            }
        }

        if (node == null) return node;

        updateHeight(node);
        int bf = getBalance(node);

        // LL Case
        if (bf > 1 && getBalance(node.left) >= 0) return rotateRight(node);
        // LR Case
        if (bf > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RR Case
        if (bf < -1 && getBalance(node.right) <= 0) return rotateLeft(node);
        // RL Case
        if (bf < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.key + " ");
            printInOrder(node.right);
        }
    }

    public static void main(String[] args) {
        TelemetryAVL tree = new TelemetryAVL();
        int[] initialSessions = {25, 35, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95};
        
        System.out.println("Inserting telemetry initial sessions...");
        for (int session : initialSessions) {
            tree.insert(session);
        }
        
        System.out.println("\nExecuting noon deletions (35, 70, 60)...");
        tree.delete(35);
        tree.delete(70);
        tree.delete(60);
        
        System.out.print("Post-deletion AVL Tree structure (In-Order): ");
        tree.printInOrder(tree.root);
        System.out.println();
    }
}