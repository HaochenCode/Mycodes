import java.util.Random;
import java.util.Scanner;

public class MultiplicationGame {

    public static void main(String[] args) {
        // Build a Scanner object to read input from user
        Scanner scanner = new Scanner(System.in);
        // Build an object in Random class to access random numbers
        Random rand = new Random();
        // Initialize the variables
        int count = 0;
        int guess = 0;
        // Create two int variables to hold the two random number
        int num1 = rand.nextInt(13);
        int num2 = rand.nextInt(13);
        // Show the real answer for the product
        System.out.println("The product is " + num1 * num2);

        // Use while loop to prompt the user's guess till he got right, and use counter to count number of trials
        while(guess != (num1 * num2)) {
            System.out.println("Enter the production of the two numbers: ");
            guess = scanner.nextInt();
            count++;
        }

        // Output the result
        System.out.println("It took " + count + " times for you to get it right.");
}}
