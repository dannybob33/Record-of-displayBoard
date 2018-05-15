package GifThing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class Jedi extends Application {
	private boolean isRunning = false;
	private DisplayBoard board;
	private int timeSpeed = 75;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private int currImg = 0;

	private ScheduledFuture<?> future;

	@Override
	public void start(DisplayBoard board) {
		// Create and show board
		this.board = board;
		this.board.show();
		isRunning = true;
		// Setup timer
		future = scheduler.scheduleAtFixedRate(update, timeSpeed, timeSpeed, timeUnit);
	}

	public final Runnable update = new Runnable() {
		public void run() {
			if (isRunning) {
				String imagePath = "";
				if (currImg < 10) {
					imagePath = "Jedi/frame_0" + currImg + "_delay-0.1s.gif";
				} else {
					imagePath = "Jedi/frame_" + currImg + "_delay-0.1s.gif";
				}
				File f = new File(imagePath);
				BufferedImage img = null;
				try {
					img = ImageIO.read(f);
				} catch (IOException e) {
					// e.printStackTrace();
				}
				board.drawImage(img, 0, 0, board.COLS, board.ROWS);
				board.repaintBoard();
				currImg++;
				if (currImg > 32) {
					currImg = 0;
				}
			}
		}
	};

	public void wait(int milli) {
		if (isRunning) {
			try {
				Thread.sleep(milli);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		isRunning = false;
	}

	@Override
	public String getName() {
		return "Jedi App";
	}
}
