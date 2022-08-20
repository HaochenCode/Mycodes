// C343 / Summer 2022
//
// KVBinaryNode - a Binary Node class with Key and Value
// helper class for Problem Set 05 - Task B

public class KVBinaryNode<Key, E> {
    private Key key;                      // the Key for this node
    private E value;                      // the Value for this node
    private KVBinaryNode<Key, E> left;    // the left child
    private KVBinaryNode<Key, E> right;   // the right child

    public KVBinaryNode(Key k, E e) {     // constructor method
        this.key = k;
        this.value = e;
        this.left = null;
        this.right = null;
    }

    // setters, getters, etc. :
    public void setKey(Key k) {
        this.key = k;
    }

    public void setValue(E e) {
        this.value = e;
    }

    public void setLeftNode(KVBinaryNode<Key, E> node) {
        this.left = node;
    }

    public void setRightNode(KVBinaryNode<Key, E> node) {
        this.right = node;
    }

    public void setLeftToNull() {
        this.left = null;
    }

    public void setRightToNull() {
        this.right = null;
    }

    public Key getKey() {
        return this.key;
    }

    public E getValue() {
        return this.value;
    }

    public KVBinaryNode<Key, E> getLeftNode() {
        return this.left;
    }

    public KVBinaryNode<Key, E> getRightNode() {
        return this.right;
    }

    public boolean isNodeLeaf() {             // test if node is leaf
        if ((this.left == null) && (this.right == null))
            return true;
        else
            return false;
    }

    public String inOrderTreeRepresentation() {             // inOrderTreeRepresentation traversal description
        String tmp = "";
        if (this.left != null) {
            tmp = tmp + this.left.inOrderTreeRepresentation();
        }
        tmp = tmp + "["+ this.key + ":" + this.value + "]";
        if (this.right != null) {
            tmp = tmp + this.right.inOrderTreeRepresentation();
        }
        return tmp;
    }

} // end of class KVBinaryNode
