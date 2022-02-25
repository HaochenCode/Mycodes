import java.util.ArrayList;

public class BlackComputerScientists {
    private String name;
    private ArrayList<String> researchArea = new ArrayList<String>();
    private String contribution;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public ArrayList<String> getResearchArea(){
        return researchArea;
    }
    public void setResearchArea(ArrayList<String> researchArea){
        this.researchArea=researchArea;
    }

    public String getContribution(){
        return contribution;
    }
    public void setContribution(String contribution){
        this.contribution=contribution;
    }




}
