import java.util.ArrayList;

public class ThemePark {
    // Create instance variables
    private ArrayList<Customer> customerList = new ArrayList<Customer>();
    private ArrayList<Employee> employeeList = new ArrayList<Employee>();
    private ArrayList<Facility> facilityList = new ArrayList<Facility>();
    private ArrayList<Ride> rideList = new ArrayList<Ride>();

    // add customer to the Arraylist
    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    // add facility to the Arraylist
    public void addFacility(Facility facility) {
        facilityList.add(facility);
    }

    // add ride to the Arraylist
    public void addRide(Ride ride) {
        rideList.add(ride);
    }

    // hire an employee with a given name
    public void hire(String name){
        employeeList.add(new Employee(name, true));
    }

    // remove the fired employee and print his name
    public void fire(String name){
        for (Employee e : employeeList) {
            if(e.getName().equals(name)){
                if (!e.getStatus())
                    continue;
                e.setStatus(false);
                break;
            }
        }
    }

    // getter for the ride
    public Ride getRide(String rideName) {
        for (Ride r : rideList) {
            if (r.getRideName().equals(rideName)) {
                return r;
            }
        }
        return null;
    }

    // Get a customer of the given name
    public Customer getCustomer(String customerName) {
        for (Customer c : customerList) {
            if (c.getName().equals(customerName)) {
                return c;
            }
        }
        return null;
    }

    // Get a facility of the given name
    public Facility getFacility(String facilityName) {
        for (Facility f : facilityList) {
            if (f.getName().equals(facilityName)) {
                return f;
            }
        }
        return null;
    }

    // Print all the stats of the park
    public void printStatistics() {
        String str = "Customers: \n";
        for (Customer c : customerList) {
            str += c + "\n";
        }
        str += "Employees: \n";
        for (Employee e : employeeList) {
            str += e + "\n";
        }
        str += "Facility: \n";
        for (Facility f : facilityList) {
            str += f.getName() + "\n";
        }
        str += "Ride: \n";
        for (Ride r : rideList) {
            str += r.getRideName() + "\n";
        }
        System.out.println(str);
    }
}


