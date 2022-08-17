import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    // Test for the constructor that takes no input
    @Test
    void myArrayList() {
        // Create a new object
        MyArrayList al = new MyArrayList();
        // Check the default size and capacity
        assertEquals(0,al.getSize());
        assertEquals(4,al.getDataCapacity());
    }

    // Test for the constructor that takes an array
    @Test
    void testMyArrayList() {
        // Create an array for input
        int[] arr = {1,2,3,4};
        // Create a new object
        MyArrayList al = new MyArrayList(arr);
        // Test the size and capacity after the constructor
        assertEquals(4,al.getSize());
        assertEquals(4,al.getDataCapacity());
    }

    // Test for the add function
    @Test
    void add() {
        // Create a new object
        MyArrayList al = new MyArrayList();
        // Check the size and capacity after the add function
        al.add(10);
        assertEquals(1, al.getSize());
        assertEquals(10,al.getData()[al.getSize()-1]);
    }

    // Test for the remove function
    @Test
    void remove() {
        // Create a new array as input for the instructor to initialize data
        int[] arr = {1,2,3,4,5};
        // Create a new object and initialize the data with this array
        MyArrayList al = new MyArrayList(arr);
        assertEquals(true,al.remove(4));
        assertEquals(true,al.remove(3));
    }

    // Test for the toString function
    @Test
    void testToString() {
        // Test if the output string matches
        int[] arr1 ={0,1,2};
        MyArrayList al = new MyArrayList(arr1);
        assertEquals(",0,1,2",al.toString());
        // Another test
        int[] arr2 = {1,2,3,4};
        MyArrayList al2 = new MyArrayList(arr2);
        assertEquals(",1,2,3,4", al2.toString());

    }

    // Test for the getSize function
    @Test
    void getSize() {
        // Test for the default constructor case
        MyArrayList al1 = new MyArrayList();
        assertEquals(0, al1.getSize());
        // Test for another non-empty case
        int[] arr1 ={0,1,2};
        MyArrayList al2 = new MyArrayList(arr1);
        assertEquals(3,al2.getSize());
    }

    // Test for the isEmpty case
    @Test
    void isEmpty() {
        // Test for empty case
        MyArrayList al = new MyArrayList();
        assertEquals(true,al.isEmpty());
        // Test for the non-empty case
        int[] arr2 ={1,2};
        MyArrayList al2 = new MyArrayList(arr2);
        assertEquals(false,al2.isEmpty());
    }

    //Test for the indexOf function
    @Test
    void indexOf() {
        // Test for the not found case
        MyArrayList al = new MyArrayList();
        assertEquals(-1, al.indexOf(2));
        // Test for the found case
        int[] arr = {1,3,4};
        MyArrayList al2 = new MyArrayList(arr);
        assertEquals(1,al2.indexOf(3));
    }
}