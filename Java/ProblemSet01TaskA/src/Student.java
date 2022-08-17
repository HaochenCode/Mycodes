/*C343 / Summer 2022
2022-5-16
Haochen Sun
haocsun*/
public class Student {
    private String name;
    private String department;

    public Student(String name){
        this.name = name;
    }

    public void setDepartment(String de){
        department = de;
    }

    public void Display(){
        System.out.println(name + " is in " + department);
    }


}
