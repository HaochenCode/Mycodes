import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PatientRecord {
    private static class Patient {
        private String name = "";
        private boolean isSick = false;
        private int birthDay = 1, birthMonth = 1, birthYear = 1900;

        public String getName() { return name; }
        public boolean getIsSick() { return isSick; }
        public int getBirthDay() { return birthDay; }
        public int getBirthMonth() { return birthMonth; }
        public int getBirthYear() { return birthYear; }

        public Patient(String name, boolean isSick, int day, int month, int year){
            this.name = name;
            this.isSick = isSick;
            birthDay = day;
            birthMonth = month;
            birthYear = year;
        }

        @Override public String toString(){
            String birthStr = "";
            if(birthMonth < 10){
                birthStr += "0" + birthMonth;
            } else {
                birthStr += birthMonth;
            }
            birthStr += "/";
            if(birthDay < 10){
                birthStr += "0" + birthDay;
            } else {
                birthStr += birthDay;
            }
            birthStr += "/" + birthYear;
            return name + " " + birthStr + (isSick ? " sick" : " recover");
        }
    }

    private static final int currentYear = 2022;

    public static String isBirthValid(int day, int month, int year){
        String date = "";
        if(day < 1 || day > 31){
            date = "Invalid input";
        }
        else if(month < 1 || month > 12){
            date = "Invalid input";
        }
        else if(year < 1900 || year > 2022){
            date = "Invalid input";
        }
        else{
            date = month + "/" + day + "/" + year;
        }
        return date;
    }

    public static void addPatient(String name, String Birth, String fileName) throws IOException {
        if(isBirthValid(Integer.parseInt(Birth.substring(3,5)),Integer.parseInt(Birth.substring(0,2)),Integer.parseInt(Birth.substring(6))).equals("Invalid input")) {
            System.out.println("The birth date is not valid, fail to add the patient.");
        }
        File inputFile = new File(fileName);
        Scanner in = new Scanner(inputFile);
        ArrayList<String> s = new ArrayList<>();
        while (in.hasNextLine()){
            String line = in.nextLine();
            s.add(line);
        }
        in.close();
        s.add(name + " " + Birth + " " + "sick");

        PrintWriter out = new PrintWriter(fileName);
        for(int i = 0; i < s.size(); i++){
            out.println(s.get(i));
        }
        out.close();
    }


    public static void deletePatient(String name, String fileName) throws IOException{
        File inputFile = new File(fileName);
        Scanner in = new Scanner(inputFile);
        ArrayList<String> temp = new ArrayList<>();
        while(in.hasNextLine()){
            String line = in.nextLine();
            temp.add(line);
        }
        in.close();

        for(int i = 0; i < temp.size();i++){
            if(temp.get(i).contains(name)){
                if(temp.get(i).contains("sick")){
                    System.out.println("Fail to remove sick patient.");
                }
                else if(temp.get(i).contains("recover")){
                    temp.remove(i);
                    break;
                }
            }
        }

        PrintWriter o = new PrintWriter(fileName);
        for(int i = 0; i < temp.size(); i++){
            o.println(temp.get(i));
        }
        o.close();

    }

    public static int countPatients(String status, String fileName) throws FileNotFoundException {
        ArrayList<Patient> patients = readPatients(fileName);
        System.out.println(patients.size());
        int count = 0;
        boolean targetIsSick = status.equals("sick");
        for(Patient p : patients){
            if(p.getIsSick() == targetIsSick || status.equals("")){
                count++;
            }
        }
        return count;
    }

    public static int averageAge(String fileName) throws FileNotFoundException {
        ArrayList<Patient> patients = readPatients(fileName);
        int totalAge = 0;
        for(Patient p : patients){
            totalAge += currentYear - p.birthYear;
        }
        return totalAge / patients.size();
    }

    public static void sortPatientsByAge(String fileName) throws FileNotFoundException {
        ArrayList<Patient> patients = readPatients(fileName);
        for(int i = 0; i < patients.size(); i++){
            int max = i;
            for(int j = i + 1; j < patients.size(); j++){
                if(patients.get(j).getBirthYear() > patients.get(max).getBirthYear()){
                    max = j;
                }
            }
            if(max != i){
                Patient temp = patients.get(max);
                patients.set(max, patients.get(i));
                patients.set(i, temp);
            }
        }
        writePatients(fileName, patients);
    }

    public static void sortPatientsByName (String fileName) throws FileNotFoundException{
        ArrayList<Patient> patients = readPatients(fileName);
        for(int i = 0; i < patients.size(); i++){
            int max = i;
            for(int j = i + 1; j < patients.size(); j++){
                if(patients.get(j).getName().compareToIgnoreCase(patients.get(max).getName()) < 0){
                    max = j;
                }
            }
            if(max != i){
                Patient temp = patients.get(max);
                patients.set(max, patients.get(i));
                patients.set(i, temp);
            }
        }
        writePatients(fileName, patients);
    }

    public static void shufflePatients (String fileName) throws FileNotFoundException{
        ArrayList<Patient> patientList = readPatients(fileName);
        ArrayList<Patient> result = new ArrayList<Patient>();
        for(int i = 0; i < patientList.size(); i++){
            result.add(null);
        }
        Random random = new Random();
        for(Patient p : patientList){
            int index = random.nextInt(patientList.size());
            while(result.get(index) != null){
                index = random.nextInt(patientList.size());
            }
            result.set(index, p);
        }
        writePatients(fileName, result);
    }

    private static ArrayList<Patient> readPatients(String fileName) throws FileNotFoundException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String name = "";
            boolean isSick = false;
            int day = 0, month = 0, year = 0;
            try{
                String[] strs = scanner.nextLine().split("\s+");
                name = strs[0] + " " + strs[1];
                if(strs[3].equals("sick")) {
                    isSick = true;
                }
                String[] birthStrs = strs[2].split("/");
                day = Integer.parseInt(birthStrs[1]);
                month = Integer.parseInt(birthStrs[0]);
                year = Integer.parseInt(birthStrs[2]);

            }catch(Exception e){ continue; }
            list.add(new Patient(name, isSick, day, month, year));
        }
        scanner.close();
        return list;
    }

    private static void writePatients(String fileName, ArrayList<Patient> patientList) throws FileNotFoundException {
        File file = new File(fileName);
        PrintWriter writer = new PrintWriter(file);
        for(Patient p : patientList){
            writer.println(p);
        }
        writer.println();
        writer.close();
    }




    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("Welcome to the patient management system, please choose from the following: ");
            System.out.println("1. Add a patient to the file.");
            System.out.println("2. Delete a patient record.");
            System.out.println("3. Count the total number of sick and recovered patients.");
            System.out.println("4. Find average age for patients.");
            System.out.println("5. Sort patients by age.");
            System.out.println("6. Sort patients by first or last name.");
            System.out.println("7. Shuffle patients.");
            System.out.println("8. Exit.");
            int choice = 0;
            choice = in.nextInt();
            in.nextLine();

            if(choice == 1){
                System.out.println("Please input the patient's name: ");
                String name = in.nextLine();
                System.out.println("Please input the patient's birthday: ");
                String birthStr = in.nextLine();
                int day = 0, month = 0, year = 0;
                try{
                    day = Integer.parseInt(birthStr.substring(3, 5));
                    month = Integer.parseInt(birthStr.substring(0, 2));
                    year = Integer.parseInt(birthStr.substring(6));
                }catch(Exception e){
                    System.out.println("Invalid format");
                    continue;
                }
                if(isBirthValid(day, month, year).equals("Invalid input")){
                    System.out.println("Invalid input");
                    continue;
                }
                addPatient(name, birthStr, "patients.txt");
            }
            else if (choice == 2){
                System.out.println("Please input the patient's name: ");
                String name = in.nextLine();
                deletePatient(name, "patients.txt");
            }
            else if (choice == 3){
                System.out.println("Please input the status: ");
                String status = in.nextLine().toLowerCase();
                if(!status.equals("sick") && !status.equals("recover") && !status.equals("")){
                    System.out.println("Invalid input");
                    continue;
                }
                int count = countPatients(status, "patients.txt");
                System.out.println("Count is " + count);
            }
            else if (choice == 4){
                System.out.println("Average age is " + averageAge("patients.txt"));
            }
            else if (choice == 5){
                sortPatientsByAge("patients.txt");
                System.out.println("Sorted successfully");
            }
            else if (choice == 6){
                sortPatientsByName("patients.txt");
                System.out.println("Sorted successfully");
            }
            else if (choice == 7){
                shufflePatients("patients.txt");
                System.out.println("shuffled successfully");
            }
            else if (choice == 8){
                System.out.println("Exited");
                break;
            }
            else{
                System.out.println("Invalid Input, please enter an integer between 1 to 8: ");
            }

            System.out.println("Press enter to continue...");
            in.nextLine();
        }
        in.close();
    }
}
