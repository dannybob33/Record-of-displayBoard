package Drawstring;

public class StringRunner {

	public static void main(String[] args) {
		DrawString(4, 0, 0, 1, 1, 1, "jeff");
		System.out.println("after jeff");
	}

	public static void DrawString(int n, int row, int col, int red, int green, int blue, String chars) {
		char[][] charset = charSet.Cset();

		for (int i = 0; i < n; i++) {
			String letter = chars.substring(i, i + 1);

			char[] locs = charset[letter.hashCode()];
			
			int k = 0;
			
			for (int j = 1; j <= 256; j*=2)
			{
				if (j&&locs[k] == j)
				{
					
				}
				
				k++;
			}

		}

	}

}
