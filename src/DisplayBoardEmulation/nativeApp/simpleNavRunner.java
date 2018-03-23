package DisplayBoardEmulation.nativeApp;

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
		//Start program
		manager.initialize();
	}
}