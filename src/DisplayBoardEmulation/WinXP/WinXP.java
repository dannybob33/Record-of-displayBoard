package DisplayBoardEmulation.WinXP;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.HettsyApp.HettsyApp;
import DisplayBoardEmulation.ImageDisplay.GalleryApp;
import DisplayBoardEmulation.NO.NO;
import DisplayBoardEmulation.discountPongExample.DiscountPongApp;
import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.fusionFeud.fusionFeudApp;
import DisplayBoardEmulation.nativeApp.Application;
import DisplayBoardEmulation.snake.SnakeApp;
import DisplayBoardEmulation.sorting.SortingApp;
import DisplayBoardEmulation.tron.TronApp;
import DisplayBoardEmulation.webcam.WebCamPhotoApp;

/**
 * Class that should not exist. 
 * @author Some bad people.
 *
 */
public class WinXP extends Application {
	
	DisplayBoard b;
	tt t;
	boolean d;
	
	public WinXP() {
		d=false;
	}
	
	public WinXP(boolean debug) {
		d=debug;
	}

	//end of hettsy face app
	
	//start of running thread

	private class tt extends Thread {
		private DisplayBoard b;
		
		public tt(DisplayBoard board, boolean debug) {
			b=board;
			d=debug;
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
			b.clear();
			if(action()) {
				return;
			}
			b.drawString(0, 0, 255,  255, 255, "A:\\Virus.exe");
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException e) {
				System.out.println("Error: 400.3");
				e.printStackTrace();
			}
			if(action()) {
				return;
			}
			if(startUp()) {
				return;
			}
			if(login()) {
				return;
			}
			if(desktop()) {
				return;
			}
			if(startMenu()) {
				return;
			}
			while(true) {
				if(action()) {
					return;
				}
				int i=(int)(Math.random()*20);
				switch(i) {
					case 1:
						if(open()) {
							return;
						}
						HettsyApp a1=new HettsyApp();
						a1.start(b);
						if(action()) {
							return;
						}
						long t1=System.nanoTime();
						while((t1+30000000)>=System.nanoTime()) {
							if(action()) {
								a1.terminate();
								return;
							}
						}
						a1.terminate();
						if(reset()) {
							return;
						}
						break;
					case 2:
						if(open()) {
							return;
						}
						SnakeApp a2=new SnakeApp();
						a2.start(b);
						if(action()) {
							return;
						}
						long t2=System.nanoTime();
						while((t2+30000000)>=System.nanoTime()) {
							if(action()) {
								a2.terminate();
								return;
							}
						}
						a2.terminate();
						if(reset()) {
							return;
						}
						break;
					case 3:
						if(open()) {
							return;
						}
						TronApp a3=new TronApp();
						a3.start(b);
						if(action()) {
							return;
						}
						long t3=System.nanoTime();
						while((t3+30000000)>=System.nanoTime()) {
							if(action()) {
								a3.terminate();
								return;
							}
						}
						a3.terminate();
						if(reset()) {
							return;
						}
						break;
					case 4:
						if(open()) {
							return;
						}
						SortingApp a4=new SortingApp();
						a4.start(b);
						if(action()) {
							return;
						}
						long t4=System.nanoTime();
						while((t4+120000000)>=System.nanoTime()) {
							if(action()) {
								a4.terminate();
								return;
							}
						}
						a4.terminate();
						if(reset()) {
							return;
						}
						break;
					case 5:
						if(open()) {
							return;
						}
						fusionFeudApp a5=new fusionFeudApp();
						a5.start(b);
						if(action()) {
							return;
						}
						long t5=System.nanoTime();
						while((t5+30000000)>=System.nanoTime()) {
							if(action()) {
								a5.terminate();
								return;
							}
						}
						a5.terminate();
						if(reset()) {
							return;
						}
						break;
					case 6:
						if(open()) {
							return;
						}
						WebCamPhotoApp a6=new WebCamPhotoApp();
						a6.start(b);
						if(action()) {
							return;
						}
						long t6=System.nanoTime();
						while((t6+30000000)>=System.nanoTime()) {
							if(action()) {
								a6.terminate();
								return;
							}
						}
						a6.terminate();
						if(reset()) {
							return;
						}
						break;
					case 7:
						if(open()) {
							return;
						}
						GalleryApp a7=new GalleryApp();
						a7.start(b);
						if(action()) {
							return;
						}
						long t7=System.nanoTime();
						while((t7+30000000)>=System.nanoTime()) {
							if(action()) {
								a7.terminate();
								return;
							}
						}
						a7.terminate();
						if(reset()) {
							return;
						}
						break;
					case 8:
						if(reset()) {
							return;
						}
						DiscountPongApp a8=new DiscountPongApp();
						a8.start(b);
						if(action()) {
							return;
						}
						long t8=System.nanoTime();
						while((t8+30000000)>=System.nanoTime()) {
							if(action()) {
								a8.terminate();
								return;
							}
						}
						a8.terminate();
						if(reset()) {
							return;
						}
						break;
					case 9:
						if(open()) {
							return;
						}
						NO a9=new NO();
						a9.start(b);
						if(action()) {
							return;
						}
						long t9=System.nanoTime();
						while((t9+30000000)>=System.nanoTime()) {
							if(action()) {
								a9.terminate();
								return;
							}
						}
						a9.terminate();
						if(reset()) {
							return;
						}
						break;
					default: 
						if(desktop()) {
							return;
						}
						break;
				}
			}
		}
		
