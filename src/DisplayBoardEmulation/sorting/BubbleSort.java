package DisplayBoardEmulation.sorting;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class BubbleSort implements SortingAlgorithm {
	private double[] values;
	private int row;
	private DisplayBoard board;
	private int currentIndex = 0;
	private boolean isDone = false;
	private int passedIndexes = 0;
	public BubbleSort(double[] vals, DisplayBoard board, int row) {
		this.board = board;
		this.values = vals;
		this.row = row;
	}
	
	@Override
	public void update() {
		if(isDone) {
			return;
		}
		if(passedIndexes >= values.length) {
			isDone = true;
			return;
		}
		if(currentIndex>=values.length-1) {
			currentIndex = 0;
		}
		if(values[currentIndex] > values[currentIndex+1]) {
			double val1 = values[currentIndex];
			double val2 = values[currentIndex+1];
			values[currentIndex] = val2;
			values[currentIndex+1] = val1;
			passedIndexes = 0;
		} else {
			currentIndex += 1;
			passedIndexes += 1;
		}
	}

	@Override
	public void restart(double[] vals) {
		currentIndex = 0;
		values = vals;
		isDone = false;
		passedIndexes = 0;
	}

	@Override
	public void paint() {
		for(int i = 0;i<values.length;i++) {
			Color c = Color.getHSBColor((float)values[i], 1.0f, 1.0f);
			board.setPixel(row, i, c);
		}
	}
	
}
