package DisplayBoardEmulation.NO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

//begin hettsys face
public class NO extends Application {
	//CS2 Images:
	//Beginning: H:\\private\\CS2\\
	//Hackerman.jpg - Hackerman Image
	//HashBrowns1.jpg - Hash Browns
	//HashBrowns2.jfif - Hash Browns
	//Bee.png - Bee Alpha testing
	//Hue_alpha_falloff.png - More Alpha Testing
	String imagePath = "WinXP/no.jpg";//NO
	
	@Override
	public void start(DisplayBoard board){
		//Load image as path
		File f = new File(imagePath);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Initial Image has Alpha? " + i.getColorModel().hasAlpha());
		board.drawImage(i,0,0,board.COLS,board.ROWS);
		board.repaintBoard();
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Image Thing";
	}

}
