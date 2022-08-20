// C343 / Summer 2022
//
// KVMinHeap - a min-heap implementation, storing elements in an array
// helper class for Problem Set 05 - Task B

public class KVMinHeap <Key extends Comparable<? super Key>, E> {
    /** store the elements in an array named listArray */
    private KVBinaryNode<Key, E>[] listArray;
    private int listSize;
    private int maxSize;

    public KVMinHeap(int maxn, int num, KVBinaryNode<Key, E>[] pIn) {
        maxSize = maxn;
        listSize = num;
        listArray = pIn;
        heapify();
    }

    public void heapify() {
        /** note siftDown() is called:
         *   starting from the middle node,
         #   ending on the first node,
         * so that the children of any node are visited before the node itself
         *   (this way, their subtrees are already heaps)
         */
        for (int i = listSize/2 - 1; i >= 0; i --) {
            siftDown(i);
        }
    }

    public int length() {
        return listSize;
    }

    public void insert(KVBinaryNode<Key, E> pIn) {
        assert listSize < maxSize : "exceed maxSize " + maxSize;
        listArray[listSize ++] = pIn;
        /** sift up the newly added node if it is less than its ancestors */
        siftUp(listSize - 1);
    }

    public KVBinaryNode<Key, E> removeMin() {
        return remove(0);
    }

    public KVBinaryNode<Key, E> remove(int pos) {
        assert (pos >= 0) && (pos < listSize) : "Illegal remove";
        if (pos == listSize -1)
            listSize --;
        else {
            /** swap the max (at position 0) with the last one, then remove the last one */
            swapNodes(pos, --listSize);
            /** call siftUp() & siftDown() to restore the heap */
            if (pos > 0) {
                siftUp(pos);
            }
            if (!this.isHeapPositionLeaf(pos)) {
                siftDown(pos);
            }
        }
        return listArray[listSize];
    }

    public void siftDown(int pos) {
        assert (pos >= 0) && (pos < listSize) : "Illegal heap position";
        /** System.out.println("now work on " + pos); */
        while (!this.isHeapPositionLeaf(pos)) {
            int i = left(pos);
            /** if the right child is less than the left child, use the right child */
            if ((i < listSize - 1) && (((Key) listArray[i].getKey()).compareTo((Key) listArray[i + 1].getKey()) > 0)) {
                i ++;
            }
            if (((Key) listArray[i].getKey()).compareTo((Key) listArray[pos].getKey()) < 0) {
                swapNodes(i, pos);
            } else {
                break;
            }
            pos = i;
        }
    }

    public void siftUp(int pos) {
        while (pos > 0) {
            int p = parent(pos);
            if (((Key) listArray[pos].getKey()).compareTo((Key)listArray[p].getKey()) < 0) {
                swapNodes(p, pos);
            } else {
                break;
            }
            pos = p;
        }
    }

    public void swapNodes(int i, int j) {
        KVBinaryNode<Key, E> tmp = listArray[i];
        listArray[i] = listArray[j];
        listArray[j] = tmp;
    }

    public boolean isHeapPositionLeaf(int pos) {
        if (pos < listSize/2)
            return false;
        else
            return true;
    }

    public int left(int pos) {
        assert 2 * pos + 1 < listSize : "left child is empty";
        return 2 * pos + 1;
    }

    public int right(int pos) {
        assert 2 * pos + 2 < listSize : "right child is empty";
        return 2 * pos + 2;
    }

    public int parent(int pos) {
        assert pos > 0 : "the node is already a root";
        return (int) Math.floor((pos - 1) / 2);
    }

    public void display() {
        String output = "[HeapSize: " + listSize + "] ";
        for (int i = 0; i < listSize; i ++) {
            output += " " + listArray[i].getKey() + "(" + listArray[i].getValue() + ")";
        }
        System.out.println(output);
    }

} // end of class KVMinHeap
