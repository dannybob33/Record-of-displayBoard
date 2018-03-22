package DisplayBoardEmulation.emulator;

import java.awt.Color;
import java.awt.Rectangle;

public interface Display {
	public void setPixel(int row, int col, int red, int green, int blue);
	public void setPixel(int row, int col, Color c);
	public Color getPixel(int row, int col);
	
	public void colorRect(int row, int col, int width, int height, int r, int g, int b);
	public void colorRect(int row, int col, int width, int height, Color c);
	public void colorRect(Rectangle rect, Color c);
	public void colorRect(Rectangle rect, int r, int g, int b);
	
	public void clear();
	public boolean isCleared();
	
	public void drawString(int row, int col, int red, int green, int blue, String chars);
	public void drawString(int row, int col, int red, int green, int blue, String chars, int spacing);
	public void drawString(int row, int col, Color c, String chars);
	public void drawString(int row, int col, Color c, String chars, int spacing);
	public void drawString(int n, int row, int col, int red, int green, int blue, String chars);
	public void drawString(int n, int row, int col, int red, int green, int blue, String chars, int spacing);
	public void drawString(int n, int row, int col, Color c, String chars);
	public void drawString(int n, int row, int col, Color c, String chars, int spacing);
	public int StringWidth(String chars);
	public int StringWidth(String chars, int spacing);
}
