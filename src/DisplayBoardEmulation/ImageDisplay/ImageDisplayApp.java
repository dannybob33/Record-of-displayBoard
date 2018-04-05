package DisplayBoardEmulation.ImageDisplay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class ImageDisplayApp extends Application {
	String imagePath = "H:\\private\\CS2\\Art.jpg";
	
	@Override
	public void start(DisplayBoard board){
		File f = new File(imagePath);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		board.drawImage(i,0,0,board.COLS,board.ROWS);
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
