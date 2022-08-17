/**
 *    C343/Summer 2022 - Mitja Hmeljak
 */


// NOTE: this PS04starterView starter code class relies on the Int2DArrayADT class,
//       which was part of Problem Set 02.
//       Use your own implementation of Int2DArrayADT from your Problem Set 02 solution,
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
public class Lab10View extends JComponent {

    static final int H_SIZE = 256;
    static final int V_SIZE = 512;

    // TODO: draw the content of a 2D array of integers,
    //       where each integer value in the array should be represented thus:
    //       from MIN_VALUE to -255 (included) - red Color
    //       from -254 to -1 - red Color at given intensity
    //       at value 0 - black Color
    //       from 1 to 254 - green Color at given intensity
    //       from 255 to MAX_VALUE - green Color
    private int[] pixels;

    public Lab10View(){
        pixels = new int[H_SIZE];
        Random randNum = new Random();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = randNum.nextInt(V_SIZE);
        }
    }

    // a counter, just to animate something...
    int repaintCounter = 0;

    // we have to implement actionPerformed() since we implement the ActionListener interface:
    public void getDataAndVisualizeIt() {
        System.out.println("here is getDataAndVisualizeIt() for PS04starterView");

        // Insertion sort
        for (int i = 1; i < pixels.length; ++i) {
            int key = pixels[i];
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && pixels[j] > key) {
                pixels[j + 1] = pixels[j];
                j = j - 1;
                this.repaint();
                try {
                    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pixels[j + 1] = key;

        }
        // ...do whatever you need to get the data...

        // tell the JComponent that it ought to repaint itself:

    } // end of actionPerformed()


    // we override the JComponent's paintComponent() method, to do some drawing:
    //  and we receive the currently active graphics for our JComponent:
    public void paintComponent(Graphics pGraphics) {
        System.out.println("here is paintComponent() for PS04starterView");
        //
        // "The Graphics class is the abstract base class for all graphics contexts
        //   that allow an application to draw onto components that are realized on\
        //   various devices, as well as onto off-screen images."
        // https://docs.oracle.com/en/java/javase/16/docs/api/java.desktop/java/awt/Graphics.html
        super.paintComponent(pGraphics);

        // clear the background at every re-painting:
        pGraphics.setColor(Color.PINK);
        Shape lClipArea = pGraphics.getClip();
        int lWidth = lClipArea.getBounds().width;
        int lHeight = lClipArea.getBounds().height;
        pGraphics.fillRect(0,0,lWidth,lHeight);

        // set paint color for drawing, using the constructor with RGB int values:
        // https://docs.oracle.com/en/java/javase/16/docs/api/java.desktop/java/awt/Color.html
        Color lForegroundColor = new Color(0,0,0);
        pGraphics.setColor(lForegroundColor);

        // just for testing purposes,
        // let's draw a diagonal line across the Graphics context:

        for (int x=0; x<pixels.length; x++) {
            pGraphics.setColor(lForegroundColor);
            pGraphics.fillRect(x,V_SIZE-pixels[x],1,pixels[x]);
//
        }

        this.repaintCounter ++;
    } // end of paintComponent()


    // client code - main() method:
    public static void main(String[] args) {
        System.out.println("here is main() for PS04starterView");

        // instantiate the main JComponent, i.e. "this" Java class:
        Lab10View theMainJComponent = new Lab10View();

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

        }
        System.out.println("... main() is done drawing a lot of times.");
    } // end of main()


} // end of class PS04starterView()