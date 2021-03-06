package DisplayBoardEmulation.discountPongExample;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;

public class DiscountPongExample {
	//Paddle stuff
	private Rectangle paddle1 = new Rectangle(4,10,3,DisplayBoard.ROWS-20); //left paddle
	private Rectangle paddle2 = new Rectangle(DisplayBoard.COLS-6,10,3,DisplayBoard.ROWS-20); //right paddle
	
	//ball stuff
	private int ballSize = 3;
	private Rectangle ballLocation = new Rectangle(0,0,ballSize,ballSize);
	private int xInc = 1;
	private int yInc = 1;
	
	//Time stuff
	private int timeSpeed = 25;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	//The board object
	private DisplayBoard board;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	
	public void start() {
		//Create and show board
		board = new DisplayBoard();
		board.show();
		//Setup timer
		scheduler.scheduleAtFixedRate(update, timeSpeed, timeSpeed, timeUnit);
	}
	
	public final Runnable update = new Runnable() {
		public void run() {
			//Erase ball
			board.colorRect(ballLocation.y,ballLocation.x,ballSize,ballSize,Color.BLACK);
			//Draw paddles
			board.colorRect(paddle1, Color.RED); //left paddle
			board.colorRect(paddle2, Color.GREEN); //right paddle
			//Update ball location
			ballLocation = new Rectangle(ballLocation.x + xInc,ballLocation.y + yInc,ballLocation.width,ballLocation.height);
			//Update ball direction
			if(ballLocation.x <= 0 || ballLocation.x >= DisplayBoard.COLS-ballSize) xInc *= -1; //x direction
			if(ballLocation.y <= 0 || ballLocation.y >= DisplayBoard.ROWS-ballSize) yInc *= -1; //y direction
			//Draw ball at new location
			board.colorRect(ballLocation, Color.YELLOW); //left paddle
		}
	};
	
	public static void main(String[] args) {
		//I just did this since I don't like static variables
		DiscountPongExample application = new DiscountPongExample();
		application.start();
	}
}
