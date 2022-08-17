// C343/Summer 2022
// Problem Set 02
// 2022-5-22
// Haochen Sun / haocsun

// Updated 2022-05-31 :
//    removed unnecessary comments about actionPerformed and ActionListener,
//    and updated references to Problem Set 04 and Lab Task 04

// NOTE: when used for Problem Set 04, this PS04starterView starter code class will
//       use the Array2DIntADT class, which was part of Lab Task 04.
//       Use your own implementation of Array2DIntADT from your Lab Task 04 solution,
//       for a test run of this PS04starterView class.


// explicit import of every Java class we use from the AWT package:
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.Random;


// explicit import of every Java class we use from the Swing package:
import javax.swing.JComponent;
import javax.swing.JFrame;


// this PS04starterView Java class will:
//   extend JComponent to draw into a Graphics object, and
//   periodically repaint the Graphics object.
public class PS04starterView extends JComponent {

    static final int H_SIZE = 1024;
    static final int V_SIZE = 512;

    // TODO: for Problem Set 04,
    //       you will draw the content of a 2D array of integers,
    //       where each integer value in the array should be visualized
    //       as explained in Problem Set 04 instructions:
    // https://homes.luddy.indiana.edu/classes/summer2022/csci/c343-mitja/2022/problems/ps-04-c343-Summer-2022.html#taskD
    //       for simplicity, here we just consider that array to be named 'pixels':


    // a counter, just to animate something for testing the PS04starterView demo code...
    int repaintCounter = 0;
    int[][] pixels = new int[H_SIZE][V_SIZE];

    // this is where we would get data from the Controller,
    //   (as provided to the Controller by the Model)
    //   and call this.repaint() whenever the data needs to be visualized
    public void getDataAndVisualizeIt() {
        System.out.println("here is getDataAndVisualizeIt() for PS04starterView");

        // ...do whatever you need to get the data...

        // tell the JComponent that it ought to repaint itself:

        Random rdm = new Random();
        for(int i = 0; i < pixels.length;i++) {
            for(int j = 0 ; j <pixels[0].length;j++){
                pixels[i][j] = rdm.nextInt(510) - 255;
                this.repaint();
                try {
                    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // now having called this.repaint(), the Java AWT/Swing runtime will
        //    cause a call to paintComponent() as soon as it can.

    } // end of getDataAndVisualizeIt()


    // we override the JComponent's paintComponent() method, to do some drawing.
    //  ( Warning: never call paintComponent() directly! )
    // the paintComponent() method receives the currently active Graphics for our JComponent:
    public void paintComponent(Graphics pGraphics) {
        System.out.println("here is paintComponent() for PS04starterView");
        //
        // "The Graphics class is the abstract base class for all graphics contexts
        //   that allow an application to draw onto components that are realized on
        //   various devices, as well as onto off-screen images."
        // https://docs.oracle.com/en/java/javase/16/docs/api/java.desktop/java/awt/Graphics.html
        super.paintComponent(pGraphics);

        // clear the background at every re-painting:


        // set paint Color for drawing content, using the constructor with RGB int values:
        // https://docs.oracle.com/en/java/javase/16/docs/api/java.desktop/java/awt/Color.html
        Color lForegroundColor = new Color(0,0,0);
        pGraphics.setColor(lForegroundColor);

        // just for testing purposes,
        //    let's draw a diagonal line across the Graphics context:
        int x, y;
        for (x=0; x<pixels.length; x++) {
            for(y = 0; y < pixels[0].length;y++){
                if(pixels[x][y] <= 0 )
                    lForegroundColor = new Color(-(pixels[x][y]),0,0);
                else
                    lForegroundColor = new Color(0,pixels[x][y],0);
                pGraphics.setColor(lForegroundColor);
                pGraphics.fillRect(x,y,1,1);


            }
        }

        // keep track of how many times paintComponent() was called:
        this.repaintCounter ++;
    } // end of paintComponent()



    // client code - main() method for testing this demo PS04starterView Class:
    public static void main(String[] args) {
        System.out.println("here is main() for PS04starterView");

        // instantiate the main JComponent, i.e. "this" Java class:
        PS04starterView theMainJComponent = new PS04starterView();

        // create JFrame where we (the main object in its JComponent identity) can paint:
        JFrame aJFrame = new JFrame();
        aJFrame.add(theMainJComponent);
        aJFrame.setSize(H_SIZE, V_SIZE);
        aJFrame.setVisible( true );

        System.out.println("main() is about to draw a lot of times...");
        // re-paint the JComponent a few times:
        for (int i=0; i< (512*512); i++) {
            // call our getDataAndVisualizeIt() method,
            //    which will (for PS04, get data, then) call JComponent's repaint()
            theMainJComponent.getDataAndVisualizeIt();
            // pause for 10 milliseconds, so that the redisplay
            // is not too fast for this demo:
            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("... main() is done drawing a lot of times.");
    } // end of main()


} // end of class PS04starterView()