		private void image(String dir) {
			try {
				b.drawImage(ImageIO.read(new File(dir)), 0, 0, DisplayBoard.ROWS-1, DisplayBoard.COLS-1);
				if(action()) {
					return;
				}
			}
			catch(IOException e) {
				if(d) {
					System.out.println("404.1");
				}
				e.printStackTrace();
			}
			if(action()) {
				return;
			}
		}
		
		private boolean desktop() {
			if(action()) {
				return true;
			}
			image("WinXP/background.jpg");
			return action();
		}
		
		private boolean login() {
			if(action()) {
				return true;
			}
			image("WinXP/login.png");
			return false;
		}
		
		private boolean startUp() {
			if(action()) {
				return true;
			}
			image("WinXP/loading.png");
			return false;
		}
		
		private boolean startMenu() {
			if(action()) {
				return true;
			}
			try {
				b.drawImage(ImageIO.read(new File("startMenu.jpg")), DisplayBoard.ROWS/2, 0, DisplayBoard.ROWS/2, DisplayBoard.COLS/5);
				if(action()) {
					return true;
				}
			}
			catch(IOException e) {
				if(d) {
					System.out.println("404.1");
				}
				e.printStackTrace();
			}
			return action();
		}
		
		public boolean reset() {
			if(action()) {
				return true;
			}
			desktop();
			return action();
		}
		
		public boolean open() {
			if(action()) {
				return true;
			}
			b.colorRect(DisplayBoard.ROWS/4, DisplayBoard.COLS/4, DisplayBoard.ROWS-(DisplayBoard.ROWS/4), DisplayBoard.COLS-(DisplayBoard.COLS/4), 0, 0, 0);
			return action();
		}
	}
	
	public void start(DisplayBoard board) {
		b=board;
		t=new tt(b, d);
		try {
			t.start();
		}
		catch(Exception e) {
			if(d) {
				System.out.println("Error: 400.1");
				e.printStackTrace();
			}
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
			if(d) {
				System.out.println("Error: 400.2");
			}
			e.printStackTrace();
		}
		b.clear();
		b.show();
		b.colorRect(0, 0, DisplayBoard.ROWS-1, DisplayBoard.COLS-1, 0, 0, 255);
		b.drawString(0, 0, 255,  255, 255, "BSOD: Machine Check Exception");
		return;
	}

	public String getName() {
		return "Windows XP";
	}
}
