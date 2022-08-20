// C343/Summer 2022
// Problem Set 02
// 2022-5-22
// Haochen Sun / haocsun
public class BinaryNodePro <K extends Comparable<?super K>> {

    private K key;                // key-only, no value
    private BinaryNodePro<K> left;   // left child
    private BinaryNodePro<K> right;  // right child

    private int size;          // the size (number of nodes)
    // of the subtree rooted at this node

    public BinaryNodePro(K k) {
        key = k;
        left = right = null;
        size = 1;
    }

    public void setLeft(BinaryNodePro<K> node) {
        left = node;
    }

    public void setRight(BinaryNodePro<K> node) {
        right = node;
    }

    public boolean isLeaf() {
        if (left == null && right == null)
            return true;
        else
            return false;
    }

    public BinaryNodePro<K> getLeft() {
        return left;
    }

    public BinaryNodePro<K> getRight() {
        return right;
    }

    public K getKey() {
        return key;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int newSize) {
        size = newSize;
    }

}