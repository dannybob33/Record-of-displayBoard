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
	private Color color = Color.YELLOW;
	private int ballSize = 3;
	private Rectangle ballLocation = new Rectangle(40, 20, ballSize, ballSize);
	
	private int xInc = 1;
	private int yInc = 1;
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
			JInputJoystick controller = new JInputJoystick(Controller.Type.STICK, Controller.Type.GAMEPAD);

			EventQueue queue = controller.getController().getEventQueue();
			// Check if the controller was found.
			if (!controller.isControllerConnected()) {
				System.out.println("No controller found!");
				// Do some stuff.
			}
			Event event = new Event();
			board.colorRect(ballLocation, color);
			while (queue.getNextEvent(event)) {
				try {
					Thread.sleep(timeSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Get current state of joystick! And check, if joystick is disconnected.
				if (!controller.pollController()) {
					System.out.println("Controller disconnected!");
					// Do some stuff.
				}
				if (controller.getButtonValue(0)) {
					color = Color.GREEN;
				}

				else if (controller.getButtonValue(1)) {
					color = Color.RED;
				}

				else if (controller.getButtonValue(2)) {
					color = Color.BLUE;
				}

				else if (controller.getButtonValue(3)) {
					color = Color.CYAN;
				} else {
					color = Color.YELLOW;
				}
				
				int rightTrigger = controller.getZAxisPercentage();
				if(rightTrigger < 45) {
					timeSpeed = 2;
				}
				else {
					timeSpeed = 25;
				}

				// Left controller joystick
				int xValuePercentageLeftJoystick = controller.getX_LeftJoystick_Percentage();
				int yValuePercentageLeftJoystick = controller.getY_LeftJoystick_Percentage();

				if (xValuePercentageLeftJoystick > 60) {
					xInc = 1;
				} else if (xValuePercentageLeftJoystick < 40) {
					xInc = -1;
				} else {
					xInc = 0;
				}
				if (yValuePercentageLeftJoystick > 60) {
					yInc = 1;
				} else if (yValuePercentageLeftJoystick < 40) {
					yInc = -1;
				} else {
					yInc = 0;
				}

				System.out.println(xValuePercentageLeftJoystick + ", " + yValuePercentageLeftJoystick);
				board.colorRect(ballLocation, Color.BLACK); // Erase old ball
				

				if (ballLocation.x + xInc < board.COLS - ballLocation.width && ballLocation.x + xInc >= 0) {
					// System.out.println("Move in X by: " + xInc);
					ballLocation.x = ballLocation.x + xInc;
				}

				if (ballLocation.y + yInc < board.ROWS - ballLocation.height && ballLocation.y + yInc >= 0) {
					// System.out.println("Move in Y by: " + yInc);
					ballLocation.y = ballLocation.y + yInc;
				}
				board.colorRect(ballLocation, color); // redraw

				// System.out.println("Ball Location X: " + ballLocation.x);
				// System.out.println("Ball Location y: " + ballLocation.y);
				// System.out.println("yInc: " + yInc);

			}
		}
	};

	public static void main(String[] args) {
		// I just did this since I don't like static variables
		ControllerTestRunner application = new ControllerTestRunner();
		application.start();
	}
}
