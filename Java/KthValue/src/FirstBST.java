import java.util.Random;

public class FirstBST <K extends Comparable<?super K>> {
    BinaryNodePro<K> root;
    BinaryNodePro<K> current;
    private static int size = 0;

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
    }
    public void traversePreOrder(BinaryNodePro<K> entry) {
        System.out.print(entry.getKey() + "(" + entry.getSize() + ") ");
        if (entry.getLeft() != null) traversePreOrder(entry.getLeft());
        if (entry.getRight() != null) traversePreOrder(entry.getRight());
    }


    public BinaryNodePro kthSmallest(BinaryNodePro<K> node, int k){
        if(node == null)
            return null;
        BinaryNodePro leftNode = kthSmallest(node.getLeft(), k);
        if(leftNode != null)
            return leftNode;
        size++;
        if(size == k)
            return node;

        return kthSmallest(node.getRight(),k);
    }


    public static void main(String[] argv) {
        /*
        FirstBST<Integer> tree = new FirstBST<Integer>();
        Integer[] keys = {2, 4, 6, 8, 10, 3, 5, 7, 9, 11, 12, -10, -20, 100};
        tree.build(keys);
        tree.display();
        */


        FirstBST<Integer> tree1 = new FirstBST<Integer>();
        FirstBST<Integer> tree2 = new FirstBST<Integer>();
        FirstBST<Integer> tree3 = new FirstBST<Integer>();
        Random rdm = new Random();
        Integer[] array1 = new Integer[10];
        Integer[] array2 = new Integer[20];
        Integer[] array3 = new Integer[30];

        for(int i = 0; i < array1.length;i++){
            array1[i]=rdm.nextInt(100);
        }
        for(int i = 0; i < array2.length;i++){
            array2[i]=rdm.nextInt(200);
        }
        for(int i = 0; i < array3.length;i++){
            array3[i]=rdm.nextInt(300);
        }

        tree1.build(array1);
        tree2.build(array2);
        tree3.build(array3);

        //Display the third-smallest value in the tree1
        tree1.display();
        System.out.println(tree1.kthSmallest(tree1.root,3).getKey());

        //Display the 5th-smallest value in the tree2
        tree2.display();
        System.out.println(tree2.kthSmallest(tree2.root,5).getKey());

        //Display the 10th-smallest value in the tree1
        tree3.display();
        System.out.println(tree3.kthSmallest(tree3.root,10).getKey());



        // TODO for C343/Summer 2022 - Problem Set 03 Task B and C
        // see instructions

    }
}
