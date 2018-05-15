package DisplayBoardEmulation.emulator;

// problems: 
// extraSpacing isn't used in drawString
// if displayboard only, doesn't read keyboard?
// webcam - displayboard can't access data buffer - hangs

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
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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

	// use these to determine default behavior for DisplayBoard

	private final static boolean DEF_EMULATOR = true;
	private final static boolean DEF_ARDUINO = true;
	private final static int ARDUINO_PORT = 7;

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

	// arduino inst vars

	private Arduino a;
	int rowMax; // DEclusive
	int colMax; // DEclusive

	private boolean em;
	private boolean ard;
	
	private ArrayList<String> commandBuffer;

	public DisplayBoard() {
		this(DEF_EMULATOR, DEF_ARDUINO);
	}

	public DisplayBoard(boolean em, boolean ard) {
		this.em = em;
		this.ard = ard;

		/*
		 * init serial port connection to arduino
		 */
		if (ard) {
			int port = ARDUINO_PORT;
			a = new Arduino("COM" + port, 250000); // Supported baud rates are 300, 600, 1200, 2400, 4800, 9600,
													// 14400, 19200, 28800, 31250, 38400, 57600, and 115200.
			a.openConnection();
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Initialized");
		}

		if (em) {
			/*
			 * ROWS = rows; COLS = cols;
			 */
			pixelRowMultiplier = PIXEL_HEIGHT + PIXEL_SPACING;
			pixelColMultiplier = PIXEL_WIDTH + PIXEL_SPACING;
			pixelArr = new Pixel[ROWS][COLS];
			for (int r = 0; r < ROWS; r++) {
				for (int c = 0; c < COLS; c++) {
					int pixelX = c * pixelColMultiplier;
					int pixelY = r * pixelRowMultiplier;
					Rectangle pixelRect = new Rectangle(pixelX, pixelY, PIXEL_WIDTH, PIXEL_HEIGHT);
					pixelArr[r][c] = new Pixel(pixelRect, Color.BLACK);
				}
			}
			setBackground(Color.GRAY);
			initFrame();
		}
		keys = new TreeSet<String>();
		this.addKeyListener(new panelKeyListener());
		keyCallbacks = new LinkedList<KeyRunnable>();
		
		commandBuffer = new ArrayList<String>();
	}

	/**
	 * Ignore this method. It sets the size of the JPanel. You won't be using it.
	 */
	public Dimension getPreferredSize() {
		return new Dimension((pixelColMultiplier * COLS) - PIXEL_SPACING, (pixelRowMultiplier * ROWS) - PIXEL_SPACING);
	}

	/**
	 * Ignore this method. It paints the JPanel. You won't be using it.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				Pixel currentPixel = pixelArr[r][c];
				g2.setColor(currentPixel.getPixelColor());
				g2.fill(currentPixel.getPixelRect());
			}
		}
	}

	public void addKeyCallback(KeyRunnable r) {
		keyCallbacks.add(r);
	}

	public boolean hasKeyCallback(KeyRunnable r) {
		return keyCallbacks.contains(r);
	}

	// getPixel - No support for physical displayboard!

	public Color getPixel(int row, int col) {
		if (em)
			return pixelArr[row][col].getPixelColor();
		return Color.BLACK;
	}

	// **** DRAW PIXEL

	// this is the master pixel drawing method
	// the emOnly flag allows it to be used by other methods to build
	// more complex shapes on the emulator, but still have the option
	// to send a single command to the Arduino for interpretation by it

	public void drawPixel(int row, int col, Color c, boolean emOnly) {
		if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
			System.out.println("Invalid pixel coordinates " + row + ", " + col + " were inputted.");
			return;
		}
		if (em) {
			pixelArr[row][col].setPixelColor(c);
		}
		if (!emOnly && ard) {
			commandBuffer.add("P" + (char) row + (char) col + (char) c.getRed() + (char) c.getGreen() + (char) c.getBlue());
		}
	}

	// this are the variants that people can use

	public void setPixel(int row, int col, int red, int green, int blue) {
		drawPixel(row, col, new Color(red, green, blue), false);
	}

	public void setPixel(int row, int col, Color c) {
		drawPixel(row, col, c, false);
	}

	private void colorPixel(int row, int col, Color c) {
		drawPixel(row, col, c, false);
	}

	// **** DRAW RECT

	public void drawRect(int row, int col, int width, int height, Color c) {
		int finalRow = row + height;
		if (finalRow >= ROWS) {
			finalRow = ROWS - 1;
		}
		int finalCol = col + width;
		if (finalCol >= COLS) {
			finalCol = COLS - 1;
		}

		// draw pixel by pixel for emulator

		if (em) {
			for (int rw = row; rw <= finalRow; rw++) {
				for (int cl = col; cl <= finalCol; cl++) {
					drawPixel(rw, cl, c, true); // draw pixels to emulator (only)
				}
			}
		}

		// send single command to Arduino and let it draw the rect

		if (ard) {
			commandBuffer.add("R" + (char) row + (char) col + (char) width + (char) height + (char) c.getRed()
					+ (char) c.getGreen() + (char) c.getBlue() + (char) 1);
		}
	}

	// variants

	public void colorRect(int row, int col, int width, int height, Color c) {
		drawRect(row, col, width, height, c);
	}

	public void colorRect(int row, int col, int width, int height, int r, int g, int b) {
		drawRect(row, col, width, height, new Color(r, g, b));
	}

	public void colorRect(Rectangle rect, Color c) {
		drawRect(rect.y, rect.x, rect.width, rect.height, c);
	}

	public void colorRect(Rectangle rect, int r, int g, int b) {
		drawRect(rect.y, rect.x, rect.width, rect.height, new Color(r, g, b));
	}
	// ***** DRAW CIRCLE

	public void drawCircle(int row, int col, int r, Color c, boolean fill) {

		// draw pixel by pixel for emulator

		if (em) {
			double PI = 3.1415926535;

			double i, angle, x1, y1;
			if (fill == false) {
				for (i = 0; i < 360; i += 1) {
					angle = i;
					x1 = r * Math.cos(angle * PI / 180);
					y1 = r * Math.sin(angle * PI / 180);

					int ElX = (int) Math.round(col + x1);
					int ElY = (int) Math.round(row + y1);

					drawPixel(ElX, ElY, c, true);
				}
			} else {
				for (int j = r; j >= 0; j--) {
					for (i = 0; i < 360; i += 1) {
						angle = i;
						x1 = j * Math.cos(angle * PI / 180);
						y1 = j * Math.sin(angle * PI / 180);

						int ElX = (int) Math.round(col + x1);
						int ElY = (int) Math.round(row + y1);

						drawPixel(ElX, ElY, c, true);
					}
				}
			}
		}

		// send command to arduino

		if (ard) {
			int i = fill ? 1 : 0;
			commandBuffer.add("C" + (char) row + (char) col + (char) r + (char) c.getRed() + (char) c.getGreen()
					+ (char) c.getBlue() + (char) i);
		}

	}

	// variants

	public void drawCircle(int row, int col, int r) {
		drawCircle(row, col, r, new Color(200, 200, 200));
	}

	public void drawCircle(int row, int col, int r, int red, int green, int blue) {
		drawCircle(row, col, r, new Color(red, green, blue));
	}

	public void drawCircle(int row, int col, int r, Color c) {
		drawCircle(row, col, r, c, false);
	}

	public void drawCircle(int x, int y, int r, int red, int green, int blue, boolean fill) {
		drawCircle(x, y, r, new Color(red, green, blue), fill);
	}

	// ***** CLEAR

	public void clear() {
		// just draw a black rectangle

		drawRect(0, 0, COLS, ROWS, Color.BLACK);
	}

	// this works only for emulator

	public boolean isCleared() {

		if (em) {
			for (int r = 0; r < ROWS; r++) {
				for (int c = 0; c < COLS; c++) {
					if (!getPixel(r, c).equals(Color.BLACK)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// ***** DRAW STRING

	public void drawString(int row, int col, Color color, String chars, int spacing) {
		int n = chars.length();

		// look up char in table and drawPixels for emulator

		if (em) {
			char[][] charset = charSet.Cset(); // creating character set

			int extraSpacing = 0; // extra spacing between letters

			for (int i = 0; i < n; i++) { // for each character in "chars"...
				String letter = chars.substring(i, i + 1); // get corresponding letter
				char[] locs = charset[letter.hashCode()]; // array of hex codes for each row of pixels in the letter

				if (letter.hashCode() == 32)// if the character is a space (" ")...
				{
					extraSpacing -= 3;// reduce the spacing
				}
				for (int c = 0; c < locs.length; c++) { // for each column...
					int r = 0; // intialized row count
					for (int j = 1; j <= 256; j *= 2) // for each pixel/binary in the row...
					{
						if (((locs[c]) & j) != 0) // if the pixel should be on...
						{

							drawPixel(r + row, c + col + extraSpacing, color, true);// turn the pixel on
						}

						r++;// increase the row count
					}
				}
				extraSpacing += 6; // add spacing between letters
			}
		}

		// send command to arduino

		if (ard) {
			commandBuffer.add("S" + (char) row + (char) col + (char) color.getRed() + (char) color.getGreen()
					+ (char) color.getBlue() + (char) chars.length() + chars);
		}
	}

	// variants

	public void drawString(int row, int col, Color c, String chars) {
		drawString(row, col, c, chars, 0);
	}

	public void drawString(int row, int col, int red, int green, int blue, String chars) {
		drawString(row, col, new Color(red, green, blue), chars, 0);
	}

	public void drawString(int n, int row, int col, int red, int green, int blue, String chars) {
		drawString(row, col, new Color(red, green, blue), chars, 0); // ignore n
	}

	public void drawString(int n, int row, int col, Color c, String chars) {
		drawString(row, col, c, chars, 0); // ignore n
	}

	public void drawString(int row, int col, int red, int green, int blue, String chars, int spacing) {
		drawString(row, col, new Color(red, green, blue), chars, spacing);
	}

	public void drawString(int n, int row, int col, Color c, String chars, int spacing) {
		drawString(n, row, col, c, chars, spacing);
	}

	public int StringWidth(String chars) {
		char[] chararr = chars.toCharArray();
		int pixels = 0;
		for (char c : chararr) {
			if (("" + c).equals(" ")) {
				pixels += 3;
			} else {
				pixels += 6;
			}
		}
		return pixels;
	}

	public int StringWidth(String chars, int spacing) {
		char[] chararr = chars.toCharArray();
		int pixels = 0;
		for (char c : chararr) {
			if (("" + c).equals(" ")) {
				pixels += spacing / 2;
			} else {
				pixels += spacing;
			}
		}
		return pixels;
	}

	// ***** DRAW LINE

	public void drawLine(int r1, int c1, int r2, int c2, Color color) {

		// draw pixel by pixel for emulator

		if (em) {
			// Draw line using Color
			int deltC = c2 - c1;
			int deltR = r2 - r1;
			double slope = (double) deltR / deltC;

			double c = Math.min(c1, c2);
			double r = Math.min(r1, r2);

			if (slope > -1 && slope < 1) {
				if (Math.min(c1, c2) == c2) {
					r = r2;
				} else {
					r = r1;
				}
				if (Math.min(r1, r2) == r2) {
					c = c2;
				} else {
					c = c1;
				}
				for (int c3 = Math.min(c1, c2); c3 <= Math.max(c1, c2); c3++) {
					r = r + slope;
					drawPixel((int) r, c3, color, true);
				}

			} else {
				if (Math.min(c1, c2) == c2) {
					r = r2;
				} else {
					r = r1;
				}
				if (Math.min(r1, r2) == r2) {
					c = c2;
				} else {
					c = c1;
				}
				for (int r3 = Math.min(r1, r2); r3 <= Math.max(r1, r2); r3++) {
					c = c + 1 / slope;
					drawPixel(r3, (int) c, color, true);
				}
			}
		}

		// send command to arduino

		if (ard) {
			commandBuffer.add("L" + (char) r1 + (char) c1 + (char) r2 + (char) c2 + (char) color.getRed()
					+ (char) color.getGreen() + (char) color.getBlue());
		}
	}

	public void drawLine(int r1, int c1, int r2, int c2, int red, int green, int blue) {
		drawLine(r1, c1, r2, c2, new Color(red, green, blue));
	}

	// ***** DRAW IMAGE

	public void drawImage(BufferedImage image, int row, int col, int width, int height) {

		// first resize the image to the newly desired dimensions

		BufferedImage newImage = resize(image, width, height);

		// draw pixels for emulator

		if (em) {
			for (int r = 0; r < newImage.getHeight(); r++) {
				for (int c = 0; c < newImage.getWidth(); c++) {
					if (r==30 && c==30) System.out.println(overlayAlphaColor(new Color(newImage.getRGB(c, r)), this.getPixel(r, c)));
					drawPixel(r + row, c + col,
							overlayAlphaColor(new Color(newImage.getRGB(c, r)), this.getPixel(r, c)), true);
				}
			}
		}

		// build complex command to send to arduino

		if (ard) {
			width = newImage.getWidth(); // just to be sure...
			height = newImage.getHeight();

			// THIS METHOD SHOULD WORK AND BE MUCH FASTER
			// BUT IT HANGS ON THE .getData() call ???
			//			//
			// WritableRaster rast = newImage.getRaster();
			//
			// DataBuffer buff = rast.getDataBuffer();
			//
			// final byte[] pixels = ((DataBufferByte) buff).getData();
			//
			// System.out.println(new String(pixels));

			// ALTERNATE (SLOWER) METHOD - GET RGB PIXEL BY PIXEL

			String pixels = "";
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
					int pix = newImage.getRGB(c, r);
					if (r==30 && c== 30) {
						int red = (pix >> 16) & 0xFF;
						int green = (pix >> 8) & 0xFF;
						int blue = pix& 0xFF;
						System.out.println(" "+red+" "+green+" "+blue);
					}
					pixels += (char) ((pix >> 16) & 0xFF);//r
					pixels += (char) ((pix >> 8) & 0xFF);//g
					pixels += (char) (pix & 0xFF);//b
				}
			}
			this.repaintBoard();
			a.serialWrite("X" + (char) row + (char) col + (char) width + (char) height + pixels);
		}
	}

	private BufferedImage resize(BufferedImage img, int width, int height) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, width, height, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	private Color overlayAlphaColor(Color c1, Color c2) {
		double a1 = c1.getAlpha() / 255.0;
		double a2 = c1.getAlpha() / 255.0;
		double r1 = c1.getRed() / 255.0;
		double r2 = c1.getRed() / 255.0;
		double g1 = c1.getGreen() / 255.0;
		double g2 = c1.getGreen() / 255.0;
		double b1 = c1.getBlue() / 255.0;
		double b2 = c1.getBlue() / 255.0;
		int r = (int) (255 * (r1 * a1 + a2 * r2 * (1 - a1)));
		int g = (int) (255 * (g1 * a1 + a2 * g2 * (1 - a1)));
		int b = (int) (255 * (b1 * a1 + a2 * b2 * (1 - a1)));
		return new Color(r, g, b);
	}

	public void repaintBoard() {
		if (em)
			repaint();
		if (ard) {
			commandBuffer.add("E");
			//compress everything into a single string command and then go
			String finalSend = "";
			for(String s : commandBuffer) {
				finalSend += s + " ";
			}
			a.serialWrite(finalSend);
			commandBuffer.clear();
		}
	}

	// Key handling
	public Set<String> getKeys() {
		return keys;
	}

	// JFrame Handling
	public void show() {
		if (em) {
			containerFrame.setVisible(true);

			this.setFocusable(true);
			this.requestFocus();
		}
	}

	private void initFrame() {
		System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
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

		public void keyPressed(KeyEvent arg0) {
			keys.add("" + arg0.getKeyChar());
			try {
				for (KeyRunnable run : keyCallbacks) {
					run.run(arg0);
				}
			} catch (ConcurrentModificationException e) {
				System.out.println("Concurrent Modification Exception in key press listeners!");

			}
		}

		public void keyReleased(KeyEvent arg0) {
			keys.remove("" + arg0.getKeyChar());
			try {
				for (KeyRunnable run : keyCallbacks) {
					run.run(arg0);
				}
			} catch (ConcurrentModificationException e) {
				System.out.println("Concurrent Modification Exception in key press listeners!");
			}
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
		
	}
	public boolean setBrightness(int brightness) {
		a.serialWrite("I" + (char)brightness);
		return true;
	}

}
