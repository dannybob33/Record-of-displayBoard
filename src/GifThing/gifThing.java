package GifThing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class gifThing extends Application {

	@Override
	public void start(DisplayBoard board) {
			for (int i = 0; i <= 32; i++) {
				String imagePath = "vader.jpg";
				if (i < 10) {
					imagePath = "H:\\private\\CS2\\Memes\\JediGif\\frame_0" + i + "_delay-0.1s.gif";
				} else {
					imagePath = "H:\\private\\CS2\\Memes\\JediGif\\frame_" + i + "_delay-0.1s.gif";
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
				wait(50);
			}
			for (int x = 0; x < 3; x++) {
				for (int i = 0; i <= 74; i++) {
					String imagePath = "vader.jpg";
					if (i < 10) {
						imagePath = "H:\\private\\CS2\\Memes\\LoopingGif\\frame_0" + i + "_delay-0.06s.gif";
					} else {
						imagePath = "H:\\private\\CS2\\Memes\\LoopingGif\\frame_" + i + "_delay-0.06s.gif";
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
					wait(10);
				}
			}
			for (int i = 0; i <= 22; i++) {
				String imagePath = "vader.jpg";
				if (i < 10) {
					imagePath = "H:\\private\\CS2\\Memes\\AvatarGif\\frame_0" + i + "_delay-0.17s.gif";
				} else {
					imagePath = "H:\\private\\CS2\\Memes\\AvatarGif\\frame_" + i + "_delay-0.17s.gif";
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
				wait(50);
			}
			for (int i = 0; i <= 19; i++) {
				String imagePath = "vader.jpg";
				if (i < 10) {
					imagePath = "H:\\private\\CS2\\Memes\\PokemonGif\\frame_0" + i + "_delay-0.1s.gif";
				} else {
					imagePath = "H:\\private\\CS2\\Memes\\PokemonGif\\frame_" + i + "_delay-0.1s.gif";
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
				wait(50);
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
