package DisplayBoardEmulation.nativeApp;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;

public class ApplicationManager {
	private DisplayBoard board;
	//Default application will be application 0.
	private ArrayList<Application> apps;
	private Application currentApplication;
	private boolean allowDefaultReset = true;
	
	public ApplicationManager() {
		this(new DisplayBoard());
	}
	
	public ApplicationManager(DisplayBoard board) {
		this.board = board;
		apps = new ArrayList<Application>();
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
}
