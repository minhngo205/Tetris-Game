package tetris;
import javax.swing.*;
import java.awt.*;

public class Window {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Tetris");
            f.setIconImage(new ImageIcon("tetris.png").getImage());
            f.setResizable(false);
            f.add(new Board(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}