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
			b.show();
			return false;
		}
		
		public void run() {
			action();
			b.drawString(0, 0, 255,  255, 255, "A:\\Virus.exe");
			action();
			update(1500);
			action();
			startUp();
			action();
			login();
			action();
			desktop();
			action();
			startMenu();
			action();
			while(true) {
			//run applications randomly
				//implement others inside of ours?
				action();
				int i=(int)(Math.random()*10);
				switch(i) {
					case 1:
						notepad("");
						action();
						break;
					case 2:
						app();
						action();
						break;
					case 3:
						app2();
						action();
						break;
					case 4:
						app3();
						action();
						break;
					case 5:
						app3();
						action();
						break;
					case 6:
						app3();
						action();
						break;
					case 7:
						app3();
						action();
						break;
					case 8:
						app3();
						action();
						break;
					case 9:
						app3();
						action();
						break;
					default: 
						action();
						break;
				}
			}
		}
		
		private void login() {
			
		}
		
		private void startUp() {
			
		}
		
		private void desktop() {
			//ISHAN
		}
		
		private void startMenu() {
			
			b.show();
		}
		
		private void drawFloppy() {
			
			b.show();
		}
		
		private void update(long l) {
			
			b.show();
		}
		
		private void notepad(String str) {
			
			b.show();
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
		b.show();
		b.drawString(0, 0, 255,  255, 255, "A:\\Virus.exe -a");
		b.show();
		try {
			Thread.sleep(7500);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		b.clear();
		b.show();
		//bsod machine check exception
		return;
	}

	public String getName() {
		return "Windows XP";
	}
	
}
