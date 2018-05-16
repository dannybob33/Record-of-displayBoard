package DisplayBoardEmulation.sorting;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class HeapSort implements SortingAlgorithm {

	private double[] values;
	private int row;
	private DisplayBoard board;
	private int currentIndex;
	private int currentIndex2;
	private boolean isDone;
	
	//not sifting
	private int end;
	private int start;
	private boolean hasBuiltHeap;
	private boolean isSifting;
	
	//sifting
	private int root;
	
	public HeapSort(double[] vals, DisplayBoard board, int row) {
		this.board = board;
		this.values = vals;
		this.row = row;
		this.end = vals.length-1;
		this.start = parent(values.length-1)+1;
		hasBuiltHeap = false;
		isSifting = false;
		isDone = false;
		currentIndex = 0;
		currentIndex2 = 0;
	}
	
	@Override
	public void update() {
		if(isDone) {
			return;
		}
		if(isSifting) {
			sift();
			return;
		}
		if(!hasBuiltHeap) {
			start -=1;
			if(start < 0) {
				hasBuiltHeap = true;
				end = values.length - 1;
			} else {
				root = start;
				isSifting = true;
				return;
			}
		}
		if(end <= 0) {
			isDone = true;
			return;
		}
		currentIndex = end;
		currentIndex2 = 0;
		swap(end,0);
		end -= 1;
		root = 0;
		isSifting = true;
	}
	
	private void sift() {
		if(leftChild(root) > end) {
			currentIndex = root;
			currentIndex2 = root;
			//this means we are done sifting
			isSifting = false;
			return;
		}
		int child = leftChild(root);
		int swap = root;
		//if left child is greater, you will swap them
		if(values[swap] < values[child])
			swap = child;
		//if right child is even greater, swap with right child instead
		if(child+1 <= end && values[swap] < values[child+1])
			swap = child+1;
		if(swap == root) {
			currentIndex = swap;
			currentIndex2 = root;
			//this means we are done sifting
			isSifting = false;
			return;
		} else {
			currentIndex = root;
			currentIndex2 = swap;
			swap(root,swap);
			root = swap;
		}
	}

	@Override
	public void restart(double[] vals) {
		this.values = vals;
		this.end = vals.length-1;
		this.start = parent(values.length-1)+1;
		hasBuiltHeap = false;
		isSifting = false;
		isDone = false;
		currentIndex = 0;
		currentIndex2 = 0;
	}

	@Override
	public void paint() {
		for(int i = 0;i<values.length;i++) {
			//Color c = Color.getHSBColor((float)values[i], 1.0f, 1.0f);
			Color c = new Color((float)values[i],1.0f-(float)values[i],1.0f-(float)values[i]);
			if(i==currentIndex && !isDone) {
				c = Color.WHITE;
			} else if (i==currentIndex2 && !isDone) {
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
	
	private int leftChild(int i) {
		return (2*i)+1;
	}
	private int parent(int i) {
		return (i-1)/2;
	}

	@Override
	public boolean isDone() {
		return isDone;
	}
	
	@Override
	public String getName() {
		return "Heap";
	}

}
