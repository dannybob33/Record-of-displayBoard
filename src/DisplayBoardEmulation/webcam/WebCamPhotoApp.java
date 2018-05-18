package DisplayBoardEmulation.webcam;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.github.sarxos.webcam.Webcam;
import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;
import com.ivan.xinput.listener.XInputDeviceListener;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;
import DisplayBoardEmulation.nativeApp.Application;

public class WebCamPhotoApp extends Application {
	private boolean isRunning = false;
	private DisplayBoard board;
	private Webcam webcam;
	private final int MIDDLE_TEXT = (DisplayBoard.ROWS/2)-4;
	
	//The timer 
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	// all devices
	private XInputDevice[] devices;
	private XInputDevice device1;
	private boolean justTookPhoto = false;
	private int controllerUpdateDelay = 20;
	
	public WebCamPhotoApp() {
		webcam = Webcam.getDefault();
	}

	@Override
	public void start(DisplayBoard board) {
		this.board = board;
		isRunning = true;
		board.clear();
		try {
			devices = XInputDevice.getAllDevices();
			device1 = devices[0];
		} catch (XInputNotLoadedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String toDisplay = "Loading";
		int centeredCol = (DisplayBoard.COLS-board.StringWidth(toDisplay))/2;
		board.drawString(18, centeredCol, Color.RED, toDisplay);
		board.repaintBoard();
		scheduler.schedule(initializeApp, 100, TimeUnit.MILLISECONDS);
	}

	@Override
	public void terminate() {
		isRunning = false;
		webcam.close();
	}

	@Override
	public String getName() {
		return "Web Cam";
	}
	
	public final KeyRunnable keyUpdate = new KeyRunnable() {
		public void run(KeyEvent e) {
			if(isRunning&&e.getID()==KeyEvent.KEY_RELEASED) {
				snap();
			}
		}
	};
	
	public void snap() {
		if(!isRunning) {
			return;
		}
		for (int i=3; i>0; i--) {
			board.clear();
			board.drawString(18, 33, Color.CYAN, ""+i);
			board.repaintBoard();
			wait(1000);
		}
		board.clear();
		board.drawString(18, 37-board.StringWidth("Smile!")/2, Color.CYAN, "Smile!");
		board.repaintBoard();
		BufferedImage takenImage = webcam.getImage();
		
		board.drawImage(takenImage,0,0,board.COLS,board.ROWS);
		board.repaintBoard();
	}
	
	public final Runnable initializeApp = new Runnable() {
		public void run() {
			webcam.open();
			snap();
			if(!board.hasKeyCallback(keyUpdate)) {
				board.addKeyCallback(keyUpdate);
			}
			if(device1 != null) {
				device1.addListener(listener);
				future = scheduler.scheduleAtFixedRate(updateController, 
						controllerUpdateDelay, 
						controllerUpdateDelay, 
						TimeUnit.MILLISECONDS);
			}
		}
	};
	
	public final Runnable updateController = new Runnable() {
		public void run() {
			device1.poll();
		}
	};
	
	XInputDeviceListener listener = new SimpleXInputDeviceListener() {
		// When a controller is connected while it is running
		@Override
		public void connected() {
			// resume the game
			System.out.println("Controller Connected!");
		}

		// When a controller is disconnect while it is running
		@Override
		public void disconnected() {
			// pause the game and display a message
			System.out.println("Controller Disconnected!");
		}

		// What to do when any Button Changes
		@Override
		public void buttonChanged(final XInputButton button, final boolean pressed) {
			// the given button was just pressed (if pressed == true) or released (pressed
			// == false)
			if(!button.equals(XInputButton.BACK) && pressed && !justTookPhoto) {
				snap();
				justTookPhoto = true;
			} else if (!button.equals(XInputButton.BACK) && !pressed && justTookPhoto) {
				justTookPhoto = false;
			}
		}
	};

	private void wait (int millis) {
		long now = System.currentTimeMillis();
		while (System.currentTimeMillis() < now+millis);
		return;
	}
}
