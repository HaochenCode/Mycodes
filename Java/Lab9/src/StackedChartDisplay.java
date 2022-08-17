import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StackedChartDisplay {

    public static void display(String title, ArrayList<String> titles, ArrayList<Integer> counts){

        // Set up the frame
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(500,500));
        frame.add(new StackedChart(titles,counts));
        frame.pack();
        frame.setVisible(true);
    }

    private static class StackedChart extends JComponent{

        // instance variables
        private ArrayList<String> titles ;
        private ArrayList<Integer> counts ;

        // Constructor
        public StackedChart(ArrayList<String> titles,ArrayList<Integer> counts){
            this.titles = titles;
            this.counts = counts;
        }

        // Implement the paintComponent function from the interface
        public void paintComponent(Graphics g){
            g.setColor(Color.red);
            int sum = 0;
            int maxHeight = 200;
            ArrayList <Color> colors = new ArrayList<Color>();
            colors.add(Color.yellow);
            colors.add(Color.blue);
            colors.add(Color.red);
            colors.add(Color.green);
            colors.add(Color.orange);
            colors.add(Color.cyan);
            for (int i : counts){
                sum += i;
            }
            // Draw the lines and strings
            int currentY = 200;
            g.setColor(Color.black);
            g.drawString("Favorite Ice Cream Poll",40,170);
            g.drawString("Favorite ice cream",160,415);
            g.drawString("0",35,405);
            g.drawString("20",35,365);
            g.drawString("100",35,205);
            g.drawString("60",35,285);
            g.drawString("40",35,325);
            g.drawString("80",35,245);

            /*
            g.setColor(Color.yellow);
            g.fillRect(70,431,13,9);
            g.setColor(Color.black);
            g.drawString("Vanilla",90,440);

            g.setColor(Color.blue);
            g.fillRect(155,431,13,9);
            g.setColor(Color.black);
            g.drawString("Chocolate",170,440);

            g.setColor(Color.red);
            g.fillRect(250,431,13,9);
            g.setColor(Color.black);
            g.drawString("Strawberry",270,440);*/

            // For loop to draw the boxes
            for(int i = 0; i < titles.size();i++){
                g.setColor(colors.get(i));
                g.fillRect(70 + 89 * i, 431, 13, 9);
                g.setColor(Color.black);
                g.drawString(titles.get(i),90 + 89 * i, 440 );
            }
            g.setColor(Color.black);
            g.drawLine(60,400,400,400);
            g.setColor(Color.lightGray);
            g.drawLine(60,380,400,380);
            g.setColor(Color.gray);
            g.drawLine(60,360,400,360);
            g.setColor(Color.lightGray);
            g.drawLine(60,340,400,340);
            g.setColor(Color.gray);
            g.drawLine(60,320,400,320);
            g.setColor(Color.lightGray);
            g.drawLine(60,300,400,300);
            g.setColor(Color.gray);
            g.drawLine(60,280,400,280);
            g.setColor(Color.lightGray);
            g.drawLine(60,260,400,260);
            g.setColor(Color.gray);
            g.drawLine(60,240,400,240);
            g.setColor(Color.lightGray);
            g.drawLine(60,220,400,220);
            g.setColor(Color.gray);
            g.drawLine(60,200,400,200);


            // Draw the rectangles
            for(int i = 0; i < titles.size();i++){
                g.setColor(colors.get(i));
                g.fillRect(150,currentY,160, (int)(((double)counts.get(i) / (double)sum) * (double)maxHeight));
                currentY = currentY + (int)(((double)counts.get(i) / (double)sum) * (double)maxHeight);
            }
        }
    }

    public static void main(String[] args) {
        // Draw the PollTracking chart
        String title = "Graph";
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Integer> counts = new ArrayList<Integer>();
        titles.add("Vanilla");
        titles.add("Chocolate");
        titles.add("Strawberry");
        titles.add("banana");
        counts.add(50);
        counts.add(30);
        counts.add(20);
        counts.add(40);
        display(title,titles,counts);

        // Draw the LuddyDemo graph
       // String title2 = "Luddy Demo";
       // ArrayList<String> genders = new ArrayList<String>();
       // ArrayList<Integer> numPeople = new ArrayList<Integer>();
       // genders.add("female");
       // genders.add("male");
       // counts.add(50);
       // counts.add(30);
       //display(title2,genders,numPeople);
    }
}

