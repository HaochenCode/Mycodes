import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        PS04Model subject = new PS04Model(512,512);
        subject.randomize();
        PS04View theMainJComponent = new PS04View(subject);
        PS04Controller observer = new PS04Controller(subject, theMainJComponent);
        JFrame aJFrame = new JFrame();
        aJFrame.add(theMainJComponent);
        aJFrame.setSize(512, 512);
        aJFrame.setVisible(true);

        observer.update();



    }
}
