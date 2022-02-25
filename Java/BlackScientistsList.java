import java.util.ArrayList;

public class BlackScientistsList {
    private ArrayList<BlackComputerScientists> list = new ArrayList<BlackComputerScientists>();
    public void addBlackScientist(BlackComputerScientists n){
        list.add(n);
    }
    public String researchArea(String area){
        String temp ="";
        int counter = 0;
        for (BlackComputerScientists a : list) {
            if(a.getResearchArea().contains(area)){
                temp = temp + a.getName() + " ";
            }
            counter++;
        }
        return temp;
    }

    public String researchContribution (String word){
        String temp ="";
        for (BlackComputerScientists a : list) {
            if(a.getContribution().contains(word)){
                temp = temp + a.getName() + " ";
            }
        }
        return temp;
    }

}
