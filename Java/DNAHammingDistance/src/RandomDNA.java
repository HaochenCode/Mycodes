/*C343 / Summer 2022
2022-5-16
Haochen Sun
haocsun*/

import java.util.ArrayList;
import java.util.Random;

public class RandomDNA {

    public String generateDNAstring(int n) {
        String lDNA = "";
        Random lRandomizer = new Random();

        ArrayList<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("T");
        letters.add("C");
        letters.add("G");

        for(int i = 0 ;i < n; i++){
            int t = lRandomizer.nextInt(4);
            lDNA += letters.get(t);
        }

        System.out.println(lDNA);

        return lDNA;
    }

    // a test client
    public static void main(String[] argv) {
        RandomDNA myRandomDNA = new RandomDNA();
        String myDNAstring1 = "";
        String myDNAstring2 = "";
        for (int i = 100; i<=300; i+=100) {
            System.out.println("");
            myDNAstring1 = myRandomDNA.generateDNAstring(i);
            myDNAstring2 = myRandomDNA.generateDNAstring(i);
            System.out.println("first DNA sequence " + i + " characters long: " + myDNAstring1);
            System.out.println("second DNA sequence " + i + " characters long: " + myDNAstring2);
            System.out.println("The distance between is : " + DNAHammingDistance.computeHammingDistance(myDNAstring1,myDNAstring2));
        }
        System.out.print(myDNAstring1);
    }

} // end of class RandomDNA
