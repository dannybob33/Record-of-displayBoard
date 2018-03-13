package DisplayBoardEmulation.emulator;
import java.awt.Color;
import java.awt.Rectangle;

public class Pixel {
		private Rectangle pixelRect;
		private Color pixelColor;
		
		public Pixel(Rectangle rect, Color color) {
			pixelColor = color;
			pixelRect = rect;
		}
		
		public void setPixelColor(int rgb) {
			pixelColor = new Color(rgb);
		}
		
		public void setPixelColor(Color c) {
			pixelColor = c;
		}
		
		public int getPixelRGB() {
			return pixelColor.getRGB();
		}
		
		public Color getPixelColor() {
			return pixelColor;
		}
		
		public Rectangle getPixelRect() {
			return pixelRect;
		}
		
		public static int getRed(int rgb) {
			return (rgb>>16) & 0xFF;
		}
		
		public static int getGreen(int rgb) {
			return (rgb>>8) & 0xFF;
		}
		
		public static int getBlue(int rgb) {
			return (rgb>>0) & 0xFF;
		}
}