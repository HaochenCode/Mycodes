import java.util.Arrays;
import java.util.Scanner;

public class StudentDirectory {
    private int[][] studentInfo = {
            {1, 15, 9, 85},
            {2, 18, 12, 70},
            {3, 16, 10, 90},
            {4, 17, 11, 95}
    };
    public int[][] getStudentInfo() { return studentInfo; }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Minimum Age: ");
        int minimumAge = scanner.nextInt();
        System.out.println("Minimum Year: ");
        int minimumYear = scanner.nextInt();
        System.out.println("Minimum Grade: ");
        int minimumGrade = scanner.nextInt();

        StudentDirectory studentDirectory = new StudentDirectory();
        int[] filteredStudents = studentDirectory.filter(minimumAge, minimumYear, minimumGrade);
        for (int i : filteredStudents) {
            System.out.printf("%d ", i);
        }
    }

    public int[] filter(int minAge, int minYear, int minGrade) {
        int[] temp = new int[studentInfo.length];
        int count = 0;

        for (int i = 0; i < studentInfo.length; i++) {
            int[] student = studentInfo[i];
            if (minAge >= 0) {
                if (student[1] < minAge) {
                    continue;
                }
            }
            if (minYear >= 0) {
                if (student[2] < minYear) {
                    continue;
                }
            }
            if (minGrade >= 0) {
                if (student[3] < minGrade) {
                    continue;
                }
            }
            temp[count] = student[0];
            count++;
        }

        int[] result = Arrays.copyOf(temp,count);


        return result;
    }
}
