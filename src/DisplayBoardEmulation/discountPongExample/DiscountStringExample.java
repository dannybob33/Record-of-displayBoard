package DisplayBoardEmulation.discountPongExample;

import java.awt.Color;
import java.awt.Rectangle;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class DiscountStringExample {
	
	
	public static void main(String[] args) {
		DisplayBoard board = new DisplayBoard();
		board.show();
		board.drawString(17, 9, 0, 255, 0, 0, "You  Got the Powa?");

	}
} 
