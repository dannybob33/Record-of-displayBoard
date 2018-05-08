package DisplayBoardEmulation.sorting;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class HeapSort implements SortingAlgorithm {

	private double[] values;
	private int row;
	private DisplayBoard board;
	private int currentIndex = 0;
	private boolean isDone = false;
	private boolean hasHeapified = false;
	private boolean isSifting = false;
	private int start;
	private int end;
	
	//sifting
	int root;
	public HeapSort(double[] vals, DisplayBoard board, int row) {
		this.board = board;
		this.values = vals;
		this.row = row;
		this.start = (vals.length/2)-1;
	}
	
	@Override
	public void update() {
		if(isDone) {
			return;
		}
		if(isSifting) {
			
		}
	}

	@Override
	public void restart(double[] vals) {
		currentIndex = 0;
		values = vals;
		isDone = false;
		hasHeapified = false;
	}

	@Override
	public void paint() {
		for(int i = 0;i<values.length;i++) {
			//Color c = Color.getHSBColor((float)values[i], 1.0f, 1.0f);
			Color c = new Color((float)values[i],1.0f-(float)values[i],1.0f-(float)values[i]);
			if(i==currentIndex && !isDone) {
				c = Color.WHITE;
			}
			board.setPixel(row, i, c);
		}
	}
	
	private void swap(int i1, int i2) {
		double c1 = values[i1];
		double c2 = values[i2];
		values[i1] = c2;
		values[i2] = c1;
	}

}
