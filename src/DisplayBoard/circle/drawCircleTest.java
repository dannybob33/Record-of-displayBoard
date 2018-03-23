package DisplayBoard.circle;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class drawCircleTest {

	public static void main(String[] args) {
		DisplayBoard board = new DisplayBoard();
		board.show();
		
		board.drawCircle(20, 20, 10);
		
	}

}
