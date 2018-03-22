package DisplayBoardEmulation.nativeApp;

import DisplayBoardEmulation.emulator.DisplayBoard;

public abstract class Application implements BoardApplication {
	//Abstract methods:
	public abstract void start(DisplayBoard board);
	public abstract void terminate();
	public abstract String getName();
	//Optional methods:
	public void setManager(ApplicationManager m) {
		return;
	}
	//Final methods:
	public final void printLine(String s) {
		System.out.println(getName() + ": " + s);
	}
}