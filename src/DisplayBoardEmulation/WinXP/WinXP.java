package DisplayBoardEmulation.WinXP;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import DisplayBoard.weather.weatherTest;
import DisplayBoardEmulation.ImageDisplay.GalleryApp;
import DisplayBoardEmulation.ImageDisplay.ImageDisplayApp;
import DisplayBoardEmulation.WinXP.uApps.*;
import DisplayBoardEmulation.discountPongExample.DiscountPongApp;
import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.fusionFeud.fusionFeudApp;
import DisplayBoardEmulation.nativeApp.Application;
import DisplayBoardEmulation.snake.SnakeApp;
import DisplayBoardEmulation.sorting.SortingApp;
import DisplayBoardEmulation.tron.TronApp;
import DisplayBoardEmulation.webcam.WebCamPhotoApp;
import GifThing.Jedi;

/**
 * Dear future readers of my Code. John Bass very much disagrees with the following Class
 * @author Tyler O'Dowd, Ishan Kumar (not really Ishan tho)
 *
 */
public class WinXP extends Application {
	
	private DisplayBoard b;
	private tt t;
	private boolean d;
	private int di;
	private int dic;
	
	public WinXP() {
		d=false;
		di=-99999;
		dic=-99999999;
	}
	
	public WinXP(boolean debug) {
		d=debug;
		di=0;
		dic=0;
	}

	private class tt extends Thread {
		private DisplayBoard b;
		private boolean background;
		
		public tt(DisplayBoard board, boolean debug) {
			b=board;
			d=debug;
			background=false;
		}
		
		private boolean action() {
			try {
				Thread.sleep((long).000001);
			}
			catch(InterruptedException e) {
				return true;
			}
			b.repaintBoard();
			try {
				Thread.sleep((long).000001);
			}
			catch(InterruptedException e) {
				return true;
			}
			if(dic%100000==0) {
				debug("THREAD_CHECK 1");
			}
			dic++;
			return false;
		}
		
		public void run() {
			b.clear();
			if(action()) {
				return;
			}
			b.drawString(0, 0, 255, 255, 255, "C:\\");
			b.drawString(8, 0, 255, 255, 255, "WinXP.exe");
			if(d) {
				System.out.println("C_WINXP_EXE 1");
			}
			if(time(1250)) {
				return;
			}
			if(startUp()) {
				return;
			}
			if(time(1550)) {
				return;
			}
			if(login()) {
				return;
			}
			if(time(1125)) {
				return;
			}
			if(desktop()) {
				return;
			}
			time(2750);
			if(startMenu()) {
				return;
			}
			if(d) {
				System.out.println("While(1) Begin");
			}
			while(true) {
				if(action()) {
					return;
				}
				int i=(int)(Math.random()*20);
				if(time((long)(Math.random()*1750))) {
					return;
				}
				try {
					switch(i) {
						case 1:
							if(time(250, new HettsyApp())) {
								return;
							}
							break;
						case 2:
							if(time(20000, new SnakeApp())) {
								return;
							}
							break;
						case 3:
							if(time(15000, new TronApp())) {
								return;
							}
							break;
					/*
						case 4:
							if(time(60000, new SortingApp())) {
								return;
							}
							break;
					 */
						case 5:
							if(time(15000, new fusionFeudApp())) {
								return;
							}
							break;
						case 6:
							if(time(7500, new WebCamPhotoApp())) {
								return;
							}
							break;
						case 7:
							if(time(30000, new GalleryApp())) {
								return;
							}
							break;
						case 8:
							if(time(15000, new DiscountPongApp())) {
								return;
							}
							break;
						case 9:
							if(time(900, new NO())) {
								return;
							}
							break;
						case 10:
							if(time(900, new SPOON())) {
								return;
							}
							break;
						case 11:
							if(startMenu()) {
								return;
							}
							else {
								if(time((long)(Math.random()*10))) {
									return;
								}
								if(Math.random()>.59) {
									if(!background) {
										if(desktop()) {
											return;
										}
									}
									else {
										if(action()) {
											return;
										}
									}
								}
							}
							break;
					case 12:
						if(startMenu()) {
							return;
						}
						else {
							if(time((long)(Math.random()*10))) {
								return;
							}
							if(Math.random()>.59) {
								if(!background) {
									if(desktop()) {
										return;
									}
								}
								else {
									if(action()) {
										return;
									}
								}
							}
						}
						break;
					/*
						case 13:
							if(time(2750, new ImageDisplayApp())) {
								return;
							}
							break;
						case 14:
							if(time(10000, new ImageDisplayApp())) {
								return;
							}
							break;
					 */
						case 15:
							if(time(3750, new Jedi())) {
								return;
							}
							break;
						case 16: 
							if(time(8000, new weatherTest())) {
								return;
							}
							break;
						case 17: 
							if(startMenu()) {
								return;
							}
							else {
								if(time((long)(Math.random()*10))) {
									return;
								}
								if(Math.random()>.59) {
									if(!background) {
										if(desktop()) {
											return;
										}
									}
									else {
										if(action()) {
											return;
										}
									}
								}
							}
							break;
						default: 
							if(!background) {
								if(desktop()) {
									return;
								}
							}
							else {
								if(action()) {
									return;
								}
							}
							break;
					}
				}
				catch(Exception e) {
					if(d) {
						e.printStackTrace();
					}
					debug("SWITCH_CASE_FAILURE -1 "+i);
				}
				debug(("LOOP_COUNT "+di+++"\nLOOP_CASE "+i));
				if(!background) {
					if(desktop()) {
						return;
					}
				}
				else {
					if(action()) {
						return;
					}
				}
				if(time(1500)) {
					return;
				}
			}
		}
		
