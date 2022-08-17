public class Main {
    public static void main(String[] args) {
        // Test 1 for ArrayStatic
        Integer[] array1 = {1,2,3,4,5};
        ArrayStatic as1 = new ArrayStatic(array1);
        as1.removeElementAt(3);
        System.out.println(as1);
        // Test 1 for ArrayDynamic
        ArrayDynamic ad1 = new ArrayDynamic(array1);
        ad1.removeElementAt(3);
        System.out.println(ad1);


        // Test 2 for ArrayStatic
        Integer[] array2 = {10,12,31,44,25};
        ArrayStatic as2 = new ArrayStatic(array2);
        as2.removeElementAt(4);
        System.out.println(as2);
        // Test 2 for ArrayDynamic
        ArrayDynamic ad2 = new ArrayDynamic(array2);
        ad2.removeElementAt(4);
        System.out.println(ad2);

        // Test 3 for ArrayStatic
        Integer[] array3 = {11,44,55};
        ArrayStatic as3 = new ArrayStatic(array3);
        as3.removeElementAt(1);
        System.out.println(as3);
        // Test 3 for ArrayDynamic
        ArrayDynamic ad3 = new ArrayDynamic(array3);
        ad3.removeElementAt(1);
        System.out.println(ad3);


    }


}
