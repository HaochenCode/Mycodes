import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test for the functions in Savings class
class SavingsTest {

    @Test
    void withdraw() {
        // Set the initial balance to 20000
        Savings s1 = new Savings(20000);
        assertEquals(20000, s1.balance);
        // First test
        s1.Withdraw(1000);
        assertEquals(18200,s1.balance);
        // Second test
        s1.Withdraw(10000);
        assertEquals(200,s1.balance);
    }

    @Test
    void deposit() {
        // Set the initial balance to 20000
        Savings s1 = new Savings(20000);
        assertEquals(20000, s1.balance);
        // First test : deposit 2000 into account
        s1.Deposit(2000);
        assertEquals(23600,s1.balance);
        // Second test : deposit 10000 into account
        s1.Deposit(10000);
        assertEquals(41600,s1.balance);
    }


}