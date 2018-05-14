package GifThing;

import DisplayBoardEmulation.nativeApp.ApplicationManager;

public class gifRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationManager manager = new ApplicationManager();
		manager.addApplication(new Jedi());
		manager.initialize();
	}

}
