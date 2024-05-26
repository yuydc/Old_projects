import javax.swing.JFrame;

public class Ekran extends JFrame {
    
    Ekran() {
        this.add(new Oyun());
        this.setTitle("Yılan Oyunu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}