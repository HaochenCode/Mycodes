import java.util.Random;
import java.util.Scanner;

public class PatternMenu {
    public static void main(String[] args) {
        String choice = "";
        String emptyString = "";
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int randomNum = rand.nextInt(8) + 3;
        System.out.println("The size is " + randomNum);

        while (choice.equals("E") == false) {
            System.out.println("A. Pattern 1");
            System.out.println("B. Pattern 2");
            System.out.println("C. Pattern 3");
            System.out.println("D. Pattern 4");
            System.out.println("E. Exit");

            choice = scanner.nextLine();

            if(choice.equals("A")){
                for (int i = randomNum; i > 0; i-- ){

                    for(int j = 0; j < randomNum - i;j++){
                        System.out.print(" ");
                    }
                    for(int j = i; j > 0; j--){
                        System.out.print("*");
                    }

                    System.out.println("");
                }
            }

            else if(choice.equals("B")){
                for (int i = 0; i < randomNum; i++){
                    for(int j = 0; j < i ; j++){
                        System.out.print(" ");
                    }

                    System.out.print(randomNum);
                    System.out.println("");
                }
                for (int i = 0; i < randomNum -1 ; i++){
                    for(int j = 0; j < (randomNum-2)-i; j++){
                        System.out.print(" ");
                    }
                        System.out.print(randomNum);
                        System.out.println("");
                }
            }

            else if(choice.equals("C")){
                for(int i = 0; i < randomNum + 1; i++){
                    for(int j = 0; j < i; j++){
                        System.out.print(i);
                    }
                    System.out.println("");
                }
            }

            else if(choice.equals("D")){
                for(int i = randomNum; i > 0 ; i--){
                    for(int j = 0; j < i; j++){
                        System.out.print(i);
                    }
                    System.out.println("");
                }
            }

            else if(choice.equals("E")== false){
                System.out.println("Invalid Input, try again: ");
                continue;
            }
        }
    }
}
