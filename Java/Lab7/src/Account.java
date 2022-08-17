// The base class for Savings and Current
public class Account {
    // Protected variables that shared with subclasses
    protected double balance;

    // Empty Constructor
    public Account(){}

    // Empty methods, will be supplemented in subclasses
    public void Withdraw(double amount){}
    public void Deposit(double amount){}

    // Print method to print the current balance
    public void PrintBalance(){
        System.out.println("The balance now is : " + balance);
    }
}


/*
    Answer to the question:
    False. Since the final keyword means it can't be modified or overridden. Because if we are declaring any method
    with the final keyword, we are indicating the JVM to provide some special attention and make sure no one can
    override it.
 */