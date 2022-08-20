// C343/Summer 2022
// Problem Set 02
// 2022-5-22
// Haochen Sun / haocsun


import static java.lang.Math.abs;

public class FirstBST <K extends Comparable<?super K>> {
    BinaryNodePro<K> root;
    BinaryNodePro<K> current;

    // TODO for C343/Summer 2022 - Problem Set 03 Task B
    // "unbalanced" is used for balance checking:
    //    if a node is unbalanced, the tree will be unbalanced
    BinaryNodePro<K> unbalanced = null;

    public FirstBST() {
        root = null;
        current = null;
    }
    public void build(K[] keys) {
        for (int i = 0; i < keys.length; i ++)
            insert(keys[i]);
    }
    public void insert(K k) {
        BinaryNodePro<K> tmpNode = new BinaryNodePro<K>(k);
        if (root == null) {
            root = current = tmpNode;
        } else {
            current = search(root, k);
            if (k.compareTo(current.getKey()) < 0)
                current.setLeft(tmpNode);
            else
                current.setRight(tmpNode);
        }
    }
    public BinaryNodePro<K> search(BinaryNodePro<K> entry, K k) {
        if (entry == null) {
            return null;
        } else {
            // update the size of the subtree by one:
            entry.setSize(entry.getSize() + 1);
            if (entry.isLeaf())
                return entry;
            if (k.compareTo(entry.getKey()) < 0) {
                if (entry.getLeft() != null)
                    return search(entry.getLeft(), k);
                else
                    return entry;
            } else {
                if (entry.getRight() != null)
                    return search(entry.getRight(), k);
                else
                    return entry;
            }
        }
    }
    public void display() {
        if (root == null) {
            return;
        }
        System.out.println("Pre-order enumeration: key(size-of-the-subtree)");
        traversePreOrder(root);
        System.out.println();

        System.out.println("Post-order enumeration: key(size-of-the-subtree)");
        traversePostOrder(root);
        System.out.println();

        System.out.println("In-order enumeration: key(size-of-the-subtree)");
        traverseInOrder(root);
        System.out.println();
    }
    public void traversePreOrder(BinaryNodePro<K> entry) {
        System.out.print(entry.getKey() + "(" + entry.getSize() + ") ");
        if (entry.getLeft() != null) traversePreOrder(entry.getLeft());
        if (entry.getRight() != null) traversePreOrder(entry.getRight());
    }

    public void traverseInOrder(BinaryNodePro<K> entry){
        if(entry.getLeft() != null) traverseInOrder(entry.getLeft());
        System.out.print(entry.getKey() + "(" + entry.getSize() + ") ");
        if (entry.getRight() != null) traversePreOrder(entry.getRight());
    }

    public void traversePostOrder(BinaryNodePro<K> entry){
        if(entry.getLeft() != null) traversePostOrder(entry.getLeft());
        if (entry.getRight() != null) traversePostOrder(entry.getRight());
        System.out.print(entry.getKey() + "(" + entry.getSize() + ") ");
    }

    // TODO for C343/Summer 2022 - Problem Set 03 Task B
    // implement balanceCheck(),
    //   and you may write heightAtNode(node) as helper function

    public boolean balanceCheck(BinaryNodePro node){
        if(node == null)
            return true;
        int lh = heightAtNode(node.getLeft());
        int rh = heightAtNode(node.getRight());

        if(abs(lh -rh) <= 1 && balanceCheck(node.getLeft()) && balanceCheck(node.getRight()))
            return true;

        return false;
    }

    public int heightAtNode(BinaryNodePro<K> node){
        if(node.getLeft() == null && node.getRight() == null){
            return 0;
        }
        int rightHeight = 1;
        int leftHeight = 1;
        if(node.getLeft() != null){
            leftHeight = leftHeight + heightAtNode(node.getLeft());
        }
        if(node.getRight() != null){

            leftHeight= rightHeight + heightAtNode(node.getRight());
        }

        if(leftHeight >= rightHeight)
            return leftHeight;
        else
            return rightHeight;


    }

    // TODO for C343/Summer 2022 - Problem Set 03 Task C
    // implement traverseInOrder()
    // implement traversePostOrder()


    public static void main(String[] argv) {
        FirstBST<Integer> tree = new FirstBST<Integer>();
        Integer[] keys = {2, 4, 6, 8, 10, 3, 5, 7, 9, 11, 12, -10, -20, 100};
        tree.build(keys);
        tree.display();
        System.out.println(tree.balanceCheck(tree.root));


        // TODO for C343/Summer 2022 - Problem Set 03 Task B and C
        // see instructions

    }
}