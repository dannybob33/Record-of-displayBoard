package DisplayBoard.circle;

import java.awt.Color;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class drawCircleTest {

	public static void main(String[] args) {
		DisplayBoard board = new DisplayBoard();
		board.show();
		
		board.drawCircle(20, 20, 10, Color.yellow);
		
		board.drawCircle(20, 41, 10, Color.orange, true);
	}

}
