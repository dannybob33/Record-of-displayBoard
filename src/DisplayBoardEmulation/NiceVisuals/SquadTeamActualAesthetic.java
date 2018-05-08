package DisplayBoardEmulation.NiceVisuals;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class SquadTeamActualAesthetic extends Application {
	private static final String NAME = "Aesthetic";
	// The board object
	private DisplayBoard board;

	// The timer

	private ScheduledFuture<?> future;

	private boolean isRunning = false;

	@Override
	public void start(DisplayBoard board) {
		this.board = board;
		isRunning=true;
		// TODO Auto-generated method stub
	
		Color [] colArr = {new Color(2,2,2), Color.PINK, Color.CYAN, Color.MAGENTA, Color.ORANGE};
		for(int i2=0; i2<5; i2++) {
			
			board.drawString(2, 27, colArr[i2], "PEAK ");
			board.drawString(18, 33, colArr[i2], "TO ");
			board.drawString(34, 27, colArr[i2], "PEAK ");
			board.repaintBoard();
			wait(2000);
			board.clear();
			wait(400);
			board.drawString(6, 8, colArr[i2], "WELCOME ");
			board.repaintBoard();
			wait(400);
			board.drawString(6, 52, colArr[i2], "TO");
			board.repaintBoard();
			wait(400);
			board.drawString(22, 23, colArr[i2], "TECH");
			board.repaintBoard();
			wait(2000);
			board.clear();
		
		board.clear();	
		wait(500);
	for(int i=0; i<66; i+=4) {
		board.drawLine(0, i,43, i+8, Color.WHITE);
		board.repaintBoard();
		wait(200);
	}
	board.clear();	
	wait(500);
	for(int i=66; i>0; i-=4) {
		board.drawLine(43, i,0, i+8, Color.GREEN);
		board.repaintBoard();
		wait(200);
	}
	board.clear();
	wait(500);
		for(int i=0; i<39; i+=4) {
			board.drawLine(i, 73,i+8, 0, Color.BLUE);
			board.repaintBoard();
			wait(200);
		}
		board.clear();
		wait(500);
		for(int i=35; i>0; i-=4) {
			board.drawLine(i, 0,i+8, 73, Color.YELLOW);
			board.repaintBoard();
			wait(200);
		}	
		
		for(double t=0; t<2*Math.PI; t+=2*Math.PI/100) {
			board.drawCircle(22, 37, 21,50, 205, 50);
			board.drawLine(22, 37, (int)(21+21*Math.sin(t)), (int)(37+21*Math.cos(t)), 50, 205, 50);
			board.repaintBoard();
			wait(100);
			board.clear();
		}
		for(double t=Math.PI/2; t<(3*Math.PI)/2; t+=Math.PI/100) {
			
			board.drawLine(22, 72, (int)(21+21*Math.sin(t)), (int)(72+21*Math.cos(t)), 50, 205, 50);
			board.drawLine(22, 1, (int)(21+21*Math.sin(t+(Math.PI))), (int)(21*Math.cos(t+(Math.PI))), 50, 205, 50);
			board.repaintBoard();
			wait(25);
			board.clear();
		}
		
		}
	}

public void wait(int m) {
	try {
		Thread.sleep(m);
			}
	catch(InterruptedException e) {
		Thread.currentThread().interrupt();
	}
}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		isRunning = false;
		future.cancel(true);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

}


