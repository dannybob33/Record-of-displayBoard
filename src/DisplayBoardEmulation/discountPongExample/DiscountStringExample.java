package DisplayBoardEmulation.discountPongExample;

import java.awt.Color;
import java.awt.Rectangle;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class DiscountStringExample {
	
	
	public static void main(String[] args) {
		DisplayBoard board = new DisplayBoard();
		board.show();
		board.DrawString(10, 0, 0, 255, 0, 0, "You Touch!", 10);
		board.DrawString(17, 9, 0, 255, 0, 0, "You  Got the Powa?");

	}
}
