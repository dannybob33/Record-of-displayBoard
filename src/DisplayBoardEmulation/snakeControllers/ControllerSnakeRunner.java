package DisplayBoardEmulation.snakeControllers;

import DisplayBoardEmulation.nativeApp.ApplicationManager;

public class ControllerSnakeRunner
{

	public static void main(String[] args)
	{
		ApplicationManager manager = new ApplicationManager();
		manager.addApplication(new SnakeAppControllers());
		manager.initialize();
	}

}
