package DisplayBoardEmulation.webcam;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class WebCamTest {

	public static void main(String[] args) {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		try {
			ImageIO.write(webcam.getImage(), "PNG", new File("H:\\private\\CS2\\test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
