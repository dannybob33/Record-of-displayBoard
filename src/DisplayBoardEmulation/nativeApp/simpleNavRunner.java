package DisplayBoardEmulation.nativeApp;

import DisplayBoardEmulation.ImageDisplay.ImageDisplayApp;
import DisplayBoardEmulation.discountPongExample.DiscountPongApp;
import DisplayBoardEmulation.tron.TronApp;

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
		//Start program
		manager.initialize();
	}
}