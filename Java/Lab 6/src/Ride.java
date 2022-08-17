public class Ride {
    private String rideName;

    public String getRideName() { return rideName; }
    public void setRideName(String rideName) { this.rideName = rideName; }

    public Ride(String name){rideName = name;}

    public void printRider(Customer customer){
        System.out.println(customer.getName() + " rides a " + rideName);
    }
}
