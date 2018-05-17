package DisplayBoardEmulation.WinXP.uApps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class fine extends Application {
	public void start(DisplayBoard board){
		try {
			board.drawImage(ImageIO.read(new File("WinXP/fine.jpg")), 0, board.COLS/6, board.COLS-(board.COLS/3), board.ROWS);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		board.repaintBoard();
	}

	public void terminate() {
		
	}

	public String getName() {
		return "Secret1";
	}

}
