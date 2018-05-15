package DisplayBoardEmulation.WindowsXP;

import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;

public class WinXP extends Application {
	
	DisplayBoard b;
	tt t;
	
	private class tt extends Thread {
		private DisplayBoard b;
		
		public tt(DisplayBoard board) {
			b=board;
		}
		
		private boolean action() {
			try {
				Thread.sleep((long).00001);
			}
			catch(InterruptedException e) {
				return true;
			}
			return false;
		}
		
		public void run() {
			//cmd prompt
				//"A:/WindpwsXP.exe"
			//update
			//startup
			//login
			//desktop
			//start menu
			//run applications randomly
				//implement others inside of ours?
		}
		
		public void drawStartMenu() {
			
		}
		
		public void drawFloppy() {
			
		}
		
		public void update(long l) {
			
		}
		
		public void notepad(String str, long s, long s1) {
			
		}
	}
	
	public void start(DisplayBoard board) {
		b=board;
		t=new tt(b);
		try {
			t.start();
		}
		catch(Exception e) {
			System.out.println("Error: 400");
		}
	}

	public void terminate() {
		t.interrupt();
		b.clear();
		//flash "A:\virus.exe -a"
		//bsod machine check exception
		return;
	}

	public String getName() {
		return "Windows XP";
	}
	
}
