public class Employee extends Person{
    private boolean status;

    public boolean getStatus() { return status; }
    public void  setStatus(boolean status) { this.status = status; }

    public Employee(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Status: " + (status ? "Hired" : "Fired");
    }
}
