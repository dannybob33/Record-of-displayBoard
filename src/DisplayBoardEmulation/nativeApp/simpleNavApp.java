package DisplayBoardEmulation.nativeApp;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	
	private final int MIDDLE_TEXT = (DisplayBoard.ROWS/2)-4;
	
	@Override
	public void start(DisplayBoard board) {
		isRunning = true;
		this.board = board;
		if(!board.hasKeyCallback(keyUpdate)) {
			board.addKeyCallback(keyUpdate);
		}
		if(apps.size() > 1) {
			BufferedImage p2p;
			try {
//			    p2p = ImageIO.read(new File("P2P.jpg"));
//			    board.drawImage(p2p, 0, 3, 67, 44);
//			    board.repaintBoard();
			    Thread.sleep(2000);
			    /*
			    board.drawString(18, centering("Loading..."), Color.RED, "Loading...");
			    board.repaintBoard();
			    Thread.sleep(5000);*/
			    board.clear();
			} catch (Exception e) {
				// ignore problems
			}
			board.drawString(0, centering("Select App"), Color.RED, "Select App");
			board.drawString(8, centering("With WASD"), Color.RED, "With WASD");
			board.drawString(16, centering("Keys"), Color.RED, "Keys");
		} else {
			board.drawString(0, centering("No Apps"), Color.RED, "No Apps");
			board.drawString(8, centering("To Select!"), Color.RED, "To Select!");
		}
		board.repaintBoard();
	}

	@Override
	public void terminate() {
		isRunning = false;
	}
	
	public final KeyRunnable keyUpdate = new KeyRunnable() {
		public void run(KeyEvent e) {
			if(isRunning && apps.size() > 1) {
				if(e.getID() != KeyEvent.KEY_PRESSED) {
					return;
				}
				if(e.getKeyCode() == RIGHT_CODE) {
					currentIndex += 1;
					if(currentIndex >= apps.size()) currentIndex = 1;
					selectApp(currentIndex);
				} else if(e.getKeyCode() == LEFT_CODE) {
					currentIndex -= 1;
					if(currentIndex < 1) currentIndex = apps.size()-1;
					selectApp(currentIndex);
				} else if (e.getKeyCode() == GO_CODE) {
					manager.changeApplication(currentIndex);
				}
			}
		}
	};
	
	private void selectApp(int index) {
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

}
