
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
	private int timeSpeed = 250;
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
			Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
			if (controllers.length == 0) {
				System.out.println("Found no controllers.");
				System.exit(0);
			}
			for (int i = 0; i < controllers.length; i++) {
				controllers[i].poll();// unsure of what this does
				EventQueue queue = controllers[i].getEventQueue();// the things that are happening on the controller
				Event event = new Event();// Create and event. This means that we can use this later to make things move
											// ????
				while (queue.getNextEvent(event) && updatePos != 0) {// while there are events in the controller, and
																		// updatePos is not null..
					// Erase ball
					board.colorRect(ballLocation.y, ballLocation.x, ballSize, ballSize, Color.BLACK);

					// Update ball location

					updatePos = event.getValue();// when buttons are being pushed, this is not zero (!= 0)
					Component comp = event.getComponent();// every button is a component
					System.out.println(comp.getName());
					if (comp.getName().equals("X Rotation")) {// if the thing that updated "updatePos" to a nonzero is
																// "X Rotation"
						if (updatePos > 0)
							xInc = 1;
						if (updatePos < 0)
							xInc = -1;
						System.out.println("X Axis Dominant. xInc: " + xInc + " yInc: " + yInc);
						ballLocation = new Rectangle(ballLocation.x + xInc, ballLocation.y + yInc, ballLocation.width,
								ballLocation.height);
					} else if (comp.getName().equals("Y Rotation")) {
						if (updatePos > 0)
							yInc = 1;
						if (updatePos < 0)
							yInc = -1;

						System.out.println("Y Axis Dominant. xInc: " + xInc + " yInc: " + yInc);
						ballLocation = new Rectangle(ballLocation.x + xInc, ballLocation.y + yInc, ballLocation.width,
								ballLocation.height);
					}

				}
				ballLocation = new Rectangle(ballLocation.x + xInc, ballLocation.y + yInc, ballLocation.width,
						ballLocation.height);
				// Update ball direction
				if (ballLocation.x <= 0 || ballLocation.x >= DisplayBoard.COLS - ballSize)
					xInc *= -1; // x direction
				if (ballLocation.y <= 0 || ballLocation.y >= DisplayBoard.ROWS - ballSize)
					yInc *= -1; // y direction
				// Draw ball at new location
				board.colorRect(ballLocation, Color.YELLOW); // left paddle
			}
		}
	};

	public static void main(String[] args) {
		// I just did this since I don't like static variables
		ControllerTestRunner application = new ControllerTestRunner();
		application.start();
	}
}
