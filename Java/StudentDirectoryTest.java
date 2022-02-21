import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDirectoryTest {

    @Test
    void filter() {
        // Create a new object to use the member function of the class
        StudentDirectory sd1 = new StudentDirectory();
        // Test 1, testing for the case where it ignores all the parameters
        assertArrayEquals(sd1.filter(-1,-1,-1), new int[]{ 1, 2, 3, 4});
        // Test 2, testing for the minYear
        assertArrayEquals(sd1.filter(-1,10,-1), new int[]{ 2, 3, 4});
        // Test 3, testing for all the parameters
        assertArrayEquals(sd1.filter(16,10,70), new int[]{ 2, 3, 4});
        // Test 4, testing for the empty case
        assertArrayEquals(sd1.filter(20,11,90), new int[]{});
    }
}