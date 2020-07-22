import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Window
 */

public class Window extends JFrame {

    public static final int WIDTH = 350, HEIGHT = 650;
    private JFrame window;
    private Board board;
    private JLabel statusbar;


    public Window(){
        initUI();
/*         window = new JFrame("Tetris Game");
        statusbar = new JLabel(" 0"); 
        add(statusbar, BorderLayout.SOUTH);
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setLocationRelativeTo(null);

        board = new Board(this);
        window.add(board);
        //window.addKeyListener(board);


        window.setVisible(true);
 */
    }
    private void initUI(){
        
        statusbar = new JLabel(" 0");
        add(statusbar,BorderLayout.SOUTH);
        
        var board = new Board(this);
        add(board);

        setTitle("Tetris Game");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    JLabel getStatusBar(){
        return statusbar;
    }
    public static void main(String[] args) {
        new Window();
    }
}