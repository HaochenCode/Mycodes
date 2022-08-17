// The base class
public class Vehicle {
    // Instance variables
    private String make;
    private String color;
    private int year;
    private String model;

    // Constructor
    public Vehicle(String make, String color, int year, String model){
        this.make = make;
        this.color = color;
        this.year = year;
        this.model = model;
    }

    // Getters and setters for the private variables
    public String getMake(){ return make;}
    public void setMake(String make){ this.make = make;}

    public String getColor(){ return color;}
    public void setColor(String color){ this.color = color;}

    public int getYear(){ return year;}
    public void setYear(int year){ this.year = year;}

    public String getModel(){ return model;}
    public void setModel(String model){ this.model = model;}

    // Print method that prints all the info about the object
    public void printDetails(){
        System.out.println("The make is : "+ getMake());
        System.out.println("The color is : "+ getColor());
        System.out.println("The year is : "+ getYear());
        System.out.println("The model is : "+ getModel());
    }
}

/*
Answer to the questions:
a. Single, Multiple, Multilevel,and Hybrid.
b. Super is keyword that a subclass class can call the function of its superclass, including constructors. A final class
   cannot be extended to subclasses; final methods cannot be overridden; final variable cannot be modified, and has to
   be initialized.
c. Since the compiler can't decide which method to inherit from and it will be an error. To prevent ambiguity and
   prevent the deadly diamond problem, Java doesn't support the multiple inheritance.
 */