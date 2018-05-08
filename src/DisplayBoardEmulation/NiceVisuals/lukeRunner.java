package DisplayBoardEmulation.NiceVisuals;

import DisplayBoardEmulation.nativeApp.ApplicationManager;
import DisplayBoardEmulation.tron.TronApp;

public class lukeRunner {

	public static void main(String[] args) {
		ApplicationManager manager = new ApplicationManager();
		manager.addApplication(new SquadTeamActualAesthetic());
		manager.initialize();
	}

}
