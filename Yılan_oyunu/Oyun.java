import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Oyun extends JPanel implements ActionListener, KeyListener {
    
    static final int EKRAN_GENISLIGI = 600;
    static final int EKRAN_YUKSEKLIGI = 600;
    static final int BIRIM_BOYUTU = 25;
    static final int OYUN_BIRIMLERI = (EKRAN_GENISLIGI * EKRAN_YUKSEKLIGI) / BIRIM_BOYUTU;
    static final int GECIKME = 75;
    final int x[] = new int[OYUN_BIRIMLERI];
    final int y[] = new int[OYUN_BIRIMLERI];
    int govdeParcalari = 6;
    int yenenElmalar;
    int elmaX;
    int elmaY;
    int morDaireX;
    int morDaireY;
    boolean morDaireVarMi = false;
    char yon = 'R';
    boolean run = false;
    Timer zamanlayici;
    Random rastgele;
    
    Oyun() {
        rastgele = new Random();
        this.setPreferredSize(new Dimension(EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        oyunuBaslat();
    }
    
    public void oyunuBaslat() {
        yeniElma();
        YeniMorDaire();
        run = true;
        zamanlayici = new Timer(GECIKME, this);
        zamanlayici.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ciz(g);
    }
    
    public void ciz(Graphics g) {
        if (run) {
            g.setColor(Color.red);
            g.fillOval(elmaX, elmaY, BIRIM_BOYUTU, BIRIM_BOYUTU);
            
            if (morDaireVarMi) {
                g.setColor(Color.magenta);
                g.fillOval(morDaireX, morDaireY, BIRIM_BOYUTU, BIRIM_BOYUTU);
            }

            for (int i = 0; i < govdeParcalari; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], BIRIM_BOYUTU, BIRIM_BOYUTU);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], BIRIM_BOYUTU, BIRIM_BOYUTU);
                }
            }
        } else {
            oyunBitti(g);
        }
    }
    
    public void yeniElma() {
        elmaX = rastgele.nextInt((int)(EKRAN_GENISLIGI / BIRIM_BOYUTU)) * BIRIM_BOYUTU;
        elmaY = rastgele.nextInt((int)(EKRAN_YUKSEKLIGI / BIRIM_BOYUTU)) * BIRIM_BOYUTU;
    }
    
    public void YeniMorDaire() {
        if (!morDaireVarMi && rastgele.nextInt(2) == 0) { 
            morDaireX = rastgele.nextInt((int)(EKRAN_GENISLIGI / BIRIM_BOYUTU)) * BIRIM_BOYUTU;
            morDaireY = rastgele.nextInt((int)(EKRAN_YUKSEKLIGI / BIRIM_BOYUTU)) * BIRIM_BOYUTU;
            morDaireVarMi = true;
        }
    }
    
    public void hareketEt() {
        for (int i = govdeParcalari; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (yon) {
            case 'U':
                y[0] = y[0] - BIRIM_BOYUTU;
                break;
            case 'D':
                y[0] = y[0] + BIRIM_BOYUTU;
                break;
            case 'L':
                x[0] = x[0] - BIRIM_BOYUTU;
                break;
            case 'R':
                x[0] = x[0] + BIRIM_BOYUTU;
                break;
        }
    }
    
    public void elmayiKontrolEt() {
        if ((x[0] == elmaX) && (y[0] == elmaY)) {
            govdeParcalari++;
            yenenElmalar++;
            yeniElma();
            YeniMorDaire();
        }
    }
    
    public void morDaireyiKontrolEt() {
        if (morDaireVarMi && (x[0] == morDaireX) && (y[0] == morDaireY)) {
            govdeParcalari = Math.max(govdeParcalari - 1, 1);
            morDaireVarMi = false;
            YeniMorDaire();
        }
    }
    
    public void carpismalariKontrolEt() {
        for (int i = govdeParcalari; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                run = false;
            }
        }

        if (x[0] < 0) {
            run = false;
        }

        if (x[0] >= EKRAN_GENISLIGI) {
            run = false;
        }

        if (y[0] < 0) {
            run = false;
        }

        if (y[0] >= EKRAN_YUKSEKLIGI) {
            run = false;
        }

        if (!run) {
            zamanlayici.stop();
        }
    }
    
    public void oyunBitti(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrik = getFontMetrics(g.getFont());
        g.drawString("Oyun Bitti", (EKRAN_GENISLIGI - metrik.stringWidth("Oyun Bitti")) / 2, EKRAN_YUKSEKLIGI / 2);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (run) {
            hareketEt();
            elmayiKontrolEt();
            morDaireyiKontrolEt();
            carpismalariKontrolEt();
        }
        repaint();
    }
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (yon != 'R') {
                    yon = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (yon != 'L') {
                    yon = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (yon != 'D') {
                    yon = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (yon != 'U') {
                    yon = 'D';
                }
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyTyped(KeyEvent e) {}
}
