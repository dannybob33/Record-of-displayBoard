
//This isn't working yet don't mind this.
package Jinput;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
			Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
			if (controllers.length == 0) {
				System.out.println("Found no controllers.");
				System.exit(0);
			}
			for (int i = 0; i < controllers.length; i++) {
				controllers[i].poll();
				EventQueue queue = controllers[i].getEventQueue();
				Event event = new Event();
				while (queue.getNextEvent(event)) {
					// Erase ball
					board.colorRect(ballLocation.y, ballLocation.x, ballSize, ballSize, Color.BLACK);
					// Draw paddles

					// Update ball location
					Component comp = event.getComponent();
					

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
