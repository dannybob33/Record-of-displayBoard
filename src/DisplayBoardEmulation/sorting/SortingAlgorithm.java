package DisplayBoardEmulation.sorting;

import DisplayBoardEmulation.emulator.DisplayBoard;

public interface SortingAlgorithm {
	public void paint();
	public void update();
	public void restart(double[] vals);
	public boolean isDone();
	public String getName();
}
