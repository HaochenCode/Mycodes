package heapsort;

public class BinarySearch<E extends Comparable<? super E>> {
    public int binarySearch(E[] A, E k){
        int l = -1;
        int r = A.length;
        while(l+1 != r){
            int i = (l+r)/2;
            if(k == A[i]) return i;
            else if( k.compareTo(A[i]) < 0 ) r = i;
            else l = i;
        }
        return A.length;
    }


}
