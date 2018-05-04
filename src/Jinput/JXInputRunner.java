package Jinput;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ivan.xinput.XInputDevice; // Class for XInput 1.3. Legacy for Win7.
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;
import com.ivan.xinput.listener.XInputDeviceListener;

import DisplayBoardEmulation.emulator.DisplayBoard;

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
				XInputDevice device = XInputDevice.getDeviceFor(0); // or devices[0]
				// use XInputDevice14 if you want to use the XInput 1.4 functions

				XInputDeviceListener listener = new SimpleXInputDeviceListener() {
					@Override
					public void connected() {
						// resume the game
						System.out.println("Game Start");
					}

					@Override
					public void disconnected() {
						// pause the game and display a message
						System.out.println("Game End");
					}

					@Override
					public void buttonChanged(final XInputButton button, final boolean pressed) {
						// the given button was just pressed (if pressed == true) or released (pressed
						// == false)
						if (pressed) {
							System.out.println(button + " was pressed");
						} else {
							System.out.println(button + " was released");
						}
					}
				};

				// whenever the device is polled, listener events will be fired as long as there
				// are changes
				device.poll();
				System.out.println(device.poll());
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
}
