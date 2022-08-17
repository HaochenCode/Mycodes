/*C343 / Summer 2022
2022-5-16
Haochen Sun
haocsun*/

public class DNAHammingDistance {

    public static int computeHammingDistance(String lDNA, String rDNA){
        int distance = 0;
        for(int i = 0 ; i < lDNA.length(); i++){
            if(lDNA.charAt(i) != rDNA.charAt(i)){
                distance++;
            }
        }
        return distance;
    }

}
