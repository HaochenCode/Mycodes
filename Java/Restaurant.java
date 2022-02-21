import java.sql.SQLOutput;
import java.util.Scanner;

public class Restaurant {
    private int balance = 0;


    public int getBalance() { return balance; }

    public void setBalance(int balance) { this.balance = balance; }

    public void itemPurchase(int purchase){ balance -= purchase;}

    public void itemSell (int sell){ balance += sell;}

}
