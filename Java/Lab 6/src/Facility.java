public class Facility {
    private String name;
    private int cost;
    private String itemName;

    public String getName() { return name; }
    public int getCost() { return cost; }
    public String getItemName() { return itemName; }
    public void setName(String name) { this.name = name; }
    public void setCost(int cost) { this.cost = cost; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Facility(String name, int cost, String itemName) {
        this.name = name;
        this.cost = cost;
        this.itemName = itemName;
    }

    public void purchase(Customer customer){
        System.out.println(customer.getName() + " buys a " + name);
    }
}
