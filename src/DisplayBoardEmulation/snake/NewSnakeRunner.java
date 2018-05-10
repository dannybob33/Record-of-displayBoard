package DisplayBoardEmulation.snake;

import DisplayBoardEmulation.nativeApp.ApplicationManager;

public class NewSnakeRunner
{

	public static void main(String[] args)
	{
		ApplicationManager manager = new ApplicationManager();
		manager.addApplication(new SnakeApp());
		manager.initialize();
	}

}
