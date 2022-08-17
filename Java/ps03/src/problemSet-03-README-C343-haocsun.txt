// C343/Summer 2022
// Problem Set 02
// 2022-5-22
// Haochen Sun / haocsun

Part D
1: Print tree by level algorithm pseudocode:
    public void printTreeByLevel(BinaryNodePro node){
        h = heightAtNode(root)
        for(int i = 0; i <= h; i++)
          printCurrentLevel(root, i);
    }

    public void printCurrentLevel(BinaryNodePro node, int level){
        if(root == null)
            return
        if(level == 1)
            output root.key
        else:
           printCurrentLevel(node.left,level-1)
           printCurrentLevel(node.right,level-1)
    }

2:
    That is because BST with same elements inserted in the same order should look exactly the same, if some people choose
     to put the same value to the left and others put to the right, or do both, it will be much more confusing to read the
    structure of the tree, and this is a good convention to avoid confusion. Also, you will notice that your tree is wrong
    when it's different from others', since the tree should look the same.

3:
             15
            /  \
          5      20
           \     / \
            7   18  25
                /
               16

 pre-order traversal:  15 5 7 20 18 16 25
 inorder traversal:    5 7 15 16 18 20 25
 post-order traversal: 7 5 16 18 25 20 15

4:
a. A linked-list maintained in sorted order, since the input data is already in sorted order, which is easy and efficient
to insert, and therefore we could use binary search, which is in O(logn) time.

b. A linked-list of unsorted records, since the input data is random, and it takes constant time to insert, even such
big size of data, but it takes longer time to search O(n). But there are only 10 searches.

c. BST, since both insertion and search takes O(logn) time, since both searching and insertion only operates 1000 times,
this works well.

d. BST, since the times of searches is big, so the binary search saves a lot of time since it's in O(logn).