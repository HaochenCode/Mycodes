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
}
