pseudocode for kthSmallest function:
    BinaryNodePro kthSmallest(int k){
    if root == null
      return null;

    BinaryNodePro left = kthSmallest(k)
    if left != null
      return left

    count++;
    if count == k
      return root

    return kthSmallest(k)
    }

Part B:
    The min value in a Max Heap must appear on the bottom level or the second last of the heap, because in a max heap,
    if a node is an internal node and has children, its children must be smaller than itself, so the min value must
    be at a leaf, which is only possible when it's at bottom or second last level, since heap can be represented in
    a complete binary tree.


    For the PDF for Max Heap, I attached the file in the folder, represented by a binary tree.