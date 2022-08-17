// A subclass of Account class
public class Current extends Account{

    // Constructor that can initialize the object with the given balance
    public Current(double balance){this.balance = balance;}

    // A method that reduces the balance of the Current account
    // override the method in base class
    public void Withdraw(double amount){
        balance -= amount;
    }

    //A method that increases the balance of the Current account
    // override the method in base class
    public void Deposit(double amount){
        balance += amount;
    }

    // Printing method that prints the balance
    // override the method in base class
    public void PrintBalance(){
        System.out.println(balance);
    }
}
