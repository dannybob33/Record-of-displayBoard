package DisplayBoardEmulation.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class SortingApp extends Application {

	private DisplayBoard board;
	
	private boolean isRunning = false;
	
	private int HEIGHT = 34;
	
	private double[][] sortingArr = new double[HEIGHT][board.COLS];
	private ArrayList<SortingAlgorithm> sorterList = new ArrayList<SortingAlgorithm>();
	
	private final int updateSpeed = 10;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	public final Runnable updateAll = new Runnable() {
		public void run() {
			if(isRunning) {
				for(SortingAlgorithm sorter : sorterList) {
					sorter.update();
					sorter.paint();
				}
			}
		}
	};
	
	@Override
	public void start(DisplayBoard board) {
		this.board = board;
		isRunning = true;
		for(int i = 0;i<sortingArr.length;i++) {
			double currentColor = 0;
			double colorInc = 255.0/(board.COLS-1);
			ArrayList<Double> d = new ArrayList<Double>();
			for(int j = 0;j<sortingArr[i].length;j++) {
				if(currentColor>255.0) {
					currentColor = 255.0;
				}
				d.add(currentColor/255.0);
				currentColor += colorInc;
			}
			Collections.shuffle(d);
			for(int j = 0;j<sortingArr[i].length;j++) {
				sortingArr[i][j] = d.get(j);
			}
		}
		for(int i = 0;i<sortingArr.length;i++) {
			InsertionSort s = new InsertionSort(sortingArr[i],board,i);
			sorterList.add(s);
			s.paint();
		}
		future = scheduler.scheduleAtFixedRate(updateAll, updateSpeed, updateSpeed, timeUnit);
		printLine("Started Sorter");
	}

	@Override
	public void terminate() {
		isRunning = false;
	}

	@Override
	public String getName() {
		return "Sorting";
	}

}
