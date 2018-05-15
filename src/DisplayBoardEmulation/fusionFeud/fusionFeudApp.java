package DisplayBoardEmulation.fusionFeud;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.ivan.xinput.XInputDevice; // Class for XInput 1.3. Legacy for Win7.
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;
import com.ivan.xinput.listener.XInputDeviceListener;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class fusionFeudApp extends Application {
	// ------Board Setup---------
	private DisplayBoard board;
	private int timeSpeed = 25;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	// ----------Paddles-----------
	private int paddleWidth = 3;
	private int paddleHeight = 8;

	// Player 1
	private Color p1Color = Color.BLUE;
	private Rectangle p1Location = new Rectangle(1, 18, paddleWidth, paddleHeight);
	private int p1XInc = 0;
	private int p1YInc = 0;

	// Player 2
	private Color p2Color = Color.RED;
	private Rectangle p2Location = new Rectangle(72 - paddleWidth, 18, paddleWidth, paddleHeight);
	private int p2XInc = 0;
	private int p2YInc = 0;

	// -----Bullets-----
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	// -----Start Game------
	private ScheduledFuture<?> future;
	
	@Override
	public void start(DisplayBoard board) {
		// Create and show board
		this.board = board;
		this.board.show();
		// Setup timer
		future = scheduler.scheduleAtFixedRate(update, timeSpeed, timeSpeed, timeUnit);
	}

	// -----Game Function---------
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

						if (button.equals(XInputButton.A) && pressed) {
							bullets.add(new Bullet((p1Location.x)+3,(p1Location.y)+4,1));
						}
					}
				};
				XInputDeviceListener listener2 = new SimpleXInputDeviceListener() {
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

						if (button.equals(XInputButton.A) && pressed) {
							bullets.add(new Bullet((p2Location.x)-3,(p2Location.y)+4,-1));
						}
					}
				};
				device.addListener(listener);
				device2.addListener(listener2);



				// Left stick movements
				int[] xy = leftStickMovement(device);
//				p1XInc = xy[0];
				 p1YInc = xy[1];
				xy = leftStickMovement(device2);
//				p2XInc = xy[0];
				 p2YInc = xy[1];
				// erase the ball
System.out.println("Clear Paddles Start");
				board.colorRect(p1Location, Color.BLACK);
				board.colorRect(p2Location, Color.BLACK);
				System.out.println("Clear Paddles End");

				// ----Move the Paddles----
				// Move the ball according to xInc
				if (p1Location.x + p1XInc < DisplayBoard.COLS - p1Location.width && p1Location.x + p1XInc >= 0) {
					p1Location.x = p1Location.x + p1XInc;
				}
				// Move the ball according to yInc
				if (p1Location.y + p1YInc < DisplayBoard.ROWS - p1Location.height && p1Location.y + p1YInc >= 0) {
					p1Location.y = p1Location.y + p1YInc;
				}
				if (p2Location.x + p2XInc < DisplayBoard.COLS - p2Location.width && p2Location.x + p2XInc >= 0) {
					p2Location.x = p2Location.x + p2XInc;
				}
				// Move the ball according to yInc
				if (p2Location.y + p2YInc < DisplayBoard.ROWS - p2Location.height && p2Location.y + p2YInc >= 0) {
					p2Location.y = p2Location.y + p2YInc;
				}
				

				// -------Move the bullets------
				System.out.println("Bullets start");
				Iterator<Bullet> itr = bullets.iterator();
				while(itr.hasNext()) {
					Bullet bullet = itr.next();
					board.colorRect(bullet.ballLocation, Color.BLACK);
					if(!bullet.move()) {
						itr.remove();
					}
					else {
						board.colorRect(bullet.ballLocation, Color.YELLOW);
					}
				}
				System.out.println("Bullets end");
				// Change the color
				System.out.println("Recolor paddles Start");
				board.colorRect(p1Location, p1Color);
				board.colorRect(p2Location, p2Color);
				System.out.println("Recolor paddles end");
				// repaint the whole board
				board.repaint();
				// poll the controller again
				System.out.println("Poll Start");
				device.poll();
				device2.poll();
				System.out.println("Poll End");
			} catch (XInputNotLoadedException e) {
				e.printStackTrace();
			}
		}

	};

	// The balls that each player shoots at each other
	private class Bullet {
		int ballSize = 2;
		int xInc;
		int yInc = 0;
		int player;
		Rectangle ballLocation;

		public Bullet(int xLoc, int yLoc, int player) {
			ballLocation = new Rectangle(xLoc, yLoc, ballSize, ballSize);
			this.player = player;
			if (this.player == 1) {
				xInc = 1;
			} else {
				xInc = -1;
			}
		}

		public boolean move() {
			if(player == 1) {
				if(ballLocation.x + xInc == p2Location.x -1 && ((ballLocation.y>=p2Location.y && ballLocation.y<=p2Location.y+paddleHeight)||(ballLocation.y+ballSize>=p2Location.y && ballLocation.y+ballSize<=p2Location.y+paddleHeight))) {
					//bounce
					player = 2;
					xInc = -1*xInc;
					ballLocation.x += xInc;
					return true;
				}
				else if (ballLocation.x + xInc < DisplayBoard.COLS - ballSize && ballLocation.x + xInc >= 0) {
					ballLocation.x += xInc;
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if(ballLocation.x + xInc == p1Location.x + paddleWidth&& ((ballLocation.y>=p1Location.y && ballLocation.y<=p1Location.y+paddleHeight)||(ballLocation.y+ballSize>=p1Location.y && ballLocation.y+ballSize<=p1Location.y+paddleHeight))) {
					//bounce
					player = 1;
					xInc = -1*xInc;
					ballLocation.x += xInc;
					return true;
				}
				else if (ballLocation.x + xInc < DisplayBoard.COLS - ballSize && ballLocation.x + xInc >= 0) {
					ballLocation.x += xInc;
					return true;
				}
				else {
					return false;
				}
				
			}
//			ATM this probably doesn't matter because balls only move in the x direction
//			if (ballLocation.y + yInc < DisplayBoard.ROWS - ballLocation.height && ballLocation.y + yInc >= 0) {
//				ballLocation.y = ballLocation.y + yInc;
//			}
		}
	}
	// puts movements into array depending on left stick of given device
	public int[] leftStickMovement(XInputDevice device) {
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

	@Override
	public void terminate() {
		future.cancel(true);		
	}

	@Override
	public String getName() {
		return "Fusion Feud!";
	}
}