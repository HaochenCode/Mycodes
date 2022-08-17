import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class editDistance {
    private int Minimum (int a, int b, int c) {
        int mi;

        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }
        return mi;

    }

    public int LD (String s, String t) {
        long lTimeBefore = System.nanoTime();
        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1

        n = s.length ();
        m = t.length ();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n+1][m+1];

        // Step 2

        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3

        for (i = 1; i <= n; i++) {

            s_i = s.charAt (i - 1);

            // Step 4

            for (j = 1; j <= m; j++) {

                t_j = t.charAt (j - 1);

                // Step 5

                if (s_i == t_j) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }

                // Step 6

                d[i][j] = Minimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);

            }

        }

        // Step 7

        long lTimeAfter = System.nanoTime();
        long lElapsedNanoSeconds = (lTimeAfter - lTimeBefore);
        System.out.println("Time elapsed: " + lElapsedNanoSeconds);
        return d[n][m];

    }

    public static int computeHammingDistance(String lDNA, String rDNA){
        long lTimeBefore = System.nanoTime();
        int distance = 0;
        for(int i = 0 ; i < lDNA.length(); i++){
            if(lDNA.charAt(i) != rDNA.charAt(i)){
                distance++;
            }
        }
        long lTimeAfter = System.nanoTime();
        long lElapsedNanoSeconds = (lTimeAfter - lTimeBefore);
        System.out.println("Time elapsed: " + lElapsedNanoSeconds);
        return distance;
    }


    public static void main(String[] args) throws Exception {
        File txt1 = new File("/Users/sunhaochen/IdeaProjects/lab13/src/pg97.txt");
        Scanner in1 = new Scanner(txt1);
        String text1 = "";
        while (in1.hasNextLine()){
            text1 += in1.nextLine();
        }
        in1.close();


        File txt2 = new File("/Users/sunhaochen/IdeaProjects/lab13/src/pg201.txt");
        Scanner in2 = new Scanner(txt2);
        String text2 = "";
        while (in2.hasNextLine()){
            text2 += in2.nextLine();
        }
        in2.close();


        // Test for Edit Distance and Hamming Distance
        editDistance ed1 = new editDistance();
        System.out.println("Edit Distance is : " + ed1.LD(text1,text2));
        System.out.println("Hamming Distance is : " + editDistance.computeHammingDistance(text1,text2));

        // Test for RandomDNA sequences
        RandomDNA rdm = new RandomDNA();
        boolean crush = false;
        int n = 4;

        while(!crush && n < 20000){
            try {
                String str1 = rdm.generateDNAstring(n);
                String str2 = rdm.generateDNAstring(n);

                System.out.println("For n = " + n);
                System.out.println("Edit Distance : " + ed1.LD(str1,str2));
                System.out.println("Hamming Distance : " + editDistance.computeHammingDistance(str1,str2));
                n *= 2;
            }
            catch (Exception e){
                crush = true;
                System.out.println("Program crashed, with n = " + n);
            }

        }
    }



}
