package DisplayBoardEmulation.tron;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;
import com.ivan.xinput.listener.XInputDeviceListener;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;
import DisplayBoardEmulation.nativeApp.Application;

public class TronAppControllers extends Application {
	private static final String NAME = "Tron";
	
	private Player p1;
	private Player p2;
	
	//Time stuff
	private final int timeSpeed = 100;
	private final int endMultiplier = 20;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	//The board object
	private DisplayBoard board;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	private boolean gameEnd;
	
	private boolean isRunning = false;
	
	// retrieve all devices
	XInputDevice[] devices;
	XInputDevice device1;
	XInputDevice device2;
		
	public void start(DisplayBoard board) {
		isRunning = true;
		
		//Set board variable
		this.board = board;
		
		gameEnd = false;
		
		//Controller stuff
		try {
			devices = XInputDevice.getAllDevices();
			device1 = devices[0];
			device2 = devices[1];
			device1.addListener(listener);
			device2.addListener(listener);
		} catch (XInputNotLoadedException e) {
			e.printStackTrace();
		}
		
		//Make players
		p1 = new Player(Color.RED, new Color(192,0,0),22,9,6);
		p2 = new Player(Color.GREEN, new Color(0,192,0),22,66,4);
		
		//Setup timer
		future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
		printLine("Game Started!");
	}
	
	public final Runnable gameUpdate = new Runnable() {
		public void run() {
			p1.changedDir = false;
			p2.changedDir = false;
			device1.poll();
			device2.poll();
			double[] p2Axes = getAxes(device2);
			System.out.println(p2Axes[0] + " " + p2Axes[1]);
			int p1Direction = getDirection(device1);
			if(p1Direction != 0)
				p1.changeDirection(p1Direction);
			
			int p2Direction = getDirection(device2);
			if(p2Direction != 0)
				p2.changeDirection(p2Direction);
			
			if(!gameEnd && isRunning) {
				movePlayer(p1);
			}
			if(!gameEnd && isRunning) {
				movePlayer(p2);
			}
			board.repaintBoard();
		}
	};

	private final Runnable reset = new Runnable() {
		public void run() {
			initializePlayers();
			gameEnd = false;
			while(!board.isCleared()) {
				board.clear();
			}
			board.repaintBoard();
			future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
			printLine("Game Started!");
		}
	};
	
	private void movePlayer(Player p) {
		if(gameEnd || !isRunning) {
			return;
		}
		int r,c;
		if(p.getDirection() == 8) {
			r=p.getRow()-1;c=p.getCol();
		} else if (p.getDirection() == 6) {
			r=p.getRow();c=p.getCol()+1;
		} else if (p.getDirection() == 4) {
			r=p.getRow();c=p.getCol()-1;
		} else {
			r=p.getRow()+1;c=p.getCol();
		}
		if(checkPositionEmpty(r,c)) {
			board.setPixel(p.getRow(),p.getCol(),p.TRAIL_COLOR);
			board.setPixel(r,c,p.TRAIL_COLOR);
			p.setRow(r);
			p.setCol(c);
		} else {
			if(!gameEnd && isRunning) {
				gameEnd = true;
				if(p.equals(p1)) {
					board.colorRect(0,0,DisplayBoard.COLS+1,DisplayBoard.ROWS+1,p2.PLAYER_COLOR);
				} else {
					board.colorRect(0,0,DisplayBoard.COLS+1,DisplayBoard.ROWS+1,p1.PLAYER_COLOR);
				}
				board.repaintBoard();
				future.cancel(true);
				scheduler.schedule(reset, timeSpeed * endMultiplier, timeUnit);
				printLine("Game Ended!");
			}
		}
	}
	
	private void initializePlayers() {
		p1.setRow(22);
		p1.setCol(9);
		p1.setDirection(6);
		
		p2.setRow(22);
		p2.setCol(66);
		p2.setDirection(4);
	}
	
	public boolean checkPositionEmpty(int row, int col) {
		if(row<0 || row>=DisplayBoard.ROWS || col<0 || col>=DisplayBoard.COLS) {
			return false;
		}
		if(board.getPixel(row, col).equals(Color.BLACK)) {
			return true;
		}
		return false;
	}

	@Override
	public void terminate() {
		isRunning = false;
		future.cancel(true);
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	//puts movements into array depending on left stick of given device
		public double[] getAxes(XInputDevice device) {
			double[] xy = new double[2];
			xy[0] = device.getComponents().getAxes().ly;
			xy[1] =  -1 * device.getComponents().getAxes().lx;
			return xy;
		}
		
		private int getDirection(XInputDevice device) {
			double[] axes = getAxes(device);
			if(Math.abs(axes[0]) > Math.abs(axes[1])) {
				if(axes[0] > 0.5) {
					return 8;
				} else if(axes[0] < -0.5){
					return 2;
				} else {
					return 0;
				}
			} else {
				if(axes[1] > 0.5) {
					return 4;
				} else if(axes[1] < -0.5){
					return 6;
				} else {
					return 0;
				}
			}
		}
		
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

				System.out.println("Button Pressed!");
			}
		};
}
