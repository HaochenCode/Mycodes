package heapsort;

/** A max-heap.
 * Special thanks to Prof.Yuzhen Ye, CSCI, Indiana University Bloomington, for the original source code.  */

import java.lang.Comparable;

public class Heap <E extends Comparable<? super E>> {

    /** store the elements in an array, listArray
     *
     * Note: this implementation uses indices from 0 to maxSize-1 ,
     *       the first element is at index 0.
     *
     * (unlike other implementations, e.g. in the Weiss textbook,
     *    which instead uses index 1 for the first element)
     */

    private E[] listArray;
    private int listSize;
    private int maxSize;

    Heap(int maxn, int num, E[] inp) {
        maxSize = maxn;
        listSize = num;
        listArray = inp;
        heapify();
    }

    public void heapify() {
        /** Note: siftDown is run from the middle node to the starting node,
         * so the children of a node are visited before the node itself
         * (subtrees are already heaps)
         */
        for(int i = listSize/2 - 1; i >= 0; i --) {
            siftDown(i);
        }
    }

    public void insert(E inp) {
        assert listSize < maxSize : "exceed maxSize " + maxSize;
        listArray[listSize ++] = inp;
        /** sift up the newly added node if it is greater than its ancestors */
        siftUp(listSize - 1);
    }

    public E removeMax() {
        return remove(0);
    }

    public E remove(int pos) {
        assert (pos >= 0) && (pos < listSize) : "Illegal remove";
        if(pos == listSize -1) listSize --;
        else {
            /** swap the max (at position 0) with the last one, then remove the last one */
            swapNodes(pos, --listSize);
            /** call siftUp() & siftDown() to restore the heap */
            if(pos > 0) siftUp(pos);
            if(!isLeaf(pos)) siftDown(pos);
        }
        return listArray[listSize];
    }

    public void siftDown(int pos) {
        assert (pos >= 0) && (pos < listSize) : "Illegal heap position";
        /** System.out.println("now work on " + pos); */
        while(!isLeaf(pos)) {
            int i = left(pos);
            /** if the right child is greater than the left child, use the right child */
            if((i < listSize - 1) && (listArray[i].compareTo(listArray[i + 1]) < 0)) i ++;
            if(listArray[i].compareTo(listArray[pos]) > 0) {
                swapNodes(i, pos);
            }
            pos = i;
        }
    }

    public void siftUp(int pos) {
        while(pos > 0) {
            int p = parent(pos);
            if(listArray[pos].compareTo(listArray[p]) > 0) {
                swapNodes(p, pos);
            }
            pos = p;
        }
    }

    public void swapNodes(int i, int j) {
        E tmp = listArray[i];
        listArray[i] = listArray[j];
        listArray[j] = tmp;
    }

    public boolean isLeaf(int pos) {
        if(pos < listSize/2) return false;
        else return true;
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
        for(int i = 0; i < listSize; i ++) output += " " + listArray[i];
        System.out.println(output);
    }

    /** sort the entire heap */
    public void heapsort() { heapsort(listSize); }

    /** only the k largest numbers will be placed at the end of the heap */
    public void heapsort(int k) {
        int listSizeOld = listSize;
        if(k > listSize) k = listSize;
        for(int i = 0; i < k; i ++) {
            removeMax();
            //display();
        }
        listSize = listSizeOld;
    }


} // end of class Heap