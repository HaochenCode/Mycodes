import java.util.ArrayList;

public class Customer extends Person {
    private int money;
    private ArrayList<String> itemList = new ArrayList<String>();

    public int getMoney() { return money; }
    public ArrayList<String> getItemList() { return itemList; }
    public void setMoney(int money) { this.money = money; }
    public void setItemList(ArrayList<String> itemList) { this.itemList = itemList; }

    public Customer(int money, String name){
        this.money = money;
        this.name = name;
    }

    public void purchaseItem(Facility facility) {
        if (money < facility.getCost())
            return;
        money -= facility.getCost();
        itemList.add(facility.getItemName());
    }

    @Override
    public String toString() {
        String str = "Name: " + name + ", Money: " + money + ", Items: [";
        for (int i = 0; i < itemList.size(); i++) {
            str += itemList.get(i);
            if (i != itemList.size() - 1)
                str += ", ";
        }
        str += "]";
        return str;
    }
}
