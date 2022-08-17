package heapsort;
import java.util.Random;

public class HeapSort {

    private static Random rmachine;

    public static void main(String[] args) {
        rmachine = new Random();
        testHeap();

        // Test for the heapSort
        Integer[] array1 = new Integer[100];
        Double[] array2 = new Double[100];
        String[] array3= new String[100];
        int[] val = new int[100];

        for (int i=0; i<array3.length; i++) {
            val[i] = (int)(100 * Math.random());
            array3[i] = String.valueOf(val[i]);
        }

        for(int i = 0; i< 100;i++){
            array1[i] = rmachine.nextInt(100);
        }

        for(int i = 0; i< 100;i++){
            array2[i] = rmachine.nextDouble(100);

        }



        System.out.println(array1[1]);
        heapsort.Heap<Integer> hp1 =  new heapsort.Heap<Integer>(120, 100, array1);
        heapsort.Heap<Double> hp2 =  new heapsort.Heap<Double>(120, 100, array2);
        heapsort.Heap<String> hp3 =  new heapsort.Heap<String>(120, 100, array3);

        hp1.heapsort();
        hp1.display();
        hp2.heapsort();
        hp2.display();
        hp3.heapsort();
        hp3.display();

        // Test for the Binary Search
        BinarySearch bs = new BinarySearch();
        System.out.println(bs.binarySearch(array1,10));
        System.out.println(bs.binarySearch(array2,3.1));
        System.out.println(bs.binarySearch(array3,"test"));

    }

    public static void testHeap() {
        System.out.println("Now test heap:");
        int maxn = 100;
        int size = 10;
        Integer[] nums = new Integer[maxn];
        for(int i = 0; i < size; i ++) nums[i] = rmachine.nextInt(100);
        heapsort.Heap<Integer> h = new heapsort.Heap<Integer>(maxn, size, nums);
        h.display();
        int num = 200;
        System.out.println("Add " + num);
        h.insert(num);
        h.display();
        System.out.println("Remove max");
        int i = (Integer) h.removeMax();
        h.display();
        System.out.println("Remove position 2");
        int j = (Integer) h.remove(2);
        h.display();
        System.out.println("After HeapSort:");
        h.heapsort();
        h.display();


    }

}