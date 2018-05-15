package DisplayBoardEmulation.WinXP;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoardEmulation.ImageDisplay.GalleryApp;
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
			if(action()) {
				return;
			}
			b.drawString(0, 0, 255,  255, 255, "A:\\Virus.exe");
			if(action()) {
				return;
			}
			update(1500);
			if(action()) {
				return;
			}
			startUp();
			if(action()) {
				return;
			}
			login();
			if(action()) {
				return;
			}
			desktop();
			if(action()) {
				return;
			}
			startMenu();
			if(action()) {
				return;
			}
			while(true) {
				if(action()) {
					return;
				}
				int i=(int)(Math.random()*10);
				switch(i) {
					case 1:
						open();
						if(action()) {
							return;
						}
						notepad("");
						if(action()) {
							return;
						}
						reset();
						break;
					case 2:
						open();
						if(action()) {
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
						reset();
						if(action()) {
							return;
						}
						break;
					case 3:
						open();
						if(action()) {
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
						reset();
						if(action()) {
							return;
						}
						break;
					case 4:
						open();
						if(action()) {
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
						reset();
						if(action()) {
							return;
						}
						break;
					case 5:
						open();
						if(action()) {
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
						reset();
						if(action()) {
							return;
						}
						break;
					case 6:
						open();
						if(action()) {
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
						reset();
						if(action()) {
							return;
						}
						break;
					case 7:
						open();
						if(action()) {
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
						reset();
						if(action()) {
							return;
						}
						break;
					case 8:
						open();
						if(action()) {
							return;
						}
						app3();
						if(action()) {
							return;
						}
						break;
					case 9:
						open();
						if(action()) {
							return;
						}
						app9();
						if(action()) {
							return;
						}
						break;
					default: 
						if(action()) {
							return;
						}
						desktop();
						if(action()) {
							return;
						}
						break;
				}
			}
		}
		
		private void login() {
			
		}
		
		private void startUp() {
			
		}
		
		private void Desktop() {
			try {
				b.drawImage(ImageIO.read(new File("")), 0, 0, DisplayBoard.ROWS, DisplayBoard.COLS);
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
		//bsod machine check exception
		return;
	}

	public String getName() {
		return "Windows XP";
	}
	
}
