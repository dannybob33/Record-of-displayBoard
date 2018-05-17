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
	private int timeSpeed = 2500;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	// The timer

	private ScheduledFuture<?> future;

	private boolean isRunning = false;

	@Override
	public void start(DisplayBoard board) {
		// Create and show board
		this.board = board;
		this.board.show();
		isRunning=true;
		// Setup timer
		future = scheduler.scheduleAtFixedRate(update, timeSpeed, timeSpeed, timeUnit);
	}
	public final Runnable update = new Runnable() {
		public void run() {
	Color[] arr= {Color.CYAN, Color.PINK, Color.RED, Color.YELLOW};
	// TODO Auto-generated method stub
			for(int i2=0; i2<arr.length; i2++) {
		wait(500);
		board.drawString(2, 27, arr[i2], "PEAK ");
		board.drawString(18, 33, arr[i2], "TO ");
		board.drawString(34, 27, arr[i2], "PEAK ");
		board.repaintBoard();
		wait(1000);
		board.clear();
		
		board.drawString(6, 8, arr[i2], "WELCOME ");
		board.repaintBoard();
		wait(500);
		board.drawString(6, 52,arr[i2], "TO");
		board.repaintBoard();
		wait(500);
		board.drawString(22, 23, arr[i2], "TECH");
		board.repaintBoard();
		wait(2500);
		board.clear();
				
for(int i=0; i<66; i+=4) {
	board.drawLine(0, i,43, i+8, Color.WHITE);
	board.repaintBoard();
	wait(200);
}
board.clear();	

for(int i=66; i>0; i-=4) {
	board.drawLine(43, i,0, i+8, Color.GREEN);
	board.repaintBoard();
	wait(200);
}
board.clear();

	for(int i=0; i<39; i+=4) {
		board.drawLine(i, 73,i+8, 0, Color.BLUE);
		board.repaintBoard();
		wait(200);
	}
	board.clear();
	
	for(int i=35; i>0; i-=4) {
		board.drawLine(i, 0,i+8, 73, Color.YELLOW);
		board.repaintBoard();
		wait(200);
	}	
	
	for(double t=0; t<2*Math.PI; t+=2*Math.PI/100) {
		board.drawCircle(22, 37, 21,50, 205, 50);
		board.drawLine(22, 37, (int)(21+21*Math.sin(t)), (int)(37+21*Math.cos(t)), 50, 205, 50);
		board.repaintBoard();
		wait(150);
		board.clear();
	}
	for(double t=Math.PI/2; t<(3*Math.PI)/2; t+=Math.PI/100) {
		
		board.drawLine(22, 72, (int)(21+21*Math.sin(t)), (int)(72+21*Math.cos(t)), 50, 205, 50);
		board.drawLine(22, 1, (int)(21+21*Math.sin(t+(Math.PI))), (int)(21*Math.cos(t+(Math.PI))), 50, 205, 50);
		board.repaintBoard();
		wait(50);
		board.clear();
	}
			}
	}
		public void wait(int m) {
			
			long iTime= System.currentTimeMillis();
			while(System.currentTimeMillis()< iTime+m);
				
		
	}
	};


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


