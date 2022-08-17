import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class LuddyDemographics {

    public static  ArrayList<Integer> CS = new ArrayList<Integer>();
    public static  ArrayList<String> CSnames = new ArrayList<String>();
    public static  ArrayList<Integer> CAG = new ArrayList<Integer>();
    public static  ArrayList<String> CAGnames = new ArrayList<String>();
    public static  ArrayList<Integer> DS = new ArrayList<Integer>();
    public static  ArrayList<String> DSnames = new ArrayList<String >();
    public static  ArrayList<Integer> INFO = new ArrayList<Integer>();
    public static  ArrayList<String> INFOnames = new ArrayList<String>();
    public static  ArrayList<Integer> ISE = new ArrayList<Integer>();
    public static  ArrayList<String> ISEnames = new ArrayList<String>();
    public static  ArrayList<Integer> TOTAL = new ArrayList<Integer>();
    public static  ArrayList<String> TOTALnames = new ArrayList<String>();

    public static int csFemale = 121;
    public static int csMale = 657;
    public static int cagFemales = 28;
    public static int cagMales = 51;
    public static int dsFemales = 13;
    public static int dsMales = 50;
    public static int infoFemales = 240;
    public static int infoMales = 766;
    public static int iseFemales = 32;
    public static int iseMales = 131;
    public static int totalFemales = 420;
    public static int totalMales = 1670;
    public static JComboBox majorList;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Luddy Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,500));
        JPanel mainPanel = new JPanel();
        JComboBox comboBox = new JComboBox();
        majorList = comboBox;
        comboBox.addItem("Choose...");
        comboBox.addItem("Computer Science");
        comboBox.addItem("Cybersecurity");
        comboBox.addItem("Data Science");
        comboBox.addItem("Info");
        comboBox.addItem("Intelligence SYSTEM");
        comboBox.addItem("All majors");
        CS.add(csFemale);
        CS.add(csMale);
        CSnames.add("CSfemales");
        CSnames.add("CSmales");
        CAG.add(cagFemales);
        CAG.add(cagMales);
        CAGnames.add("CAGfemalse");
        CAGnames.add("CAGmales");
        DS.add(dsFemales);
        DS.add(dsMales);
        DSnames.add("DSfemalse");
        DSnames.add("DSmales");
        INFO.add(infoFemales);
        INFO.add(infoMales);
        INFOnames.add("INFOfemales");
        INFOnames.add("INFOMales");
        ISE.add(iseFemales);
        ISE.add(iseMales);
        ISEnames.add("ISEfemales");
        ISEnames.add("ISEOMales");
        TOTAL.add(totalFemales);
        TOTAL.add(totalMales);
        TOTALnames.add("TOTALfemales");
        TOTALnames.add("TOTALmales");

        DegreeSelectedListener dsListener = new DegreeSelectedListener();
        comboBox.addItemListener(dsListener);
        JLabel jLabel = new JLabel("Instructions");
        frame.add(jLabel);
        frame.add(mainPanel);
        frame.add(comboBox);
        frame.pack();
        frame.setVisible(true);



    }
    private static class DegreeSelectedListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            String title ="";
            ArrayList<Integer> chosen = TOTAL;
            ArrayList<String> chosenName = TOTALnames;
            StackedChartDisplay l = new StackedChartDisplay();

            if(majorList.getSelectedItem().equals("Computer Science")) {
                    chosen = CS;
                    title = "Computer science";
            }
            else if(majorList.getSelectedItem().equals("Cybersecurity")) {
                chosen = CAG;
                title = "Cybersecurity";
            }
            else if(majorList.getSelectedItem().equals("Data Science")) {
                chosen = DS;
                title = "Data Science";
            }
            else if(majorList.getSelectedItem().equals("Info")) {
                chosen = INFO;
                title = "Info";
            }
            else if(majorList.getSelectedItem().equals("Intelligence SYSTEM")) {
                chosen = ISE;
                title = "Intelligence SYSTEM";
            }
            else if(majorList.getSelectedItem().equals("All majors")) {
                chosen = TOTAL;
                title = "All majors";
            }



            StackedChartDisplay.display(title,TOTALnames,TOTAL);

        }
    }



}

