import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GunlukGorevListesi extends JFrame {
    private JTextArea metinAlani;
    private Timer zamanlayici;

    public GunlukGorevListesi() {
        setTitle("Listeleyici");
        setSize(600, 600);
        setIconImage(Toolkit.getDefaultToolkit().getImage("ikon.png"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 18, 18));

        metinAlani = new CizgiliTextArea();
        metinAlani.setLineWrap(true);
        metinAlani.setWrapStyleWord(true);
        metinAlani.setForeground(Color.WHITE);
        metinAlani.setBackground(new Color(30, 30, 30));
        metinAlani.setFont(new Font("Arial", Font.PLAIN, 16));
        metinAlani.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane kaydirmaPane = new JScrollPane(metinAlani);
        kaydirmaPane.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(148, 0, 211)));

        JScrollBar dikeyKaydirmaCubu = kaydirmaPane.getVerticalScrollBar();
        dikeyKaydirmaCubu.setBackground(new Color(30, 30, 30));
        dikeyKaydirmaCubu.setForeground(new Color(148, 0, 211));
        dikeyKaydirmaCubu.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(148, 0, 211);
                this.trackColor = new Color(30, 30, 30);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton buton = new JButton();
                buton.setBackground(new Color(75, 0, 130));
                buton.setBorder(BorderFactory.createEmptyBorder());
                buton.setPreferredSize(new Dimension(0, 0));
                return buton;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton buton = new JButton();
                buton.setBackground(new Color(75, 0, 130));
                buton.setBorder(BorderFactory.createEmptyBorder());
                buton.setPreferredSize(new Dimension(0, 0));
                return buton;
            }
        });

        add(kaydirmaPane, BorderLayout.CENTER);

        JLabel baslik = new JLabel("Listeleyici", SwingConstants.CENTER);
        baslik.setForeground(new Color(148, 0, 211));
        baslik.setFont(new Font("Arial", Font.BOLD, 24));
        add(baslik, BorderLayout.NORTH);

        dosyadanYukle();

        zamanlayici = new Timer(3000, e -> gorevleriKaydet());
        zamanlayici.start();

        setVisible(true);
    }

    private void gorevleriKaydet() {
        try (BufferedWriter yazar = new BufferedWriter(new FileWriter("liste.txt"))) {
            yazar.write(metinAlani.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dosyadanYukle() {
        try (BufferedReader okur = new BufferedReader(new FileReader("liste.txt"))) {
            String satir;
            StringBuilder icerik = new StringBuilder();
            while ((satir = okur.readLine()) != null) {
                icerik.append(satir).append("\n");
            }
            metinAlani.setText(icerik.toString());
        } catch (IOException e) {
            System.out.println("Dosya bulunamadı veya okunamadı.");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(GunlukGorevListesi::new);
    }
}

class CizgiliTextArea extends JTextArea {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(50, 50, 50));

        int satirYuksekligi = g2d.getFontMetrics().getHeight();
        for (int i = 0; i < getHeight(); i += satirYuksekligi) {
            g2d.drawLine(0, i, getWidth(), i);
        }

        super.paintComponent(g);
    }
}
