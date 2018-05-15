package JavaArduino;

public class bounce {
	public static void main(String[] args) {
		com c;
		c=new com(9, 73, 43);
		c.clear();
		c.show();
		for (int r=0; r<44; r++) {
			for (int col=0; col<74; col++) {
				c.setPixel(r, col, 192, 128, 255);
				c.show();
			}
		}
		
	}
}
