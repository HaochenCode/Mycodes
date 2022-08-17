// Subclass for Account
public class Savings extends Account{
    // A global constant that represents the interest rate
     final private double interestRate = (double) 0.8;

     // Constructor that sets the balance
     public Savings(double balance){ this.balance = balance;}

    // A method that reduces the balance of the Savings account
    // override the method in base class
     public void Withdraw(double amount){
         balance -= amount + amount * interestRate;
     }

     // A method that increases the balance of the Savings account
     // override the method in base class
     public void Deposit(double amount){
         balance += amount+ amount * interestRate;
     }

     // override the method in base class
     // Printing method to print the balance
     public void PrintBalance(){
         System.out.println(balance);
     }


}
