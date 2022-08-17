import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// Test for the functions in the Current Class
class CurrentTest {

    // Test for the Withdraw function
    @Test
    void withdraw() {
        // Create a new object and set the balance to 20000
        Current c1 = new Current(20000);
        assertEquals(20000, c1.balance);
        // Test 1: Withdraw 1000 from account
        c1.Withdraw(1000);
        assertEquals(19000,c1.balance);
        // Test 2: Withdraw 5000 from account
        c1.Withdraw(5000);
        assertEquals(14000,c1.balance);
    }

    // Test for the Deposit function
    @Test
    void deposit() {
        // Create a new object and set the balance to 20000
        Current c1 = new Current(20000);
        assertEquals(20000, c1.balance);
        // Test 1: Deposit 2000 from account
        c1.Deposit(2000);
        assertEquals(22000,c1.balance);
        // Test 2: Deposit 6000 from account
        c1.Deposit(6000);
        assertEquals(28000,c1.balance);
    }
}