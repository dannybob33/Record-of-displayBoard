
//This isn't working yet don't mind this.
package Jinput;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerTestRunner {
	// ball stuff
	private int ballSize = 3;
	private Rectangle ballLocation = new Rectangle(0, 0, ballSize, ballSize);
	private int xInc = 0;
	private int yInc = 0;
	public static float updatePos = 1;
	// Time stuff
	private int timeSpeed = 25;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	// The board object
	private DisplayBoard board;

	// The timer
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public void start() {
		// Create and show board
		board = new DisplayBoard();
		board.show();
		// Setup timer
		scheduler.scheduleAtFixedRate(update, timeSpeed, timeSpeed, timeUnit);
	}

	public final Runnable update = new Runnable() {
		public void run() {
			
			// First you need to create controller.
			JInputJoystick joystick = new JInputJoystick(Controller.Type.STICK, Controller.Type.GAMEPAD);
			EventQueue queue = joystick.getController().getEventQueue();
			// Check if the controller was found.
			if (!joystick.isControllerConnected()) {
				System.out.println("No controller found!");
				// Do some stuff.
			}
			Event event = new Event();
			ballLocation = new Rectangle(ballLocation.x + xInc,ballLocation.y + yInc,ballLocation.width,ballLocation.height);
			board.colorRect(ballLocation, Color.YELLOW); 
			while (queue.getNextEvent(event)) {


				// Get current state of joystick! And check, if joystick is disconnected.
				if (!joystick.pollController()) {
					System.out.println("Controller disconnected!");
					// Do some stuff.
				}

				// Right controller joystick
				int xValuePercentageRightJoystick = joystick.getX_RightJoystick_Percentage()/joystick.getX_RightJoystick_Percentage();
				int yValuePercentageRightJoystick = joystick.getY_RightJoystick_Percentage()/joystick.getY_RightJoystick_Percentage();

				xInc = xValuePercentageRightJoystick;
				yInc = yValuePercentageRightJoystick;
				System.out.println("xInc: " + xInc);
				System.out.println("yInc: " + yInc);
				board.colorRect(ballLocation, Color.BLACK); 
				ballLocation = new Rectangle(ballLocation.x + xInc,ballLocation.y + yInc,ballLocation.width,ballLocation.height);
				board.colorRect(ballLocation, Color.YELLOW); 
				System.out.println("Ball Location X: " + ballLocation.x);
				System.out.println("Ball Location y: " + ballLocation.y);
				System.out.println("yInc: " + yInc);
				//Update ball direction
				if(ballLocation.x <= 0 || ballLocation.x >= DisplayBoard.COLS-ballSize) xInc *= -1; //x direction
				if(ballLocation.y <= 0 || ballLocation.y >= DisplayBoard.ROWS-ballSize) yInc *= -1; //y direction
				//Draw ball at new location
				board.colorRect(ballLocation, Color.YELLOW);
				
				
			}
		}
	};

	public static void main(String[] args) {
		// I just did this since I don't like static variables
		ControllerTestRunner application = new ControllerTestRunner();
		application.start();
	}
}
