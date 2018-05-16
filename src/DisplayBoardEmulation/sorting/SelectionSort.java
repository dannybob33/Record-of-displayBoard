package DisplayBoardEmulation.sorting;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class SelectionSort implements SortingAlgorithm {

	private double[] values;
	private int row;
	private DisplayBoard board;
	private int currentIndex = 0;
	private boolean isDone = false;
	private int currentMinIndex = 0;
	private int currentMinValue = 0;
	public SelectionSort(double[] vals, DisplayBoard board, int row) {
		this.board = board;
		this.values = vals;
		this.row = row;
	}
	
	@Override
	public void update() {
		if(isDone) {
			return;
		}
		if(currentMinIndex >= values.length-1) {
			isDone = true;
			return;
		}
		if(currentIndex >= values.length) {
			double c1 = values[currentMinIndex];
			double c2 = values[currentMinValue];
			values[currentMinIndex] = c2;
			values[currentMinValue] = c1;
			currentMinIndex += 1;
			currentIndex = currentMinIndex;
			currentMinValue = currentMinIndex;
		}
		if(values[currentIndex] < values[currentMinValue]) {
			currentMinValue = currentIndex;
		}
		currentIndex += 1;
	}

	@Override
	public void restart(double[] vals) {
		currentIndex = 0;
		values = vals;
		isDone = false;
		currentMinIndex = 0;
	}

	@Override
	public void paint() {
		for(int i = 0;i<values.length;i++) {
			//Color c = Color.getHSBColor((float)values[i], 1.0f, 1.0f);
			Color c = new Color((float)values[i],1.0f-(float)values[i],1.0f-(float)values[i]);
			if(i==currentIndex && !isDone) {
				c = Color.WHITE;
				board.setPixel(row, i, c);
			}
			if(board.getPixel(row,i).equals(c) == false) {
				board.setPixel(row, i, c);
			}
		}
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	@Override
	public String getName() {
		return "SelectionSort";
	}

}
