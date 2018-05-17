package DisplayBoardEmulation.nativeApp;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

public class ApplicationManager {
	private DisplayBoard board;
	//Default application will be application 0.
	private ArrayList<Application> apps;
	private Application currentApplication;
	private boolean allowDefaultReset = true;
	
	private XInputDevice[] devices;
	private boolean justReturned = false;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	private final int updateSpeed = 20;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private int timeSinceChange = 0;
	private final int changeTime = 500;
	
	public ApplicationManager() {
		this(new DisplayBoard());
	}
	
	public ApplicationManager(DisplayBoard board) {
		this.board = board;
		apps = new ArrayList<Application>();
		try {
			devices = XInputDevice.getAllDevices();
			devices[0].addListener(listener);
			future = scheduler.scheduleAtFixedRate(controllerPoll, updateSpeed, updateSpeed, timeUnit);
		} catch (XInputNotLoadedException e1) {
			//do nothing
		}
	}
	
	public void addApplication(Application a) {
		apps.add(a);
	}
	
	public void addNavigatingApplication(Application a) {
		apps.add(a);
		a.setManager(this);
	}
	
	public void initialize() {
		if(apps.size() == 0) {
			System.out.println("No applications have been added!");
			return;
		}
		board.addKeyCallback(keyUpdate);
		board.show();
		//Default application will be application 0.
		currentApplication = apps.get(0);
		currentApplication.start(board);
	}
	
	public final KeyRunnable keyUpdate = new KeyRunnable() {
		public void run(KeyEvent e) {
			if(e.getID() == KeyEvent.KEY_PRESSED && //if key is pressed,
					e.getKeyCode()==27 &&  //and key pressed is escape
								(allowDefaultReset || //if app resetting is allowed
								!currentApplication.equals(apps.get(0)))) {   //or this isnt default app, 
					goToDefaultApp();
			}
		}
	};
	
	public final Runnable controllerPoll = new Runnable() {
		public void run() {
			devices[0].poll();
			timeSinceChange += updateSpeed;
			if(timeSinceChange >= changeTime) {
				justReturned = false;
			}
		}
	};
	
	private void goToDefaultApp() {
		changeApplication(0);
	}
	
	//Returns if default can reset
	public boolean toggleDefaultReset() {
		allowDefaultReset =  !allowDefaultReset;
		return allowDefaultReset;
	}
	
	public void changeApplication(int index) {
		board.clear();
		currentApplication.terminate();
		currentApplication = apps.get(index);
		System.out.println("Application Manager: Changed application to " + currentApplication.getName() + ".");
		currentApplication.start(board);
	}
	
	public ArrayList<Application> getApps() {
		return apps;
	}
	
	public int currentApplicationIndex() {
		return apps.indexOf(currentApplication);
	}
	
	//Controller Listener
	XInputDeviceListener listener = new SimpleXInputDeviceListener() {
		// When a controller is connected while it is running
		@Override
		public void connected() {
			System.out.println("Device Connected!");
		}

		// When a controller is disconnect while it is running
		@Override
		public void disconnected() {
			System.out.println("Device Disconnected!");
		}

		// What to do when any Button Changes
		@Override
		public void buttonChanged(final XInputButton button, final boolean pressed) {
			if(button.equals(XInputButton.BACK) && pressed == true && !justReturned) {
				timeSinceChange = 0;
				goToDefaultApp();
				justReturned = true;
			} else if(button.equals(XInputButton.BACK) && pressed == false && justReturned) {
				justReturned = false;
			}
		}
	};
}
