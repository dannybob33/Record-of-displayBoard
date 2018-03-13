package DisplayBoardEmulation.keyExample;

import java.awt.Color;
import java.util.Set;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class KeyExample {

	public static void main(String[] args) {
		DisplayBoard board = new DisplayBoard();
		board.show();
		Set<String> keys = board.getKeys();
		while(keys.contains("c") == false) { //when you press c, quit.
			if(keys.contains("q")) { //when you press q, color a certain pixel. 
				board.setPixel(10, 10, Color.RED);
			}
			if(keys.contains("w")) { //when you press w, color a certain rectangle.
				board.colorRect(20, 20, 10, 10, Color.BLUE);
			}
			if(keys.contains("e")) { //when you press e, clear the canvas.
				board.clear();
			}
			keys = board.getKeys();
		}
		board.close();
	}

}
