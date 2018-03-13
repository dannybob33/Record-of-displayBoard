package DisplayBoardEmulation.emulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DisplayBoard extends JPanel {
	private final static int PIXEL_WIDTH = 10;
	private final static int PIXEL_HEIGHT = 10;
	private final static int PIXEL_SPACING = 2;
	private int pixelRowMultiplier;
	private int pixelColMultiplier;
	
	public final static int ROWS = 44;
	public final static int COLS = 74;
	
	private Pixel[][] pixelArr;
	
	private JFrame containerFrame;
	
	private TreeSet<String> keys;
	
	public DisplayBoard() {
		/*ROWS = rows;
		COLS = cols;*/
		pixelRowMultiplier = PIXEL_HEIGHT + PIXEL_SPACING;
		pixelColMultiplier = PIXEL_WIDTH + PIXEL_SPACING;
		pixelArr = new Pixel[ROWS][COLS];
		for(int r = 0;r<ROWS;r++) {
			for(int c = 0;c<COLS;c++) {
				int pixelX = c * pixelColMultiplier;
				int pixelY = r * pixelRowMultiplier;
				Rectangle pixelRect = new Rectangle(pixelX,pixelY,PIXEL_WIDTH,PIXEL_HEIGHT);
				pixelArr[r][c] = new Pixel(pixelRect,Color.BLACK);
			}
		}
		keys = new TreeSet<String>();
		this.addKeyListener(new panelKeyListener());
		
		initFrame();
	}
	
	/**
	 * Ignore this method. It sets the size of the JPanel. You won't be using it.
	 */
	public Dimension getPreferredSize() {
        return new Dimension((pixelColMultiplier*COLS)-PIXEL_SPACING,
        		(pixelRowMultiplier*ROWS)-PIXEL_SPACING);
    }
	
	/**
	 * Ignore this method. It paints the JPanel. You won't be using it.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for(int r = 0;r<ROWS;r++) {
			for(int c = 0;c<COLS;c++) {
				Pixel currentPixel = pixelArr[r][c];
				g2.setColor(currentPixel.getPixelColor());
				g2.fill(currentPixel.getPixelRect());
			}
		}
	}
	//Methods to use
	public void setPixel(int row, int col, int red, int green, int blue) {
		/*int rgb = red;
		rgb = (rgb<<8) + green;
		rgb = (rgb<<8) + blue;*/
		colorPixel(row,col,new Color(red,green,blue));
		repaint();
	}
	
	public void setPixel(int row, int col, Color c) {
		colorPixel(row,col,c);
		repaint();
	}
	
	public Color getPixel(int row, int col) {
		return pixelArr[row][col].getPixelColor();
	}
	
	private void colorPixel(int row, int col, Color c) {
		if(row<0 || row>=ROWS || col<0 || col>=COLS) {
			System.out.println("Invalid pixel coordinates " + row + ", " + col + " were inputted.");
			return;
		}
		pixelArr[row][col].setPixelColor(c);
	}
	
	public void colorRect(int row, int col, int width, int height, int r, int g, int b) {
		int finalRow = row+height;
		if(finalRow >= ROWS) {
			finalRow = ROWS-1;
		}
		int finalCol = col+width;
		if(finalCol >= COLS) {
			finalCol = COLS-1;
		}
		for(int rw = row;rw<finalRow;rw++) {
			for(int cl = col;cl<finalCol;cl++) {
				colorPixel(rw,cl,new Color(r,g,b));
			}
		}
		repaint();
	}
	
	public void colorRect(int row, int col, int width, int height, Color c) {
		int finalRow = row+height;
		if(finalRow >= ROWS) {
			finalRow = ROWS-1;
		}
		int finalCol = col+width;
		if(finalCol >= COLS) {
			finalCol = COLS-1;
		}
		for(int rw = row;rw<finalRow;rw++) {
			for(int cl = col;cl<finalCol;cl++) {
				colorPixel(rw,cl,c);
			}
		}
		repaint();
	}
	
	public void colorRect(Rectangle rect, Color c) {
		colorRect(rect.y,rect.x,rect.width,rect.height,c);
	}
	
	public void colorRect(Rectangle rect, int r, int g, int b) {
		colorRect(rect.y,rect.x,rect.width,rect.height,r,g,b);
	}
	
	public void clear() {
		colorRect(0,0,ROWS,COLS,Color.BLACK);
	}
	
	//Key handling
	public Set<String> getKeys() {
		return keys;
	}
	
	// JFrame Handling
	public void show() {
		containerFrame.setVisible(true);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	private void initFrame() {
		System.out.println("Created GUI on EDT? "+
		        SwingUtilities.isEventDispatchThread());
		containerFrame = new JFrame();
		// f.setUndecorated(true);
		// f.setBackground(new Color(0, 0, 0, 0));
		containerFrame.setTitle("Pixel Display");
        containerFrame.add(this);
        containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        containerFrame.pack();
	}
	
	public void close() {
		containerFrame.setVisible(false);
		containerFrame.dispose();
	}
	
	private class panelKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			keys.add("" + arg0.getKeyChar());
			System.out.println("key pressed!");
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			keys.remove("" + arg0.getKeyChar());
			System.out.println("key released!");
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
