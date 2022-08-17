import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PollTracking {

    private static ArrayList<String> titles = new ArrayList<String>();
    private static ArrayList<Integer> votes = new ArrayList<Integer>();
    private static JFrame frame = null;
    private static JTextField textField = null;
    private static JLabel pollOption1Label = null;
    private static JLabel pollOption2Label = null;
    private static JLabel pollOtherLabel = null;

    public static void main(String[] args) {
        titles.add("Vanilla");
        votes.add(0);
        titles.add("Chocolate");
        votes.add(0);


        frame = new JFrame("Favorite ice cream");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // JPanels
        JPanel mainPanel = new JPanel();
        JPanel votingPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel createGraphPanel = new JPanel();

        // Set layout
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setPreferredSize(new Dimension(175, 100));

        // add all panels to the mainPanel
        mainPanel.add(votingPanel);
        mainPanel.add(labelPanel);
        mainPanel.add(createGraphPanel);

        // Buttons
        JButton button1 = new JButton();
        button1.setSize(100, 100);
        button1.setText("Vanilla");
        button1.addActionListener(new Option1ButtonListener());
        votingPanel.add(button1);

        JButton button2 = new JButton();
        button2.setSize(100, 100);
        button2.setText("Chocolate");
        button2.addActionListener(new Option2ButtonListener());
        votingPanel.add(button2);

        // JTextField
        textField = new JTextField();
        textField.setColumns(10);
        textField.addActionListener(new OptionOtherButtonListener());
        votingPanel.add(textField);

        // JLabels
        pollOption1Label = new JLabel("Vanilla");
        pollOption2Label = new JLabel("Chocolate");
        pollOtherLabel = new JLabel("Other");

        labelPanel.add(pollOption1Label);
        labelPanel.add(pollOption2Label);
        labelPanel.add(pollOtherLabel);


        frame.add(mainPanel);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);

       // LuddyDemographics luddyDemographics = new LuddyDemographics();
    }

    private static void repaint() {
        frame.repaint();
    }

    private static class Option1ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            votes.set(0, votes.get(0) + 1);
            pollOption1Label.setText("Vanilla:  " + votes.get(0));
            repaint();
        }
    }

    private static class Option2ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            votes.set(1, votes.get(1) + 1);
            pollOption2Label.setText("Chocolate:  " + votes.get(1));
            repaint();
        }
    }

    private static class OptionOtherButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (textField.getText().equals(""))
                return;

            boolean found = false;
            for(int i = 0; i < titles.size();i++){
                if(titles.get(i).equals(textField.getText())){
                    votes.set(i,votes.get(i) + 1);
                    found = true;
                }
            }
            if (!found){
                titles.add(textField.getText());
                votes.add(1);
            }

            String str = "";
            for (int i = 2; i < titles.size(); i++) {
                str += titles.get(i) + ": " + votes.get(i) + "   ";
            }
            pollOtherLabel.setText(str);

            textField.setText("");

            repaint();
        }
    }


}
