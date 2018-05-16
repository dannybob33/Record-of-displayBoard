package DisplayBoardEmulation.sorting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class SortingApp extends Application {

	private DisplayBoard board;
	
	private boolean isRunning = false;
	
	private int HEIGHT = 34;
	
	private double[][] sortingArr = new double[HEIGHT][board.COLS];
	private ArrayList<ArrayList<SortingAlgorithm>> sorterList = new ArrayList<ArrayList<SortingAlgorithm>>();
	private int currentAlg = 0;
	private ArrayList<SortingAlgorithm> currentSorterList;
	
	private final int updateSpeed = 10;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	private boolean allDone = false;
	
	private XInputDevice[] devices;
	private XInputDevice device1;
	private boolean justChangedAlgorithm = false;
	
	private final int TEXT_ROW = DisplayBoard.ROWS-9;
	
	public final Runnable updateAll = new Runnable() {
		public void run() {
			if(!isRunning) {
				future.cancel(true);
				return;
			}
			device1.poll();
			double leftX = device1.getComponents().getAxes().lx;
			if(leftX >= 0.5 && !justChangedAlgorithm) {
				changeSorter(-1);
				justChangedAlgorithm = true;
			} else if (leftX <= -0.5 && !justChangedAlgorithm) {
				changeSorter(1);
				justChangedAlgorithm = true;
			} else if (leftX > -0.5 && leftX < 0.5 && justChangedAlgorithm) {
				justChangedAlgorithm = false;
			}
			if(!allDone) {
				boolean allAreDone = true;
				for(SortingAlgorithm sorter : currentSorterList) {
					sorter.update();
					sorter.paint();
					if(!sorter.isDone()) {
						allAreDone = false;
					}
				}
				if(!allAreDone) {
					board.repaintBoard();
				} else {
					board.repaintBoard();
					allDone = true;
				}
			}
		}
	};
	
	@Override
	public void start(DisplayBoard board) {
		this.board = board;
		isRunning = true;
		try {
			devices = XInputDevice.getAllDevices();
			device1 = devices[0];
		} catch (XInputNotLoadedException e) {
			// Do nothing
		}
		makeLists();
		//SelectionSort
		ArrayList<SortingAlgorithm> selectionList = new ArrayList<SortingAlgorithm>();
		for(int i = 0;i<sortingArr.length;i++) {
			SelectionSort s = new SelectionSort(sortingArr[i],board,i);
			selectionList.add(s);
		}
		sorterList.add(selectionList);
		//BubbleSort
		ArrayList<SortingAlgorithm> bubbleList = new ArrayList<SortingAlgorithm>();
		for(int i = 0;i<sortingArr.length;i++) {
			BubbleSort s = new BubbleSort(sortingArr[i],board,i);
			bubbleList.add(s);
		}
		sorterList.add(bubbleList);
		//InsertionSort
		ArrayList<SortingAlgorithm> insertionList = new ArrayList<SortingAlgorithm>();
		for(int i = 0;i<sortingArr.length;i++) {
			InsertionSort s = new InsertionSort(sortingArr[i],board,i);
			insertionList.add(s);
		}
		sorterList.add(insertionList);
		//HeapSort
		ArrayList<SortingAlgorithm> heapList = new ArrayList<SortingAlgorithm>();
		for(int i = 0;i<sortingArr.length;i++) {
			HeapSort s = new HeapSort(sortingArr[i],board,i);
			heapList.add(s);
		}
		sorterList.add(heapList);
		initializeSorters();
	}
	
	private void changeSorter(int direction) {
		if(!isRunning) {
			return;
		}
		//Cancel updating
		future.cancel(true);
		//Reset all sorting algorithms in current list
		for(int i = 0;i<sortingArr.length;i++) {
			currentSorterList.get(i).restart(sortingArr[i]);
		}
		currentAlg += direction;
		if(currentAlg < 0) {
			currentAlg = sorterList.size()-1;
		} else if (currentAlg >= sorterList.size()) {
			currentAlg = 0;
		}
		makeLists();
		initializeSorters();
	}
	private void initializeSorters() {
		if(!isRunning) {
			return;
		}
		currentSorterList = sorterList.get(currentAlg);
		for(SortingAlgorithm sorter : currentSorterList) {
			sorter.paint();
		}
		board.colorRect(board.ROWS-9,0,board.COLS,9,Color.BLACK);
		String name = currentSorterList.get(0).getName();
		board.drawString(TEXT_ROW, centering(name), Color.WHITE, name);
		board.repaintBoard();
		future = scheduler.scheduleAtFixedRate(updateAll, updateSpeed, updateSpeed, timeUnit);
		allDone = false;
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
	
	private void makeLists() {
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
	}
	
	private int centering(String text) {
		int width = board.StringWidth(text);
		System.out.println(width);
		return (DisplayBoard.COLS-width)/2;
	}

}
