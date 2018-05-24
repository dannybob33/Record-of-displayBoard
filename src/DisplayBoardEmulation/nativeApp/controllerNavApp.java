package DisplayBoardEmulation.nativeApp;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;
import com.ivan.xinput.listener.XInputDeviceListener;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;

public class controllerNavApp extends Application {
	private boolean isRunning = false;
	private DisplayBoard board;
	private ApplicationManager manager;
	private ArrayList<Application> apps;
	private int currentIndex;
	
	private final int RIGHT_CODE = 65;
	private final int LEFT_CODE = 68;
	private final int GO_CODE = 10;
	
	//Controller things
	private XInputDevice[] devices;
	private XInputDevice device0;
	private boolean hasFoundDevice = false;
	private boolean hasChangedApp = false;
	
	private final int MIDDLE_TEXT = (DisplayBoard.ROWS/2)-4;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	private final int updateSpeed = 20;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	private int timeSinceChange = 0;
	private final int changeTime = 500;
	
	@Override
	public void start(DisplayBoard board) {
		isRunning = true;
		this.board = board;
		try {
			devices = XInputDevice.getAllDevices();
			device0 = devices[0];
			hasFoundDevice = true;
			device0.addListener(listener);
		} catch (XInputNotLoadedException e1) {
			//do nothing
		}
		if(apps.size() > 1) {
			BufferedImage p2p;
			board.clear();
			try {
			    p2p = ImageIO.read(new File("P2P.jpg"));
			    board.drawImage(p2p, 0, 3, 67, 44);
			    board.repaintBoard();
			    Thread.sleep(3000);
			    board.clear();
			} catch (Exception e) {
				// ignore problems
			}
			board.drawString(0, centering("Select App"), Color.RED, "Select App");
			board.drawString(8, centering("With Left"), Color.RED, "With Left");
			board.drawString(16, centering("Stick"), Color.RED, "Stick");
		} else {
			board.drawString(0, centering("No Apps"), Color.RED, "No Apps");
			board.drawString(8, centering("To Select!"), Color.RED, "To Select!");
		}
		board.repaintBoard();
		future = scheduler.scheduleAtFixedRate(controllerPoll,updateSpeed,updateSpeed,timeUnit);
	}

	@Override
	public void terminate() {
		isRunning = false;
	}
	
	public final Runnable controllerPoll = new Runnable() {
		public void run() {
			if(!isRunning) {
				future.cancel(true);
				return;
			}
			if(!hasFoundDevice || device0 == null) {
				try {
					devices = XInputDevice.getAllDevices();
					hasFoundDevice = true;
					device0 = devices[0];
					device0.addListener(listener);
				} catch (XInputNotLoadedException e1) {
					hasFoundDevice = false;
				}
			}
			device0.poll();
			if(isRunning && apps.size() > 1 && hasFoundDevice) {
				timeSinceChange += updateSpeed;
				if(devices.length <= 0) {
					hasFoundDevice = false;
					return;
				}
				double leftX = device0.getComponents().getAxes().lx;
				if(leftX >= 0.5 && !hasChangedApp) {
					currentIndex += 1;
					if(currentIndex >= apps.size()) currentIndex = 1;
					selectApp(currentIndex);
					hasChangedApp = true;
					timeSinceChange = 0;
				} else if(leftX <= -0.5 && !hasChangedApp) {
					currentIndex -= 1;
					if(currentIndex < 1) currentIndex = apps.size()-1;
					selectApp(currentIndex);
					hasChangedApp = true;
					timeSinceChange = 0;
				} else if (timeSinceChange >= changeTime) {
					hasChangedApp = false;
				} else if (leftX > -0.5 && leftX < 0.5 && hasChangedApp) {
					hasChangedApp = false;
				}
			}
		}
	};
	
	private void selectApp(int index) {
		if(!isRunning) {
			return;
		}
		Application app = apps.get(index);
		board.clear();
		printLine("Selected app is: " + app.getName());
		board.drawString(MIDDLE_TEXT, centering(app.getName()), Color.RED, app.getName());
		board.repaintBoard();
	}
	
	private int centering(String text) {
		int width = board.StringWidth(text);
		System.out.println(width);
		return (DisplayBoard.COLS-width)/2;
	}

	@Override
	public String getName() {
		return"Navigator";
	}
	
	public void setManager(ApplicationManager m) {
		manager = m;
		apps = m.getApps();
		currentIndex = 1;
	}
	
	XInputDeviceListener listener = new SimpleXInputDeviceListener() {
		// When a controller is connected while it is running
		@Override
		public void connected() {
			//Do nothing
		}

		// When a controller is disconnect while it is running
		@Override
		public void disconnected() {
			//Do nothing
		}

		// What to do when any Button Changes
		@Override
		public void buttonChanged(final XInputButton button, final boolean pressed) {
			if(!isRunning) {
				return;
			}
			if((button.equals(XInputButton.A) || button.equals(XInputButton.START)) && pressed==true) {
				manager.changeApplication(currentIndex);
			}
		}
	};

}
