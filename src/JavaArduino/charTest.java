package JavaArduino;

public class charTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int x = 125; x < 140; x++) {
			String s = Character.toString((char)x);
			int y = s.charAt(0);
			for (int i = 256; i >= 1; i /= 2) {
				System.out.print((y & i) == 0 ? 0 : 1);
			}
			System.out.println(" "+x+" "+y+" "+s);
		}
	}
}
