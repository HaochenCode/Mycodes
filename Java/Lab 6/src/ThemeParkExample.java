public class ThemeParkExample {
    // Main method to test all the functions
    public static void main(String[] args) {
        ThemePark park = new ThemePark();

        // add two customers to the theme park
        park.addCustomer(new Customer(50, "Wanda"));
        park.addCustomer(new Customer(30, "Alex"));

        // add two facilities to the theme park
        park.addFacility(new Facility("Ice-cream shop", 1, "Ice-cream"));
        park.addFacility(new Facility("Popcorn shop", 3, "Popcorn"));

        // add two rides to the theme park
        park.addRide(new Ride("Roller-coaster"));
        park.addRide(new Ride("Freefall"));

        // hire two employees
        park.hire("Abby");
        park.hire("Zachary");

        // print the stats
        park.printStatistics();

        // print the riders
        park.getRide("Freefall").printRider(park.getCustomer("Wanda"));
        park.getRide("Roller-coaster").printRider(park.getCustomer("Alex"));

        // fire the employee
        park.fire("Abby");

        System.out.println();
        // let the customers purchase items
        park.getCustomer("Wanda").purchaseItem(park.getFacility("Popcorn shop"));
        park.getCustomer("Wanda").purchaseItem(park.getFacility("Ice-cream shop"));
        park.getCustomer("Alex").purchaseItem(park.getFacility("Ice-cream shop"));

        // print the new stats
        park.printStatistics();
    }
}
