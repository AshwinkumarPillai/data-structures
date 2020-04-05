// import java.util.Iterator;

/**
 * A BinarySearchTree implementation.
 * 
 * @author Ashwinkumar Pillai, ashwinfury1@gmail.com
 */
public class BinarySearchTree<T extends Comparable<T>> {
    private int NodeCount = 0;
    private Node root = null;

    private class Node {
        T data;
        Node left, right;

        public Node(Node left, T data, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public int size() {
        return NodeCount;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(T elem) {
        if (contains(elem))
            return false;
        root = add(root, elem);
        NodeCount++;
        return true;
    }

    private Node add(Node node, T elem) {
        // base case
        if (node == null) {
            node = new Node(null, elem, null);
        } else {
            if (elem.compareTo(node.data) < 0) {
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }
        return node;
    }

    public boolean remove(T elem) {
        if (contains(elem)) {
            root = remove(root, elem);
            NodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T elem) {
        if (node == null)
            return null;
        int cmp = elem.compareTo(node.data);
        if (cmp < 0)
            node.left = remove(node.left, elem);
        else if (cmp > 0)
            node.right = remove(node.right, elem);
        else {
            if (node.left == null) {
                Node rightChild = node.right;
                node.data = null;
                node = null;
                return rightChild;
            } else if (node.right == null) {
                Node leftChild = node.left;
                node.data = null;
                node = null;
                return leftChild;
            } else {
                Node temp = digLeft(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data);
            }
        }
        return node;
    }

    private Node digLeft(Node node) {
        Node cur = node;
        while (cur.left != null)
            cur = cur.left;
        return cur;
    }

    // private Node digRigght(Node node) {
    // Node cur = node;
    // while (cur.right != null)
    // cur = cur.right;
    // return cur;
    // }

    public boolean contains(T elem) {
        return contains(root, elem);
    }

    private boolean contains(Node node, T elem) {
        if (node == null)
            return false;
        int cmp = elem.compareTo(node.data);
        if (cmp < 0)
            contains(node.left, elem);
        else if (cmp > 0)
            contains(node.right, elem);
        return true;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null)
            return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // public Iterator<T> traverse(TreeTraversalOrder order) {
    // switch (order) {
    // case PRE_ORDER:
    // return preOrderTraversal();
    // case IN_ORDER:
    // return inOrderTraversal();
    // case POST_ORDER:
    // return postOrderTraversal();
    // case LEVEL_ORDER:
    // return levelOrderTraversal();
    // default:
    // return null;
    // }
    // }

    // private Iterator<T> preOrderTraversal() {
    // return null;
    // }

    // private Iterator<T> inOrderTraversal() {
    // return null;
    // }

    // private Iterator<T> postOrderTraversal() {
    // return null;
    // }

    // private Iterator<T> levelOrderTraversal() {
    // return null;
    // }

    // public enum TreeTraversalOrder {
    // PRE_ORDER, IN_ORDER, POST_ORDER, LEVEL_ORDER
    // }
}