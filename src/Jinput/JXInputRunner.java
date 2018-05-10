package Jinput;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ivan.xinput.XInputComponentsDelta;
import com.ivan.xinput.XInputDevice; // Class for XInput 1.3. Legacy for Win7.
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;
import com.ivan.xinput.listener.XInputDeviceListener;

import DisplayBoardEmulation.emulator.DisplayBoard;
import de.hardcode.jxinput.test.AxisListener;

public class JXInputRunner {
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
			try {
				// retrieve all devices
				XInputDevice[] devices = XInputDevice.getAllDevices();
				// retrieve the device for player 1
				XInputDevice device = devices[0];
				XInputDevice device2 = devices[1];
				// Listens for Button Changes
				XInputDeviceListener listener = new SimpleXInputDeviceListener() {
					// When a controller is connected while it is running
					@Override
					public void connected() {
						// resume the game
						System.out.println("Game Start");
					}

					// When a controller is disconnect while it is running
					@Override
					public void disconnected() {
						// pause the game and display a message
						System.out.println("Game End");
					}

					// What to do when any Button Changes
					@Override
					public void buttonChanged(final XInputButton button, final boolean pressed) {
						// the given button was just pressed (if pressed == true) or released (pressed
						// == false)

						if (button.equals(button.A) && pressed) {
							color = Color.GREEN;
						} else {
							color = Color.YELLOW;
						}
					}
				};

				// whenever the device is polled, listener events will be fired as long as there
				// are changes
				device2.addListener(listener);
				// Left stick movements
				int[] xy = moveBall(device);
				xInc = xy[0];
				yInc = xy[1];
				// erase the ball
				board.colorRect(ballLocation, Color.BLACK);
				// Move the ball according to xInc
				if (ballLocation.x + xInc < board.COLS - ballLocation.width && ballLocation.x + xInc >= 0) {
					ballLocation.x = ballLocation.x + xInc;
				}
				// Move the ball according to yInc
				if (ballLocation.y + yInc < board.ROWS - ballLocation.height && ballLocation.y + yInc >= 0) {
					ballLocation.y = ballLocation.y + yInc;
				}
				// Change the color
				board.colorRect(ballLocation, color);
				// repaint the whole board
				board.repaint();
				// poll the controller again
				device.poll();
				device2.poll();
			} catch (XInputNotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	public static void main(String[] args) {
		// I just did this since I don't like static variables
		JXInputRunner application = new JXInputRunner();
		application.start();
	}
	//puts movements into array depending on left stick of given device
	public int[] moveBall(XInputDevice device) {
		int[] xy = new int[2];
		if (device.getComponents().getAxes().lx < -.5) {
			xy[0] = -1;
		} else if (device.getComponents().getAxes().lx > .5) {
			xy[0] = 1;
		} else {
			xy[0] = 0;
		}
		if (device.getComponents().getAxes().ly < -.5) {
			xy[1] = 1;
		} else if (device.getComponents().getAxes().ly > .5) {
			xy[1] = -1;
		} else {
			xy[1] = 0;
		}
		return xy;
	}
}