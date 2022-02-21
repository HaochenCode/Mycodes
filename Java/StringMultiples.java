import java.util.Scanner;

public class StringMultiples {
    public static void main(String[] args) {
        /*
        First, I need to build a new scanner object to read the inputs from user.
        Second, I will write testing cases that test each circumstance.
        Finally, I implemented my static function that determines if the second String is a
        multiple of the first one. Inside this function, I need to use the methods from string class.
         */

        //build a new scanner object
        Scanner scanner = new Scanner(System.in);
        //Let user input the first string
        System.out.println("Enter the first String: ");
        String firstString = scanner.next();
        //Let user input the second string
        System.out.println("Enter the second String: ");
        String secondString = scanner.next();
        //Print the result
        System.out.println((checkIfMultipleOf(firstString, secondString)));

        //check the empty string case for second input
        System.out.println(checkIfMultipleOf("cat", ""));// returns false
        //check the empty string case for first input
        System.out.println(checkIfMultipleOf("", "catcatcat"));// returns false
        //check the multiple string case
        System.out.println(checkIfMultipleOf("cat", "catcatcat"));// returns true
        System.out.println(checkIfMultipleOf("cat", "catdatcat"));// returns false
        //check the equal case
        System.out.println(checkIfMultipleOf("cat", "cat"));// returns true
        //check the capital letter case
        System.out.println(checkIfMultipleOf("cat", "catcatCat"));// returns false


    }
    public static boolean checkIfMultipleOf(String single,
                                            String multiple){
        //Initiate a boolean variable
        boolean bool = false;

        //Test if the multiple or single string is empty
        if (single.isEmpty()||multiple.isEmpty())
            bool = false;
        //Test if the multiple string contains the single one
        else if (multiple.contains(single) == false)
            bool = false;
        //Test if the length of the multiple string is a multiple of the single
        else if(multiple.length()%single.length()!= 0)
            bool = false;
        else{
            //Repeat the single string till it has the same length of the multiple string
            String repeatedSingle= single.repeat(multiple.length()/single.length());
            //Test if the repeated string is equal to the multiple
            if(repeatedSingle.equals(multiple))
                bool = true;
        }

        return bool;



    }
}
