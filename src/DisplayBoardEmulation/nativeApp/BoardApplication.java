package DisplayBoardEmulation.nativeApp;

import DisplayBoardEmulation.emulator.DisplayBoard;

public interface BoardApplication {
	public void start(DisplayBoard board);
	public void terminate();
}
