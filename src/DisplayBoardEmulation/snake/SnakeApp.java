package DisplayBoardEmulation.snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;
import DisplayBoardEmulation.tron.Player;

public class SnakeApp
{
private static final String NAME = "Snake";
	
	private Player p1;

	
	//Time stuff
	private final int timeSpeed = 100;
	private final int endMultiplier = 20;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	//The board object
	private DisplayBoard board;
	
	//The timer
	private final ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	
	private boolean gameEnd;
	
	private boolean isRunning = false;
		
	public void start(DisplayBoard board) {
		isRunning = true;
		
		//Set board variable
		this.board = board;
		
		gameEnd = false;
		
		//Make players
		p1 = new Player(Color.RED, new Color(192,0,0),22,9,6);
		p2 = new Player(Color.GREEN, new Color(0,192,0),22,66,4);
		//Make key inputs
		p1.addDirection("w", 8); p1.addDirection("a", 4); p1.addDirection("s", 2); p1.addDirection("d", 6);
		p2.addDirection("i", 8); p2.addDirection("j", 4); p2.addDirection("k", 2); p2.addDirection("l", 6);
		
		if(!board.hasKeyCallback(keyUpdate)) {
			board.addKeyCallback(keyUpdate);
		}
		
		//Setup timer
		future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
		printLine("Game Started!");
	}
	
	public final Runnable gameUpdate = new Runnable() {
		public void run() {
			p1.changedDir = false;
			p2.changedDir = false;
			if(!gameEnd && isRunning) {
				movePlayer(p1);
			}
			if(!gameEnd && isRunning) {
				movePlayer(p2);
			}
			board.repaintBoard();
		}
	};

	private final Runnable reset = new Runnable() {
		public void run() {
			initializePlayers();
			gameEnd = false;
			while(!board.isCleared()) {
				board.clear();
			}
			board.repaintBoard();
			future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
			printLine("Game Started!");
		}
	};
	
	public final KeyRunnable keyUpdate = new KeyRunnable() {
		public void run(KeyEvent e) {
			if(isRunning) {
				p1.changeDirection("" + e.getKeyChar());
				p2.changeDirection("" + e.getKeyChar());
			}
		}
	};
	
	private void movePlayer(Player p) {
		if(gameEnd || !isRunning) {
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
			if(!gameEnd && isRunning) {
				gameEnd = true;
				if(p.equals(p1)) {
					board.colorRect(0,0,DisplayBoard.COLS+1,DisplayBoard.ROWS+1,p2.PLAYER_COLOR);
				} else {
					board.colorRect(0,0,DisplayBoard.COLS+1,DisplayBoard.ROWS+1,p1.PLAYER_COLOR);
				}
				board.repaintBoard();
				future.cancel(true);
				scheduler.schedule(reset, timeSpeed * endMultiplier, timeUnit);
				printLine("Game Ended!");
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

	@Override
	public void terminate() {
		isRunning = false;
		future.cancel(true);
	}

	@Override
	public String getName() {
		return NAME;
	}
}
