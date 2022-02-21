import java.util.Scanner;

public class MovieTicket {
    public static boolean ageRestricted(int age, String rating){
        // TODO: Complete this function and return the appropriate boolean value
        // given the two parameters.
        if (age >= 0 && age <= 12) {
            if(rating.equals("G") || rating.equals("PG")){
                return false;
            }
            else if(rating.equals("PG13") || rating.equals("R")){
                return true;
            }
            else System.out.println("Invalid Input!");

        }
        else if(age >= 13 && age <= 17){
            if(rating.equals("G") || rating.equals("PG") || rating.equals("PG13")){
                return false;
            }
            else if(rating.equals("R")){
                return true;
            }
            else System.out.println("Invalid Input!");
        }
        else if(age >= 18){
            if(rating.equals("G") || rating.equals("PG") || rating.equals("PG13") || rating.equals("R")){
                return false;
            }
            else System.out.println("Invalid Input!");
        }
        return true; //Tip: true means ageRestricted, which means the customer
                     //cannot see the movie
    }
    public static int getPrice(int age){
        // TODO: Complete this function and return the correct ticket price given
        // the parameter.
        int price = 0;
        if(age >= 0 && age <= 12)
            price = 5;
        else if(age >= 13 && age <= 17)
            price = 9;
        else if(age >= 18){
            price = 12;
        }
        return price;
    }
    public static void main(String[] args){

       /* TODO: 1. Collect user input to get the movie rating and the number of
tickets for a transaction.
                 2. USING A LOOP, calculate and print either the total cost of the
tickets OR print "Age Restricted" if
                 AT LEAST one customer is age restricted.
                 3. Test the functionality of getPrice and ageRestricted using
JUnit tests in a separate file.
        */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the movie rating: ");
        String rate = scanner.nextLine();
        System.out.println("Enter the number of tickets to buy: ");
        int numOfTickets = scanner.nextInt();

        int age= 0;
        int totalCost= 0;
        boolean restricted = false;

        if(rate.equals("G")  || rate.equals("PG")){
            for(int i = 0; i < numOfTickets; i++) {
                System.out.println("Enter your age for ticket #" + (i + 1) + " : ");
                age = scanner.nextInt();
                totalCost += getPrice(age);
            }
            System.out.println("The total cost is : " + totalCost);
        }

        else if(rate.equals("PG13") || rate.equals("R")){
            for(int i = 0; i < numOfTickets; i++){
                System.out.println("Enter your age for ticket  #" + (i + 1) + " : ");
                age = scanner.nextInt();
                if(ageRestricted(age,rate)){
                    System.out.println("Age Restricted");
                    restricted = true;
                    break;

                }
                else{
                    totalCost += getPrice(age);
                }
            }
            if(restricted == false) {
                System.out.println("The total price is " + totalCost);
            }
        }


    }
}