package DisplayBoardEmulation.WinXP.uApps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class NO extends Application {

	public void start(DisplayBoard board){
		try {
			board.drawImage(ImageIO.read(new File("WinXP/no.jpg")), 0, 0, board.COLS, board.ROWS);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		board.repaintBoard();
	}

	public void terminate() {
		
	}

	public String getName() {
		return "NO";
	}

}
