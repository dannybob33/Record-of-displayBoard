package DisplayBoardEmulation.emulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import arduino.Arduino;

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

	private LinkedList<KeyRunnable> keyCallbacks;

//  arduino inst vars
	
	private Arduino a;
	int rowMax; //DEclusive
	int colMax; //DEclusive

	public DisplayBoard()
	{
		/*
		 * init serial port connection to arduino
		 */
		int port = 6;
		a=new Arduino("COM"+port, 115200); // Supported baud rates are 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 31250, 38400, 57600, and 115200.
		a.openConnection();
		try {
			Thread.sleep(2500);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Initialized");

		/*
		 * ROWS = rows; COLS = cols;
		 */
		pixelRowMultiplier = PIXEL_HEIGHT + PIXEL_SPACING;
		pixelColMultiplier = PIXEL_WIDTH + PIXEL_SPACING;
		pixelArr = new Pixel[ROWS][COLS];
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				int pixelX = c * pixelColMultiplier;
				int pixelY = r * pixelRowMultiplier;
				Rectangle pixelRect = new Rectangle(pixelX, pixelY, PIXEL_WIDTH, PIXEL_HEIGHT);
				pixelArr[r][c] = new Pixel(pixelRect, Color.BLACK);
			}
		}
		keys = new TreeSet<String>();
		this.addKeyListener(new panelKeyListener());
		keyCallbacks = new LinkedList<KeyRunnable>();
		setBackground(Color.GRAY);
		initFrame();
	}

	/**
	 * Ignore this method. It sets the size of the JPanel. You won't be using it.
	 */
	public Dimension getPreferredSize()
	{
		return new Dimension((pixelColMultiplier * COLS) - PIXEL_SPACING, (pixelRowMultiplier * ROWS) - PIXEL_SPACING);
	}

	/**
	 * Ignore this method. It paints the JPanel. You won't be using it.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				Pixel currentPixel = pixelArr[r][c];
				g2.setColor(currentPixel.getPixelColor());
				g2.fill(currentPixel.getPixelRect());
			}
		}
	}

	// Methods to use
	public void setPixel(int row, int col, int red, int green, int blue)
	{
		/*
		 * int rgb = red; rgb = (rgb<<8) + green; rgb = (rgb<<8) + blue;
		 */
		colorPixel(row, col, new Color(red, green, blue));
	}

	public void setPixel(int row, int col, Color c)
	{
		colorPixel(row, col, c);
	}

	public Color getPixel(int row, int col)
	{
		return pixelArr[row][col].getPixelColor();
	}

	private void colorPixel(int row, int col, Color c)
	{
		if (row < 0 || row >= ROWS || col < 0 || col >= COLS)
		{
			System.out.println("Invalid pixel coordinates " + row + ", " + col + " were inputted.");
			return;
		}
		pixelArr[row][col].setPixelColor(c);
	}

	public void drawCircle(int x, int y, int r)
	{
		drawCircle(x,y,r,new Color(200, 200, 200));
	}

	public void drawCircle(int x, int y, int r, int red, int green, int blue)
	{
		drawCircle(x,y,r,new Color(red,green,blue));
	}

	public void drawCircle(int x, int y, int r, Color col)
	{
		double PI = 3.1415926535;
		double i, angle, x1, y1;

		for (i = 0; i < 360; i += 1)
		{
			angle = i;
			x1 = r * Math.cos(angle * PI / 180);
			y1 = r * Math.sin(angle * PI / 180);

			int ElX = (int) Math.round(x + x1);
			int ElY = (int) Math.round(y + y1);

			setPixel(ElX, ElY, col);
		}
	}

	public void drawCircle(int x, int y, int r, int red, int green, int blue, boolean fill)
	{
		double PI = 3.1415926535;
		double i, angle, x1, y1;
		if (fill == false)
		{
			for (i = 0; i < 360; i += 1)
			{
				angle = i;
				x1 = r * Math.cos(angle * PI / 180);
				y1 = r * Math.sin(angle * PI / 180);

				int ElX = (int) Math.round(x + x1);
				int ElY = (int) Math.round(y + y1);

				setPixel(ElX, ElY, new Color(red, green, blue));
			}
		}
		else
		{
			for (int j = r; j >= 0; j--)
			{
				for (i = 0; i < 360; i += 1)
				{
					angle = i;
					x1 = j * Math.cos(angle * PI / 180);
					y1 = j * Math.sin(angle * PI / 180);

					int ElX = (int) Math.round(x + x1);
					int ElY = (int) Math.round(y + y1);

					setPixel(ElX, ElY, new Color(red, green, blue));
				}
			}
		}
	}

	public void drawCircle(int x, int y, int r, Color col, boolean fill)
	{
		double PI = 3.1415926535;
		double i, angle, x1, y1;
		if (fill == false)
		{
			for (i = 0; i < 360; i += 1)
			{
				angle = i;
				x1 = r * Math.cos(angle * PI / 180);
				y1 = r * Math.sin(angle * PI / 180);

				int ElX = (int) Math.round(x + x1);
				int ElY = (int) Math.round(y + y1);

				setPixel(ElX, ElY, col);
			}
		}
		else
		{
			for (int j = r; j >= 0; j--)
			{
				for (i = 0; i < 360; i += 1)
				{
					angle = i;
					x1 = j * Math.cos(angle * PI / 180);
					y1 = j * Math.sin(angle * PI / 180);

					int ElX = (int) Math.round(x + x1);
					int ElY = (int) Math.round(y + y1);

					setPixel(ElX, ElY, col);
				}
			}
		}
	}

	public void colorRect(int row, int col, int width, int height, int r, int g, int b)
	{
		colorRect(row,col,width,height,new Color(r,g,b));
	}

	public void addKeyCallback(KeyRunnable r)
	{
		keyCallbacks.add(r);
	}

	public boolean hasKeyCallback(KeyRunnable r)
	{
		return keyCallbacks.contains(r);
	}

	public void colorRect(int row, int col, int width, int height, Color c)
	{
		int finalRow = row + height;
		if (finalRow >= ROWS)
		{
			finalRow = ROWS - 1;
		}
		int finalCol = col + width;
		if (finalCol >= COLS)
		{
			finalCol = COLS - 1;
		}
		for (int rw = row; rw <= finalRow; rw++)
		{
			for (int cl = col; cl <= finalCol; cl++)
			{
				colorPixel(rw, cl, c);
			}
		}
		
		// send to Arduino
		
		a.serialWrite("R "+row+" "+col+" "+width+" "+height+" "+c.getRed()+" "+c.getGreen()+" "+c.getBlue()+" "+"1");

	}

	public void colorRect(Rectangle rect, Color c)
	{
		colorRect(rect.y, rect.x, rect.width, rect.height, c);
	}

	public void colorRect(Rectangle rect, int r, int g, int b)
	{
		colorRect(rect.y, rect.x, rect.width, rect.height, r, g, b);
	}

	public void clear()
	{
		colorRect(0, 0, COLS, ROWS, Color.BLACK);
	}

	public boolean isCleared()
	{
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				if (!getPixel(r, c).equals(Color.BLACK))
				{
					return false;
				}
			}
		}
		return true;
	}

	// Key handling
	public Set<String> getKeys()
	{
		return keys;
	}

	// JFrame Handling
	public void show()
	{
		containerFrame.setVisible(true);
		this.setFocusable(true);
		this.requestFocus();
	}

	private void initFrame()
	{
		System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
		containerFrame = new JFrame();
		// f.setUndecorated(true);
		// f.setBackground(new Color(0, 0, 0, 0));
		containerFrame.setTitle("Pixel Display");
		containerFrame.add(this);
		containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		containerFrame.pack();
	}

	public void close()
	{
		containerFrame.setVisible(false);
		containerFrame.dispose();
	}

	private class panelKeyListener implements KeyListener
	{

		
		public void keyPressed(KeyEvent arg0)
		{
			keys.add("" + arg0.getKeyChar());
			try
			{
				for (KeyRunnable run : keyCallbacks)
				{
					run.run(arg0);
				}
			}
			catch (ConcurrentModificationException e)
			{
				System.out.println("Concurrent Modification Exception in key press listeners!");

			}
		}

		
		public void keyReleased(KeyEvent arg0)
		{
			keys.remove("" + arg0.getKeyChar());
			try
			{
				for (KeyRunnable run : keyCallbacks)
				{
					run.run(arg0);
				}
			}
			catch (ConcurrentModificationException e)
			{
				System.out.println("Concurrent Modification Exception in key press listeners!");
			}
		}

		
		public void keyTyped(KeyEvent arg0)
		{
			// TODO Auto-generated method stub

		}
	}

	
	/**
	 * drawString without n (for easier Java testing)
	 * 
	 * @param row
	 * @param col
	 * @param red
	 * @param green
	 * @param blue
	 * @param chars
	 */
	public void drawString(int row, int col, int red, int green, int blue, String chars)
	{
		char[][] charset = charSet.Cset(); // creating character set

		int extraSpacing = 0; // extra spacing between letters

		for (int i = 0; i < chars.length(); i++)
		{ // for each character in "chars"...
			String letter = chars.substring(i, i + 1); // get corresponding letter

			char[] locs = charset[letter.hashCode()]; // array of hex codes for each row of pixels in the letter
			System.out.println("L: " + letter + "; HC: " + letter.hashCode());
			if (letter.hashCode() == 32)// if the character is a space (" ")...
			{
				extraSpacing -= 3;// reduce the spacing
			}
			for (int c = 0; c < locs.length; c++) // for each column...
			{
				int r = 0; // intialized row count
				for (int j = 1; j <= 256; j *= 2) // for each pixel/binary in the row...
				{
					if (((locs[c]) & j) != 0) // if the pixel should be on...
					{

						setPixel(r + row, c + col + extraSpacing, red, green, blue);// turn the pixel on
					}

					r++;// increase the row count
				}
			}
			extraSpacing += 6; // add spacing between letters
		}

	}

	public void drawLine(int r1, int c1, int r2, int c2, Color col) {
		// Draw line using Color
		int deltC = c2 - c1;
		int deltR = r2 - r1;
		double slope = (double) deltR / deltC;

		double c = Math.min(c1, c2);
		double r= Math.min(r1, r2);
		
		if (slope > -1 && slope < 1) {
			if(Math.min(c1, c2)==c2) {
				r=r2;
			}
			else {
				r=r1;
			}
			if(Math.min(r1, r2)==r2) {
				c=c2;
			}
			else {
				c=c1;
			}
			for (int c3 = Math.min(c1, c2); c3 <= Math.max(c1, c2); c3++) {
				r = r + slope;
				setPixel((int)r, c3, col);
			}

		}
		else {
			if(Math.min(c1, c2)==c2) {
				r=r2;
			}
			else {
				r=r1;
			}
			if(Math.min(r1, r2)==r2) {
				c=c2;
			}
			else {
				c=c1;
			}
			for (int r3 = Math.min(r1, r2); r3 <= Math.max(r1, r2); r3++) {
				c = c + 1/slope;
				setPixel(r3, (int)c, col);
			}
		}
	}

	public void drawLine(int r1, int c1, int r2, int c2, int red, int green, int blue) {
		// Draw line using RGB
		int deltC = c2 - c1;
		int deltR = r2 - r1;
		double slope = (double) deltR / deltC;

		double c = Math.min(c1, c2);
		double r= Math.min(r1, r2);
		
		if (slope > -1 && slope < 1) {
			if(Math.min(c1, c2)==c2) {
				r=r2;
			}
			else {
				r=r1;
			}
			if(Math.min(r1, r2)==r2) {
				c=c2;
			}
			else {
				c=c1;
			}
			for (int c3 = Math.min(c1, c2); c3 <= Math.max(c1, c2); c3++) {
				r = r + slope;
				setPixel((int)r, c3, red, green, blue);
			}

		}
		else {
			if(Math.min(c1, c2)==c2) {
				r=r2;
			}
			else {
				r=r1;
			}
			if(Math.min(r1, r2)==r2) {
				c=c2;
			}
			else {
				c=c1;
			}
			for (int r3 = Math.min(r1, r2); r3 <= Math.max(r1, r2); r3++) {
				c = c + 1/slope;
				setPixel(r3, (int)c, red, green, blue);
			}
		}
	}


	/**
	 * 
	 * @param n
	 *            - The number of characters in chars
	 * @param row
	 * @param col
	 * @param red
	 * @param green
	 * @param blue
	 * @param chars
	 */
	public void drawString(int n, int row, int col, int red, int green, int blue, String chars)
	{
		char[][] charset = charSet.Cset(); // creating character set
		int extraSpacing = 0; // extra spacing between letters

		for (int i = 0; i < n; i++)
		{ // for each character in "chars"...
			String letter = chars.substring(i, i + 1); // get corresponding letter

			char[] locs = charset[letter.hashCode()]; // array of hex codes for each row of pixels in the letter
			System.out.println("L: " + letter + "; HC: " + letter.hashCode());
			if (letter.hashCode() == 32)// if the character is a space (" ")...
			{
				extraSpacing -= 3;// reduce the spacing
			}
			for (int c = 0; c < locs.length; c++) // for each column...
			{
				int r = 0; // intialized row count
				for (int j = 1; j <= 256; j *= 2) // for each pixel/binary in the row...
				{
					if (((locs[c]) & j) != 0) // if the pixel should be on...
					{

						setPixel(r + row, c + col + extraSpacing, red, green, blue);// turn the pixel on
					}

					r++;// increase the row count
				}
			}
			extraSpacing += 6; // add spacing between letters
		}

	}

	
	/**
	 * 
	 * @param n
	 *            - The number of Characters in chars
	 * @param row
	 * @param col
	 * @param red
	 * @param green
	 * @param blue
	 * @param chars
	 * @param spacing
	 *            - Integer to customize spacing between characters
	 */
	public void drawString(int n, int row, int col, int red, int green, int blue, String chars, int spacing)
	{
		char[][] charset = charSet.Cset(); // creating character set

		int extraSpacing = 0; // extra spacing between letters

		for (int i = 0; i < n; i++)
		{ // for each character in "chars"...
			String letter = chars.substring(i, i + 1); // get corresponding letter

			char[] locs = charset[letter.hashCode()]; // array of hex codes for each row of pixels in the letter
			System.out.println("L: " + letter + "; HC: " + letter.hashCode());
			if (letter.hashCode() == 32)// if the character is a space (" ")...
			{
				extraSpacing -= spacing / 2;// reduce the spacing
			}
			for (int c = 0; c < locs.length; c++) // for each column...
			{
				int r = 0; // intialized row count
				for (int j = 1; j <= 256; j *= 2) // for each pixel/binary in the row...
				{
					if (((locs[c]) & j) != 0) // if the pixel should be on...
					{

						setPixel(r + row, c + col + extraSpacing, red, green, blue);// turn the pixel on
					}

					r++;// increase the row count
				}
			}
			extraSpacing += spacing; // add spacing between letters
		}

	}

	
	public void drawString(int row, int col, Color c, String chars)
	{
		drawString(row, col, c.getRed(), c.getGreen(), c.getBlue(), chars);
	}

	
	public void drawString(int row, int col, Color c, String chars, int spacing)
	{
		drawString(row, col, c.getRed(), c.getGreen(), c.getBlue(), chars, spacing);
	}

	public int StringWidth(String chars)
	{
		char[] chararr = chars.toCharArray();
		int pixels = 0;
		for (char c : chararr)
		{
			if (("" + c).equals(" "))
			{
				pixels += 3;
			}
			else
			{
				pixels += 6;
			}
		}
		return pixels;
	}

	public int StringWidth(String chars, int spacing)
	{
		char[] chararr = chars.toCharArray();
		int pixels = 0;
		for (char c : chararr)
		{
			if (("" + c).equals(" "))
			{
				pixels += spacing / 2;
			}
			else
			{
				pixels += spacing;
			}
		}
		return pixels;
	}

	
	public void drawString(int row, int col, int red, int green, int blue, String chars, int spacing)
	{
		drawString(chars.length(), row, col, red, green, blue, chars, spacing);
	}
	
	public void drawString(int n, int row, int col, Color c, String chars)
	{
		drawString(n, row, col, c.getRed(), c.getGreen(), c.getBlue(), chars);
	}

	
	public void drawString(int n, int row, int col, Color c, String chars, int spacing)
	{
		drawString(n, row, col, c.getRed(), c.getGreen(), c.getBlue(), chars, spacing);
	}
	
	public void drawImage(BufferedImage img, int row, int col, int width, int height) {
		BufferedImage newImage = resize(img,width,height);
		for(int r = 0;r<newImage.getHeight();r++) {
			for(int c = 0;c<newImage.getWidth();c++) {
				//System.out.println(newImage.getWidth() + ", " + newImage.getHeight() + ", " + c + ", "+ r);
				this.setPixel(r+row,c+col,
						overlayAlphaColor(
								new Color(newImage.getRGB(c, r)),
								this.getPixel(r,c)));
			}
		}
	}
	
	private BufferedImage resize(BufferedImage img, int width, int height) {
		int w = img.getWidth();
	    int h = img.getHeight();
	    BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = dimg.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(img, 0, 0, width, height, 0, 0, w, h, null);
	    g.dispose();
	    return dimg;  
	}
	
	private Color overlayAlphaColor(Color c1, Color c2) {
		double a1 = c1.getAlpha()/255.0;
		double a2 = c1.getAlpha()/255.0;
		double r1 = c1.getRed()/255.0;
		double r2 = c1.getRed()/255.0;
		double g1 = c1.getGreen()/255.0;
		double g2 = c1.getGreen()/255.0;
		double b1 = c1.getBlue()/255.0;
		double b2 = c1.getBlue()/255.0;
		int r = (int)(255*(r1*a1 + a2*r2*(1-a1)));
		int g = (int)(255*(g1*a1 + a2*g2*(1-a1)));
		int b = (int)(255*(b1*a1 + a2*b2*(1-a1)));
		return new Color(r,g,b);
	}
	
	public void repaintBoard() {
		repaint();
		a.serialWrite("E");
	}
}
