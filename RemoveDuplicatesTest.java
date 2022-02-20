import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveDuplicatesTest {

    @Test
    void removeDuplicates() {
        // Test 1
        int[] ar1 = {1,3,2,4,3,3,1};
        int[] ar2 = RemoveDuplicates.removeDuplicates(ar1);
        int[] ar3 = {1,2,3,4};
        assertArrayEquals(ar2,ar3);

        // Test 2
        int[] arry1 = {0,0,1,1,1,2,2,3,3,4};
        int[] arry2 = RemoveDuplicates.removeDuplicates(arry1);
        int[] arry3 = {0,1,2,3,4};
        assertArrayEquals(arry2,arry3);

        // Test 3
        int[] array1 = {10,20,30,40,40,40,50,50};
        int[] array2 = RemoveDuplicates.removeDuplicates(array1);
        int[] array3 = {10,20,30,40,50};
        assertArrayEquals(array2,array3);
    }
}