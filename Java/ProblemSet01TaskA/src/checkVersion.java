/*C343 / Summer 2022
2022-5-16
Haochen Sun
haocsun*/
public class checkVersion {
    public static void main(String[] args) {
        System.out.println("java.vm.version is " + System.getProperty("java.vm.version"));
        System.out.println("java.vm.vendor is " + System.getProperty("java.vm.vendor"));
        System.out.println("java.vm.name is " + System.getProperty("java.vm.name"));
        System.out.println("java.vm.specification.version is " + System.getProperty("java.vm.specification.version"));
        System.out.println("java.vm.specification.vendor is " + System.getProperty("java.vm.specification.vendor"));
        System.out.println("java.vm.specification.name is " + System.getProperty("java.vm.specification.name"));
        System.out.println("java.version is " +  System.getProperty("java.version"));
        System.out.println("java.vendor is " + System.getProperty("java.vendor"));


        // Initialize the first student
        String name = "Sam";
        Student stu1 = new Student(name);
        stu1.setDepartment("CSCI");

        // Create and initialize an array of students
        Student[] studentList = new Student[3];
        studentList[0] = stu1;
        studentList[1] = new Student("John");
        studentList[1].setDepartment("Music");
        studentList[2] = new Student("Susan");
        studentList[2].setDepartment("Info");

        // Display all students' info
        for(Student s : studentList){
            s.Display();
        }

    }



}
