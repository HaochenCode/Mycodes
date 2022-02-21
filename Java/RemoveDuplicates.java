import java.util.Arrays;

public class RemoveDuplicates {
    public static int[] removeDuplicates (int[] arry){
        int count = 1;
        Arrays.sort(arry);
        // Compare each element to the previous one in the array
        for(int i = 1; i <= arry.length-1; i++){
            if(arry[i]!=arry[i-1]){
                count++;
                arry[count-1] = arry[i];
            }
            else continue;
        }
        // Copy the non-zero elements to a new array with length count
        int[] finalArray = Arrays.copyOf(arry,count);
        // Return the array
        return finalArray;
    }

    public static void main(String[] args) {
        int[] array1 = {0,0,1,1,2,2,3,3,4};
        System.out.print("Input Array = [");
        for (int i = 0;i < array1.length;i++) {
            System.out.print(array1[i] + ", ");
        }
        System.out.print("]" + "\n");

        int[] removed = RemoveDuplicates.removeDuplicates(array1);
        // Print the output
        System.out.print("Output Array = [");
        for (int i = 0;i < removed.length;i++){
            System.out.print(removed[i]+", ");
        }
        System.out.print("]");
    }
}
