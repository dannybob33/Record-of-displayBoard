package DisplayBoardEmulation.sorting;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class InsertionSort implements SortingAlgorithm {

	private double[] values;
	private int row;
	private DisplayBoard board;
	private int currentIndex = 0;
	private boolean isDone = false;
	private int currentMaxIndex;
	public InsertionSort(double[] vals, DisplayBoard board, int row) {
		this.board = board;
		this.values = vals;
		this.row = row;
		currentMaxIndex = 1;
		currentIndex = currentMaxIndex;
	}
	
	@Override
	public void update() {
		if(isDone) {
			return;
		}
		if(currentIndex <= 0) {
			currentMaxIndex += 1;
			currentIndex = currentMaxIndex;
		}
		if(currentMaxIndex >= values.length) {
			isDone = true;
			return;
		}
		if(values[currentIndex-1] > values[currentIndex]) {
			swap(currentIndex-1,currentIndex);
		}
		currentIndex -=1;
	}

	@Override
	public void restart(double[] vals) {
		currentIndex = 0;
		values = vals;
		isDone = false;
		currentMaxIndex = 1;
		currentIndex = currentMaxIndex;
	}

	@Override
	public void paint() {
		for(int i = 0;i<values.length;i++) {
			//Color c = Color.getHSBColor((float)values[i], 1.0f, 1.0f);
			Color c = new Color((float)values[i],1.0f-(float)values[i],1.0f-(float)values[i]);
			if(i==currentIndex && !isDone) {
				c = Color.WHITE;
			}
			if(board.getPixel(row,i).equals(c) == false) {
				board.setPixel(row, i, c);
			}
		}
	}
	
	private void swap(int i1, int i2) {
		double c1 = values[i1];
		double c2 = values[i2];
		values[i1] = c2;
		values[i2] = c1;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	@Override
	public String getName() {
		return "Insertion";
	}

}
