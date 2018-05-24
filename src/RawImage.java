import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RawImage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("P2P.png");
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Height: " + i.getHeight());
		System.out.println("Width: " + i.getWidth());
		System.out.println("int[" + i.getHeight() + "][" + i.getWidth() + "][3]={");
		for (int r = 0; r < i.getHeight(); r++) {
			System.out.print("{");
			for (int c = 0; c < i.getWidth(); c++) {
				Color col = new Color(i.getRGB(c, r));
				System.out.print("{" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + "}");
				if (c != i.getWidth()-1) System.out.print(",");
			}
			if (r!=i.getHeight()-1) 
				System.out.println("},");
			else
				System.out.println("}");
		}
		System.out.println("};");

	}

}
