package DisplayBoardEmulation.nativeApp;

import DisplayBoard.weather.weatherTest;
import DisplayBoardEmulation.ImageDisplay.GalleryApp;
import DisplayBoardEmulation.WinXP.WinXP;
import DisplayBoardEmulation.fusionFeud.fusionFeudApp;
import DisplayBoardEmulation.snake.SnakeApp;
import DisplayBoardEmulation.snakeControllers.SnakeAppControllers;
import DisplayBoardEmulation.sorting.SortingApp;
import DisplayBoardEmulation.tron.TronApp;
import DisplayBoardEmulation.tron.TronAppControllers;
import DisplayBoardEmulation.webcam.WebCamPhotoApp;

public class controllerNavRunner {
	public static void main(String[] args) {
		//Make Manager
		ApplicationManager manager = new ApplicationManager();
		//Add navigator
		manager.addNavigatingApplication(new controllerNavApp());
		//Add other apps
		manager.addApplication(new TronAppControllers());
		manager.addApplication(new SnakeAppControllers());
		manager.addApplication(new SortingApp());
		manager.addApplication(new weatherTest());
		//Start program
		manager.initialize();
	}
}
