import java.util.Random;
import java.util.Scanner;

/**
 * In this part of the lab you will use your newfound debugging skills to fix this program that won't seem to run.
 * The class below, Person, is a class that contains a few common methods such as getters and setters and a couple of
 * others.
 *
 * At the bottom of this file, you will see another class called PersonMain. This class only has two methods: a main
 * method and a helper function (see the method itself for details). The main method does not contain any errors,
 * and its sole purpose is to be able to see if the program can run without issues.
 *
 * The only place that is exempt from bugs is the main method at the bottom of this file.
 * You might find bugs anywhere else in the code.
 */




public class Person {
    /**
     * Instance variables for objects of type "Person." Each Person will have a name and an age.
     */
    private String name;
    private int age;


    /**
     * Empty constructor. Creates a new Person object, but does not set the name or age of this person.
     */
    public Person() {}

    /**
     * Public constructor for the Person class. Called when creating a new Person object.
     * Takes in the person's name and age as a String and an integer, respectively, and
     * then creates an instance of that Person object.
     */
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    /**
     * Returns the name of this person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this person.
     */
    // Change to this.name
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age of this person.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of this person.
     */
    //Change to this.age
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns true if the two people have the same name, false if they have different names.
     */
    public boolean hasSameName(Person person){
        //Change to Strings.equals member function rather than ==
        if(person.getName().equals(this.getName())){
            return true;
        } else {
            return false;
        }
    }

    public boolean hasSameAge(Person person){
        if(this.getAge() == person.getAge()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "[Name: " + this.name + ", Age: " + this.age + "]";
    }

}

class PersonMain {

    /**
     * This method should generate and return an array (of size sizeOfArray) of people in which each person's name is
     *  generated randomly.
     *
     * There are 5 names that a person could have: "Gloria", "Alberto", "Clint", "Marie",
     *  or "Dr. Phil" (The fifth name can be anything that you want it to be, but Dr. Phil is a good fifth name).
     *
     * After generating the name of each person, the method will then give them an age (from 1 to 90) based on another
     *  randomly-generated integer.
     */
    public static Person[] makeListOfPeople(int sizeOfArray){
        Person[] myPeople = new Person[sizeOfArray];

        Random r = new Random();

        for(int i=0; i<sizeOfArray ;i++){
            Person newPerson = new Person();
            int rand = r.nextInt(5);

            if(rand == 0){
                newPerson.setName("Gloria");
            } else if(rand == 1){
                newPerson.setName("Alberto");
            } else if(rand == 2){
                newPerson.setName("Clint");
            } else if(rand == 3){
                newPerson.setName("Marie");
            }
            // Add an else case for the if statement
            else newPerson.setName("Dr. Phil");

            int age = r.nextInt(90) + 1;

            newPerson.setAge(age);

            // change rand to i
            myPeople[i] = newPerson;
        }
        return myPeople;
    }




    /*
     TODO IMPORTANT NOTE: THERE ARE NO BUGS IN THE MAIN METHOD.
    */


    /**
     * This is the method that you will actually run to test if your code is working correctly.
     * As long as you can reach the end of this method without producing any exceptions, your code works correctly.
     *
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("What size of array do you want to generate?");
        int size = s.nextInt();

        Person[] myList = makeListOfPeople(size);

        System.out.println("Printing list of people:");

        for (int i = 0; i < myList.length ; i++) {
            System.out.println("Person #" + (i + 1) + ": " + myList[i].toString());
        }

        System.out.println();

        System.out.println("Now we must select a CHOSEN ONE. Who shall it be? (Enter the person's number)");
        System.out.println("Don't ask what happens to the CHOSEN ONE.");

        int whoToRemove = s.nextInt() - 1;

        Person chosenOne = new Person();

        if(whoToRemove >= 0 && whoToRemove < size){
            chosenOne = myList[whoToRemove];
        } else {
            boolean fourthWallBroken = false;
            int counter = 0;
            while (whoToRemove < 0 || whoToRemove >= size) {
                if (counter == 3) {
                    fourthWallBroken = true;
                    System.out.println("Now you are the CHOSEN ONE. What is your name?");
                    String name = s.next();
                    System.out.println("Very good. And your age?");
                    int age = s.nextInt();
                    chosenOne = new Person(name, age);
                } else if (counter == 2) {
                    System.out.println("You are JUnit testing my patience. This is your last chance. Who shall it be?");
                } else {
                    System.out.println("Invalid choice. There must be a CHOSEN ONE. Try again.");
                    whoToRemove = s.nextInt() - 1;
                }
                counter++;
            }
            if(!fourthWallBroken){chosenOne = myList[whoToRemove];}
        }

        System.out.println("Behold, THE CHOSEN ONE!!");
        System.out.println(chosenOne.toString());

        Person inputPerson = new Person();
        System.out.println("Now, for your final test: What is the CHOSEN ONE's name?");
        inputPerson.setName(s.next());

        if(chosenOne.hasSameName(inputPerson)){
            System.out.println("Correct...");
        } else {
            System.out.println("You have failed the test. You are certainly not the CHOSEN ONE.");
            return;
        }

        System.out.println("And their age?");
        inputPerson.setAge(s.nextInt());
        if(chosenOne.hasSameAge(inputPerson)){
            System.out.println("You have passed the test...");
        } else {
            System.out.println("You have failed the test. You are certainly not the CHOSEN ONE.");
        }
    }
}