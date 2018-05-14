package GifThing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class Test extends Application {

	@Override
	public void start(DisplayBoard board) {
		while (true) {
			String imagePath = "H:\\private\\CS2\\Memes\\Luke.jpg";
			for (int i = 0; i <= 21; i++) {
				if (i < 10) {
					imagePath = "H:\\private\\CS2\\Memes\\Hyper2\\frame_0" + i + "_delay-0.04s.gif";
				} else {
					imagePath = "H:\\private\\CS2\\Memes\\Hyper2\\frame_" + i + "_delay-0.04s.gif";
				}
				File f = new File(imagePath);
				BufferedImage img = null;
				try {
					img = ImageIO.read(f);
				} catch (IOException e) {
					// e.printStackTrace();
				}
				board.drawImage(img, 0, 0, board.COLS, board.ROWS);
				board.repaintBoard();
				wait(40);
			}
		}
	}

	public void wait(int milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "gifThing";
	}
}
