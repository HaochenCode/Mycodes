import java.util.Random;

public class Lab12sortByCounting {
    private int max;

    public int[] sortByCounting(int[] array,int k){
        long lTimeBefore = System.nanoTime();
        // Find the maximum of the array
        int maxx =array[0];
        // Loop through the array A
        for(int i = 1; i < array.length;i++){
            // If any element greater than max,
            if(array[i] > array[i-1])
                // Assign it to the maximum
                maxx = array[i];
        }

        // save the max value
        max = maxx;

        // Error checking
        if(k < max)
            // Print the error message
            System.out.println("K is smaller than the maximum.");

        // B array which stores the count for each element in array A
        int[] temp = new int[k];
        // Count ++ each time
        for(int i = 0; i < array.length;i++){
            temp[array[i]] += 1;
        }

        // Accumulate the counts in array B
        for(int i = 1; i < temp.length;i++){
            temp[i] += temp[i-1];
        }

        // The count -- and then put the element of A in the right position in C
        int[] result = new int[array.length];
        // In array C, loop through each element
        for(int i = array.length-1;i>= 0;i--){
            int a = array[i];
            temp[a] -= 1;
            result[temp[a]] =array[i];
        }
        long lTimeAfter = System.nanoTime();
        long lElapsedNanoSeconds = (lTimeAfter - lTimeBefore);
        System.out.println("Running time: "+ lElapsedNanoSeconds);
        // Return array C
        return result;
    }


    // Getter to get the maximum of the array
    public int getK(){ return max;}

    void sort(int arr[])
    {
        int n = arr.length;
        long lTimeBefore = System.nanoTime();

        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        long lTimeAfter = System.nanoTime();
        long lElapsedNanoSeconds = (lTimeAfter - lTimeBefore);
        System.out.println("Running time: "+ lElapsedNanoSeconds);
    }
    public static void main(String[] args) {
        // Test 1 for n = 10
        Random rdm = new Random();
        Lab12sortByCounting srt = new Lab12sortByCounting();
        int[] rdmArray1 = new int[10000];

        // Fill the random int array
        for(int i = 0; i < rdmArray1.length;i++){
            rdmArray1[i] = rdm.nextInt(100000);
        }

        srt.sort(rdmArray1);
        /*// Call the sort function
        int[] result1 = srt.sortByCounting(rdmArray1,1000000000);


        // Test 2 for n =20
        Lab12sortByCounting srt2 = new Lab12sortByCounting();
        int[] rdmArray2 = new int[100];
        // Fill a random int array
        for(int i = 0; i < rdmArray2.length;i++){
            rdmArray2[i] = rdm.nextInt(100);
        }
        // Call the sort function
        int[] result2 = srt.sortByCounting(rdmArray2,100);

        // Test 3 n = 30
        Lab12sortByCounting srt3 = new Lab12sortByCounting();
        int[] rdmArray3 = new int[1000];
        // Fill the array
        for(int i = 0; i < rdmArray3.length;i++){
            rdmArray3[i] = rdm.nextInt(100);
        }

        // Call the sort function
        int[] result3 = srt.sortByCounting(rdmArray3,100);

        // Test 4 n =40
        Lab12sortByCounting srt4 = new Lab12sortByCounting();
        int[] rdmArray4 = new int[10000];
        // Fill the array with random int
        for(int i = 0; i < rdmArray4.length;i++){
            rdmArray4[i] = rdm.nextInt(100);
        }
        // Call the sort function
        int[] result4 = srt.sortByCounting(rdmArray4,100);

        // Test 5 n = 50
        Lab12sortByCounting srt5 = new Lab12sortByCounting();
        int[] rdmArray5 = new int[1000000];
        // Fill the random array
        for(int i = 0; i < rdmArray5.length;i++){
            rdmArray5[i] = rdm.nextInt(100);
        }

        // Call the sort function
        int[] result5 = srt5.sortByCounting(rdmArray5,100);
*/


        // When the maximum of the array exceeds 2^32, which is the upperbound of integer datatype, it will crash.
    }


}
