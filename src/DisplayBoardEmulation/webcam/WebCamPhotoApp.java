package DisplayBoardEmulation.webcam;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.github.sarxos.webcam.Webcam;

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
	
	public WebCamPhotoApp() {
		webcam = Webcam.getDefault();
	}

	@Override
	public void start(DisplayBoard board) {
		this.board = board;
		isRunning = true;
		board.clear();
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
		}
	};

}
