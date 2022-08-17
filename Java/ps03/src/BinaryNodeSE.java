// C343/Summer 2022
// Problem Set 02
// 2022-5-22
// Haochen Sun / haocsun

public class BinaryNodeSE <E extends Comparable<?super E>> {
    private E value;                     // value-only, no key
    private BinaryNodeSE<E> left;    // left child
    private BinaryNodeSE<E> right;   // right child

    public BinaryNodeSE(E e) {
        value = e;
        left = right = null;
    }

    public void setLeft(BinaryNodeSE<E> node) {
        left = node;
    }

    public void setRight(BinaryNodeSE<E> node) {
        right = node;
    }

    public boolean find(E q) {

        if (q == value)
            return true;
        if (left != null) {
            boolean hasFound = left.find(q);
            if (hasFound)
                return true;
        }
        if (right != null) {
            boolean hasFound = right.find(q);
            if (hasFound)
                return true;
        }
        return false;
    }

    public static void main(String[] argv) {

        // TODO for C343/Summer 2022 - Problem Set 03 Task A
        // see instructions

        // Root
        BinaryNodeSE<Integer> root = new BinaryNodeSE<Integer>(10);
        // Level 1
        BinaryNodeSE<Integer> node1 = new BinaryNodeSE<Integer>(9);
        BinaryNodeSE<Integer> node2 = new BinaryNodeSE<Integer>(6);
        // Level 2
        BinaryNodeSE<Integer> node3 = new BinaryNodeSE<Integer>(7);
        BinaryNodeSE<Integer> node4 = new BinaryNodeSE<Integer>(22);
        BinaryNodeSE<Integer> node5 = new BinaryNodeSE<Integer>(11);
        BinaryNodeSE<Integer> node6 = new BinaryNodeSE<Integer>(14);
        // Level 3
        BinaryNodeSE<Integer> node7 = new BinaryNodeSE<Integer>(1);
        BinaryNodeSE<Integer> node8 = new BinaryNodeSE<Integer>(2);
        BinaryNodeSE<Integer> node9 = new BinaryNodeSE<Integer>(3);
        BinaryNodeSE<Integer> node10 = new BinaryNodeSE<Integer>(5);

        // Build my tree
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setLeft(node5);
        node2.setRight(node6);
        node3.setLeft(node7);
        node3.setRight(node8);
        node4.setLeft(node9);
        node4.setRight(node10);


        // find() needs to be implemented

        System.out.println("is 11 found in the tree: " + root.find(11));
        // find(11) should return true
        System.out.println("is 103 found in the tree: " + root.find(103));
        // find(103) should return false
        System.out.println("is 1 found in the tree: " + root.find(1));
        // find(103) should return true
        System.out.println("is 5 found in the tree: " + root.find(5));
        // find(103) should return true
        System.out.println("is 222 found in the tree: " + root.find(222));
        // find(103) should return false


        // TODO for C343/Summer 2022 - Problem Set 03 Task A
        // see instructions

    } // end of main()

} // end of BinaryNodeSE class