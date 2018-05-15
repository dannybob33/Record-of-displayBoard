package DisplayBoardEmulation.snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.emulator.KeyRunnable;
import DisplayBoardEmulation.nativeApp.Application;

public class SnakeApp extends Application
{
	private static final String NAME = "Snake";

	private Snek p1;

	// Time stuff
	private final int timeSpeed = 100;
	private final int endMultiplier = 20;
	private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	// The board object
	private DisplayBoard board;

	// The timer
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;

	private boolean gameEnd;

	private boolean isRunning = false;

	private LinkedList<Location> locs;

	private int sLength;

	public void start(DisplayBoard board)
	{

		locs = new LinkedList<Location>();

		isRunning = true;

		// Set board variable
		this.board = board;

		gameEnd = false;

		// Make players
		p1 = new Snek(Color.ORANGE, new Color(0, 192, 0), 22, 9, 6);

		board.setPixel(22, 36, Color.RED);

		locs.add(new Location(22, 9));
		sLength = 4;
		// Make key inputs
		p1.addDirection("w", 8);
		p1.addDirection("a", 4);
		p1.addDirection("s", 2);
		p1.addDirection("d", 6);

		if (!board.hasKeyCallback(keyUpdate))
		{
			board.addKeyCallback(keyUpdate);
		}

		// Setup timer
		future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
		printLine("Game Started!");
	}

	public final Runnable gameUpdate = new Runnable()
	{
		public void run()
		{
			p1.changedDir = false;
			if (!gameEnd && isRunning)
			{
				moveSnek(p1);
			}
			board.repaintBoard();
		}
	};

	private final Runnable reset = new Runnable()
	{
		public void run()
		{
			initializePlayers();
			board.setBrightness(32);
			gameEnd = false;
			while (!board.isCleared())
			{
				board.clear();
			}
			board.repaintBoard();
			sLength = 4;
			future = scheduler.scheduleAtFixedRate(gameUpdate, timeSpeed, timeSpeed, timeUnit);
			board.setPixel(22, 36, Color.RED);
			printLine("Game Started!");
		}
	};

	public final KeyRunnable keyUpdate = new KeyRunnable()
	{
		public void run(KeyEvent e)
		{
			if (isRunning)
			{

					p1.changeDirection("" + e.getKeyChar());
				
			}
		}
	};

	private void moveSnek(Snek p)
	{
		if (gameEnd || !isRunning)
		{
			return;
		}
		int r, c;
		if (p.getDirection() == 8)
		{
			r = p.getRow() - 1;
			c = p.getCol();
		}
		else if (p.getDirection() == 6)
		{
			r = p.getRow();
			c = p.getCol() + 1;
		}
		else if (p.getDirection() == 4)
		{
			r = p.getRow();
			c = p.getCol() - 1;
		}
		else
		{
			r = p.getRow() + 1;
			c = p.getCol();
		}
		if (checkPositionEmpty(r, c))
		{
			locs.add(new Location(r, c));
			if (board.getPixel(r, c).equals(Color.RED))
			{
				if (sLength >= 42*72)
				{
					if (!gameEnd && isRunning)
					{
						gameEnd = true;
						if (p.equals(p1))
						{
							board.colorRect(0, 0, DisplayBoard.COLS + 1, DisplayBoard.ROWS + 1, Color.GREEN);
							board.drawString(0, centering("WIN"), Color.WHITE, "WIN");
						}
						board.repaintBoard();
						future.cancel(true);
						scheduler.schedule(reset, timeSpeed * endMultiplier, timeUnit);
						sLength = 4;
						printLine("Game Ended!");

					}
				}
				sLength += 4;
				int x;
				int y;
				
				x = (int)((Math.random() * 42) + 1);
				y = (int)((Math.random() * 72) + 1);
				
				while (checkPositionEmpty(x,y) == false)
				{
					x = (int)((Math.random() * 42) + 1);
					y = (int)((Math.random() * 72) + 1);
				}
				
				board.setPixel(x, y, Color.RED);
			}
			if (locs.size() > sLength)
			{
				board.setPixel(locs.get(0).getRow(), locs.get(0).getCol(), Color.BLACK);
				locs.removeFirst();
			}
			board.setPixel(p.getRow(), p.getCol(), p.TRAIL_COLOR);
			board.setPixel(r, c, p.PLAYER_COLOR);
			p.setRow(r);
			p.setCol(c);
		}
		else
		{
			if (!gameEnd && isRunning)
			{
				gameEnd = true;
				if (p.equals(p1))
				{
					board.setBrightness(8);
					board.colorRect(0, 0, DisplayBoard.COLS + 1, DisplayBoard.ROWS + 1, Color.RED);
					board.drawString(10, centering("LOSE"), Color.WHITE, "LOSE");
					board.drawString(19, centering("Score: " + ((sLength/4) - 1)), Color.WHITE, "Score: " + ((sLength/4) - 1));
				}
				board.repaintBoard();
				future.cancel(true);
				scheduler.schedule(reset, timeSpeed * endMultiplier, timeUnit);
				sLength = 4;
				printLine("Game Ended!");

			}
		}
	}

	private void initializePlayers()
	{
		p1.setRow(22);
		p1.setCol(9);
		p1.setDirection(6);
		locs = new LinkedList<Location>();
		locs.add(new Location(22, 9));
		board.setPixel(22, 36, Color.RED);
		sLength = 4;
	}

	public boolean checkPositionEmpty(int row, int col)
	{
		if (row < 0 || row >= DisplayBoard.ROWS || col < 0 || col >= DisplayBoard.COLS)
		{
			return false;
		}
		if (board.getPixel(row, col).equals(Color.BLACK) || board.getPixel(row, col).equals(Color.RED))
		{
			return true;
		}
		return false;
	}

	@Override
	public void terminate()
	{
		isRunning = false;
		future.cancel(true);
	}
	
	private int centering(String text) {
		int width = board.StringWidth(text);
		System.out.println(width);
		return (DisplayBoard.COLS-width)/2;
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
}
