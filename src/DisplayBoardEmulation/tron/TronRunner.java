package DisplayBoardEmulation.tron;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;

public class TronRunner {
	private Player p1;
	private Player p2;
	
	//Time stuff
	private int timeSpeed = 50;
	private int clearMultiplier = 20;
	private int endMultiplier = 20;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	//The board object
	private DisplayBoard board;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	private boolean gameEnd;
		
	public void start() {
		//Create and show board
		board = new DisplayBoard();
		board.show();
		
		gameEnd = false;
		
		//Make players
		p1 = new Player(Color.RED, new Color(192,0,0),22,9,6);
		p2 = new Player(Color.GREEN, new Color(0,192,0),22,66,4);
		//Make key inputs
		p1.addDirection("w", 8); p1.addDirection("a", 4); p1.addDirection("s", 2); p1.addDirection("d", 6);
		p2.addDirection("i", 8); p2.addDirection("j", 4); p2.addDirection("k", 2); p2.addDirection("l", 6);
		
		board.addKeyCallback(keyUpdate);
		
		//Setup timer
		future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
	}
	
	public final Runnable gameUpdate = new Runnable() {
		public void run() {
			p1.changedDir = false;
			p2.changedDir = false;
			if(!gameEnd) {
				movePlayer(p1);
			}
			if(!gameEnd) {
				movePlayer(p2);
			}
		}
	};
	public final Runnable reset = new Runnable() {
		public void run() {
			initializePlayers();
			gameEnd = false;
			while(!board.isCleared()) {
				board.clear();
			}
			future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
			System.out.println("Game Started!");
		}
	};
	
	public final KeyRunnable keyUpdate = new KeyRunnable() {
		public void run(KeyEvent e) {
			p1.changeDirection("" + e.getKeyChar());
			p2.changeDirection("" + e.getKeyChar());
		}
	};
	
	public void movePlayer(Player p) {
		if(gameEnd) {
			return;
		}
		int r,c;
		if(p.getDirection() == 8) {
			r=p.getRow()-1;c=p.getCol();
		} else if (p.getDirection() == 6) {
			r=p.getRow();c=p.getCol()+1;
		} else if (p.getDirection() == 4) {
			r=p.getRow();c=p.getCol()-1;
		} else {
			r=p.getRow()+1;c=p.getCol();
		}
		if(checkPositionEmpty(r,c)) {
			board.setPixel(p.getRow(),p.getCol(),p.TRAIL_COLOR);
			board.setPixel(r,c,p.TRAIL_COLOR);
			p.setRow(r);
			p.setCol(c);
		} else {
			if(!gameEnd) {
				gameEnd = true;
				board.colorRect(0,0,DisplayBoard.COLS+1,DisplayBoard.ROWS+1,p.PLAYER_COLOR);
				future.cancel(true);
				scheduler.schedule(reset, timeSpeed * endMultiplier, timeUnit);
				System.out.println("Game Ended!");
			}
		}
	}
	private void initializePlayers() {
		p1.setRow(22);
		p1.setCol(9);
		p1.setDirection(6);
		
		p2.setRow(22);
		p2.setCol(66);
		p2.setDirection(4);
	}
	
	public boolean checkPositionEmpty(int row, int col) {
		if(row<0 || row>=DisplayBoard.ROWS || col<0 || col>=DisplayBoard.COLS) {
			return false;
		}
		if(board.getPixel(row, col).equals(Color.BLACK)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		//I just did this since I don't like static variables
		TronRunner application = new TronRunner();
		application.start();
	}

}
