package DisplayBoardEmulation.sorting;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class BubbleSort implements SortingAlgorithm {
	private double[] values;
	private int row;
	private DisplayBoard board;
	private int currentIndex = 0;
	private boolean isDone = false;
	private int currentMaxIndex;
	public BubbleSort(double[] vals, DisplayBoard board, int row) {
		this.board = board;
		this.values = vals;
		this.row = row;
		this.currentMaxIndex = this.values.length-1;
	}
	
	@Override
	public void update() {
		if(isDone) {
			return;
		}
		if(currentMaxIndex <= 0) {
			isDone = true;
			return;
		}
		if(currentIndex>=currentMaxIndex) {
			currentIndex = 0;
			currentMaxIndex -= 1;
		}
		if(values[currentIndex] > values[currentIndex+1]) {
			double val1 = values[currentIndex];
			double val2 = values[currentIndex+1];
			values[currentIndex] = val2;
			values[currentIndex+1] = val1;
		} else {
			currentIndex += 1;
		}
	}

	@Override
	public void restart(double[] vals) {
		currentIndex = 0;
		values = vals;
		isDone = false;
		currentMaxIndex = this.values.length-1;
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
	
	public boolean isDone() {
		return isDone;
	}

	@Override
	public String getName() {
		return "BubbleSort";
	}
	
}
