// A subclass for the Vehicle class
public class Car extends Vehicle{
    // Instance Variables
    private String bodyStyle;
    private int topSpeed;
    private int currSpeed;

    // Constructor for this subclass that initializes an object
    public Car(String make, String color, int year, String model, String bodyStyle, int topSpeed, int currSpeed) {
        super(make, color, year, model);
        this.bodyStyle = bodyStyle;
        this.currSpeed = currSpeed;
        this.topSpeed = topSpeed;
    }

    // Getters and setters for the private variables
    public String getBodyStyle(){ return bodyStyle;}
    public void setBodyStyle(String bodyStyle) { this.bodyStyle = bodyStyle;}

    public int getTopSpeed(){ return topSpeed;}
    public void setTopSpeed(int topSpeed){ this.topSpeed = topSpeed;}

    public int getCurrSpeed(){ return currSpeed;}
    public void setCurrSpeed(int currSpeed){ this.currSpeed = currSpeed;}

    // accelerate function that returns the sum of current speed and top speed
    public int accelerate(){ return (currSpeed + topSpeed);}

    // Display method that print all the info about a Car object
    public void carDetails(){
        printDetails();
        System.out.println("The body style is : " + getBodyStyle());
        System.out.println("The accelerated speed is : " + accelerate());
    }

    // Main method to create object in Car class and display the car info
    public static void main(String[] args) {
        Car car1 = new Car("BMW", "Silver", 2020,"M4","Race",160, 40);
        car1.carDetails();
    }
}
