package DisplayBoardEmulation.nativeApp;

import DisplayBoardEmulation.discountPongExample.DiscountPongApp;
import DisplayBoardEmulation.tron.TronApp;

public class simpleNavRunner {
	public static void main(String[] args) {
		ApplicationManager manager = new ApplicationManager();
		manager.addNavigatingApplication(new simpleNavApp());
		manager.addApplication(new TronApp());
		manager.addApplication(new DiscountPongApp());
		manager.initialize();
	}
}