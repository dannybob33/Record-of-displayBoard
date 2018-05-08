package DisplayBoardEmulation.ImageDisplay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class GalleryApp extends Application {
	private static final String NAME = "Gallery";
	
	//Image Stuff
	private String directory_path = "H:\\private\\CS2";
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	private int currentImageIndex = 0;
	
	static final String[] EXTENSIONS = new String[]{
	        "gif", "png", "bmp", "jfif", "jpg"
	};
	
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
	
	//Time stuff
	private final int galleryChangeSpeed = 10000;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private DisplayBoard board;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	private boolean isRunning = false;
	
	@Override
	public void start(DisplayBoard board) {
		this.board = board;
		isRunning = true;
		//Get all Images
		File dir = new File(directory_path);
		if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(f);
                    images.add(img);
                } catch (final IOException e) {
                    this.printLine("IOException in image input!");
                }
            }
        } else {
        	this.printLine("Path Specified is not a valid directory!");
        }
		updateImage();
		future = scheduler.scheduleAtFixedRate(imageUpdate, galleryChangeSpeed, galleryChangeSpeed, timeUnit);
		printLine("Started Gallery");
		
	}
	
	private void updateImage() {
		if(isRunning) {
			board.drawImage(images.get(currentImageIndex), 0, 0, board.COLS, board.ROWS);
			board.repaintBoard();
		}
	}
	
	public final Runnable imageUpdate = new Runnable() {
		public void run() {
			if(isRunning) {
				currentImageIndex += 1;
				if(currentImageIndex >= images.size()) {
					currentImageIndex = 0;
				}
				updateImage();
			}
		}
	};

	@Override
	public void terminate() {
		isRunning = false;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
