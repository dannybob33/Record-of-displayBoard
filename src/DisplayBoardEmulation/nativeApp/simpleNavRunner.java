package DisplayBoardEmulation.nativeApp;

import DisplayBoardEmulation.ImageDisplay.GalleryApp;
import DisplayBoardEmulation.ImageDisplay.ImageDisplayApp;
import DisplayBoardEmulation.discountPongExample.DiscountPongApp;
import DisplayBoardEmulation.fusionFeud.fusionFeudApp;
import DisplayBoardEmulation.snake.SnakeApp;
import DisplayBoardEmulation.sorting.SortingApp;
import DisplayBoardEmulation.tron.TronApp;
import DisplayBoardEmulation.webcam.WebCamPhotoApp;
import GifThing.Jedi;

public class simpleNavRunner {
	public static void main(String[] args) {
		//Make Manager
		ApplicationManager manager = new ApplicationManager();
		//Add navigator
		manager.addNavigatingApplication(new simpleNavApp());
		//Add other apps
		manager.addApplication(new TronApp());
		manager.addApplication(new DiscountPongApp());
		manager.addApplication(new ImageDisplayApp());
		manager.addApplication(new GalleryApp());
		manager.addApplication(new SortingApp());
		manager.addApplication(new WebCamPhotoApp());
		manager.addApplication(new SnakeApp());
		manager.addApplication(new Jedi());
		manager.addApplication(new fusionFeudApp());
		//Start program
		manager.initialize();
	}
}