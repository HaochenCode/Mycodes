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

    public static void main(String[] args) {

        // First Black Computer scientist
        BlackComputerScientists scientists1 = new BlackComputerScientists();
        scientists1.setName("Katherine Johnson");
        ArrayList<String> con1 = new ArrayList<String>();
        con1.add("Military");
        con1.add("Satellite");
        scientists1.setResearchArea(con1);
        scientists1.setContribution("She was the key to the success of Apollo 11 mission.");

        //Second one
        BlackComputerScientists scientists2 = new BlackComputerScientists();
        scientists2.setName("Evelyn Granville");
        ArrayList<String> con2 = new ArrayList<String>();
        con2.add("Mathematics");
        con2.add("Software Engineering");
        scientists2.setResearchArea(con2);
        scientists2.setContribution("He worked for NASA's Apollo space program including digital computer techs.");

        //Third one
        BlackComputerScientists scientists3 = new BlackComputerScientists();
        scientists3.setName("Roy Clay");
        ArrayList<String> con3 = new ArrayList<String>();
        con3.add("Radiation");
        con3.add("Computer Marketing");
        scientists3.setResearchArea(con3);
        scientists3.setContribution("He inducted into Silicon Valley Engineering Council's Hall of Fame in 2003.");

        //Fourth one
        BlackComputerScientists scientists4 = new BlackComputerScientists();
        scientists4.setName("Clarence Ellis");
        ArrayList<String> con4 = new ArrayList<String>();
        con4.add("GUI");
        con4.add("OOP");
        scientists4.setResearchArea(con4);
        scientists4.setContribution("He was the first black named to the Association of Computer Machinery.");

        //Fifth one
        BlackComputerScientists scientists5 = new BlackComputerScientists();
        scientists5.setName("Mark Dean");
        ArrayList<String> con5 = new ArrayList<String>();
        con5.add("Hardware");
        con5.add("Architecture");
        scientists5.setResearchArea(con5);
        scientists5.setContribution("Contributed to building PC monitor and the first gigahertz chip.");

        BlackScientistsList l1 = new BlackScientistsList();
        l1.addBlackScientist(scientists1);
        l1.addBlackScientist(scientists2);
        l1.addBlackScientist(scientists3);
        l1.addBlackScientist(scientists4);
        l1.addBlackScientist(scientists5);

        System.out.println(l1.researchContribution("PC monitor"));
        System.out.println(l1.researchArea("GUI"));

    }

}
