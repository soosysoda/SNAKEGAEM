import javax.swing.*;

public class frame extends JFrame {
    frame() {
        this.setIconImage(new ImageIcon("doc/download.jpg").getImage());
        this.add(new panel());
        this.setVisible(true);
        //this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snaekgaem");
        this.setResizable(false);
        this.pack();
    }
}
