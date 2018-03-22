package DisplayBoardEmulation.tron;

import DisplayBoardEmulation.nativeApp.ApplicationManager;

public class NewTronRunner {

	public static void main(String[] args) {
		ApplicationManager manager = new ApplicationManager();
		manager.addApplication(new TronApp());
		manager.initialize();
	}

}
