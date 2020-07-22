import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;
//import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
/**
 * Board
 */
public class Board extends JPanel /* implements KeyListener  */{

    private static final long serialVersionUID = -8715353373678321308L;

    private BufferedImage blocks;
    private final int blockSize = 30;
    private final int boardWidth = 10, boardHeight = 20;
    private int[][] board = new int[boardHeight][boardWidth];

    private Shape[] shapes = new Shape[7];
    private Shape currentShape;
    private Timer timer;
    private final int FPS = 60;
    private final int delay = 1000 / FPS;
    private boolean gameOver = false;
    //private int numLinesRemove = 0;
    private JLabel statusbar;

    public Board(Window parent) {
        try {
            blocks = ImageIO.read(Board.class.getResource("tiles.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // TODO Auto-generated method stub
                update();
                repaint();
            }
        });


        timer.start();
        // shape
        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][] { { 1, 1, 1, 1 } // I shape
        }, this, 1);
        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize),
                new int[][] { { 1, 1, 0 }, { 0, 1, 1 } // Z shape
                }, this, 2);
        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize),
                new int[][] { { 0, 1, 1 }, { 1, 1, 0 } // S shape
                }, this, 3);
        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize),
                new int[][] { { 1, 1, 1 }, { 0, 0, 1 } // J shape
                }, this, 4);
        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize),
                new int[][] { { 1, 1, 1 }, { 1, 0, 0 } // L shape
                }, this, 5);
        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize),
                new int[][] { { 1, 1, 1 }, { 0, 1, 0 } // T shape
                }, this, 6);
        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize),
                new int[][] { { 1, 1 }, { 1, 1 } // O shape
                }, this, 7);

        setNextShape();
        initBoard(parent);
    }

    private void initBoard(Window parent) {

        setFocusable(true);
        statusbar = parent.getStatusBar();
        addKeyListener(new TAdapter());
    }

    public void update(){
        currentShape.update();
        if(gameOver){
            timer.stop();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentShape.render(g);
        
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if(board[row][col] != 0)
                    g.drawImage(blocks.getSubimage((board[row][col] - 1)*blockSize, 0, blockSize, blockSize), col*blockSize, row*blockSize, null);
            }
            
        }
        g.drawRect(0,0,blockSize*boardWidth,blockSize*boardHeight);

        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }
        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }
    }

    public void setNextShape(){
        int index = (int)(Math.random()*shapes.length);

        Shape newShape = new Shape(shapes[index].getBlock(), shapes[index].getCoords(), this, shapes[index].getColor());
        currentShape = newShape;

        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[row].length; col++) {
                if(currentShape.getCoords()[row][col] != 0){
                    if(board[row][col + 3] != 0)
                        gameOver = true;
                }
            }
        }


    }

    public JLabel getboardSTT(){
        return statusbar;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int[][] getBoard(){
        return board;
    }
    class TAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                currentShape.setDeltaX(-1);
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                currentShape.setDeltaX(1);
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                currentShape.speedDown();
            }
            if(e.getKeyCode() == KeyEvent.VK_UP){
                currentShape.rotate();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                currentShape.normalSpeed();
            }
        }
    }
}