		private boolean time(long millis) {
			try {
				long t=System.currentTimeMillis();
				debug("DELAY_TIME 0 "+millis);
				while(System.currentTimeMillis()<=(t+(millis))) {
					if(action()) {
						return true;
					}
				}
				Thread.sleep((long).000000001);
			}
			catch(InterruptedException e) {
				debug("DELAY_TIME -1 "+millis);
				e.printStackTrace();
			}
			debug("DELAY_TIME 1 "+millis);
			return false;
		}
		
		private boolean time(long millis, Application a) {
			background=false;
			if(action()) {
				return true;
			}
			b.clear();
			if(action()) {
				return true;
			}
			b.repaintBoard();
			if(open()) {
				return true;
			}
			a.start(b);
			try {
				long t=System.currentTimeMillis();
				debug("DELAY_TIME_APP 0 "+millis+" "+a.getName());
				while(System.currentTimeMillis()<=(t+(millis))) {
					if(action()) {
						a.terminate();
						return true;
					}
				}
				Thread.sleep((long).000000001);
			}
			catch(InterruptedException e) {
				debug("DELAY_TIME_APP -1 "+millis+" "+a.getName());
				e.printStackTrace();
			}
			debug("DELAY_TIME_APP 1 "+millis+" "+a.getName());
			if(reset()) {
				return true;
			}
			a.terminate();
			return action();
		}
		
		private void debug(String msg) {
			if(d) {
				System.out.println(msg);
			}
		}
		
		private boolean image(String dir) {
			debug("IMAGE 0 "+dir);
			try {
				if(action()) {
					return true;
				}
				b.drawImage(ImageIO.read(new File(dir)), 0, 0, DisplayBoard.COLS, DisplayBoard.ROWS);
				if(action()) {
					return true;
				}
			}
			catch(IOException e) {
				debug("IMAGE -1 "+dir);
				e.printStackTrace();
			}
			if(action()) {
				return true;
			}
			debug("IMAGE 1 "+dir);
			return false;
		}
		
		private boolean desktop() {
			if(action()) {
				return true;
			}
			background=true;
			image("WinXP/background.jpg");
			if(action()) {
				return true;
			}
			debug("DESKTOP_BACKGROUND 1");
			return action();
		}
		
		private boolean login() {
			if(action()) {
				return true;
			}
			image("WinXP/login.png");
			if(action()) {
				return true;
			}
			debug("LOGIN_PAGE 1");
			return false;
		}
		
		private boolean startUp() {
			if(image("WinXP/loading.png")) {
				return true;
			}
			debug("LOADING_PAGE 1");
			return false;
		}
		
		private boolean startMenu() {
			if(action()) {
				return true;
			}
			try {
				b.drawImage(ImageIO.read(new File("startMenu.gif")), DisplayBoard.ROWS/2, 0, DisplayBoard.ROWS/2, DisplayBoard.COLS/5);
				if(action()) {
					return true;
				}
			}
			catch(IOException e) {
				debug("START_MENU -1");
				e.printStackTrace();
			}
			debug("START_MENU 1");
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
			b.colorRect(DisplayBoard.ROWS/4, DisplayBoard.COLS/4, DisplayBoard.COLS-(DisplayBoard.COLS/4), DisplayBoard.ROWS-(DisplayBoard.ROWS/4), 0, 0, 0);
			time((long).25);
			debug("OPEN_APPLICATION 1");
			return action();
		}
	}
	
	public void start(DisplayBoard board) {
		b=board;
		t=new tt(b, d);
		debug("START_WINDOWS_XP 0");
		try {
			t.start();
		}
		catch(Exception e) {
			debug("START_WINDOWS_XP -1");
			e.printStackTrace();
		}
		debug("START_WINDOWS_XP 1");
		return;
	}

	public void terminate() {
		t.interrupt();
		debug("STOP_WINDOWS_XP 0");
		while(!t.interrupted()) {
			//do nothing
			//waiting for thread to be interrupted
		}
		debug("STOPPED_WINDOWS_XP 1");
		b.clear();
		b.repaintBoard();
		b.drawString(0, 0, 255,  255, 255, "A:\\Virus.exe -a");
		b.repaintBoard();
		try {
			Thread.sleep(2000);
		}
		catch(InterruptedException e) {
			debug("VIRUS -1");
			e.printStackTrace();
		}
		debug("VIRUS 1");
		b.clear();
		b.repaintBoard();
		b.colorRect(0, 0, DisplayBoard.COLS, DisplayBoard.ROWS, 0, 0, 255);
		b.drawString(0, 0, 255,  255, 255, "BSOD: Machine Check Exception");
		debug("BSOD_MCE 1");
		return;
	}
	
	private void debug(String msg) {
		if(d) {
			System.out.println(msg);
		}
		return;
	}

	public String getName() {
		return "Windows XP";
	}
}
