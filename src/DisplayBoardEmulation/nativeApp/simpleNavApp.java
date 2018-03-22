package DisplayBoardEmulation.nativeApp;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;

public class simpleNavApp extends Application {
	private boolean isRunning = false;
	private DisplayBoard board;
	private ApplicationManager manager;
	private ArrayList<Application> apps;
	private int currentIndex;
	
	private final int RIGHT_CODE = 65;
	private final int LEFT_CODE = 68;
	private final int GO_CODE = 10;
	
	@Override
	public void start(DisplayBoard board) {
		isRunning = true;
		this.board = board;
		if(!board.hasKeyCallback(keyUpdate)) {
			board.addKeyCallback(keyUpdate);
		}
	}

	@Override
	public void terminate() {
		isRunning = false;
	}
	
	public final KeyRunnable keyUpdate = new KeyRunnable() {
		public void run(KeyEvent e) {
			if(isRunning) {
				if(e.getID() != KeyEvent.KEY_PRESSED) {
					return;
				}
				if(e.getKeyCode() == RIGHT_CODE) {
					currentIndex += 1;
					if(currentIndex >= apps.size()) currentIndex = 0;
					printLine("Selected app is: " + apps.get(currentIndex).getName());
				} else if(e.getKeyCode() == LEFT_CODE) {
					currentIndex -= 1;
					if(currentIndex < 0) currentIndex = apps.size()-1;
					printLine("Selected app is: " + apps.get(currentIndex).getName());
				} else if (e.getKeyCode() == GO_CODE) {
					manager.changeApplication(currentIndex);
				}
			}
		}
	};

	@Override
	public String getName() {
		return"SimpleNavigator";
	}
	
	public void setManager(ApplicationManager m) {
		manager = m;
		apps = m.getApps();
		currentIndex = m.currentApplicationIndex();
	}

}
