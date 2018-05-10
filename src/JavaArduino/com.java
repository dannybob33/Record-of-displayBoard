package JavaArduino;

import java.io.IOException;

import arduino.Arduino;

public class com {
	private Arduino a;
	int rowMax; //DEclusive
	int colMax; //DEclusive
	
	public com(int port, int row, int max) { //DEclusive
		a=new Arduino("COM"+port, 115200); // Supported baud rates are 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 31250, 38400, 57600, and 115200.
		a.openConnection();
		try {
			Thread.sleep(2500);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Initialized");
	}
	
	//CIRC row col radius red green blue isFull
	//C [0-] [0-] [0-] [0-255] [0-255] [0-255] [0-1]
	/*
	public boolean drawCircle(int row, int col, int radius, int r, int g, int b, boolean isFull) {
		
	}*/

	//R 10 20 5 10 128 255 0 0
	public boolean drawRect(int row, int col, int w, int h, int r, int g, int b, boolean isFull) {
		/*
		if(rgb(r)) {
			return false;
		}
		else if(rgb(g)) {
			return false;
		}
		else if(rgb(b)) {
			return false;
		}
		else if((row>rowMax)||(row<0)) {
			return false;
		}
		else if((col>colMax)||(col<0)) {
			return false;
		}
		else if((colMax-col)<w) {
			return false;
		}
		else if((rowMax-row)<h) {
			return false;
		}
		*/
		int i=isFull?1:0;
		send("R "+row+" "+col+" "+w+" "+h+" "+r+" "+g+" "+b+" "+i);
		return true;
	}

	//P 20 16 0 128 255
	public boolean setPixel(int row, int col, int r, int g, int b) {
		/*
		if(r>=256) {
			return false;
		}
		else if(r>=256) {
			return false;
		}
		else if(r>=256) {
			return false;
		}
		else if((row>rowMax)||(row<0)) {
			return false;
		}
		else if((col>colMax)||(col<0)) {
			return false;
		}
		*/
		send("P "+row+" "+col+" "+r+" "+g+" "+b);
		return true;
	}
	
	public void show() {
		send("E");
		return;
	}
	
	public void clear() {
		send("D");
		return;
	}
	
	private void send(String s) {
		a.serialWrite(s);
		return;
	}
	
	public boolean rgb(int i) {
		if((i>=256)||(i<0)) {
			return false;
		}
		return true;
	}
	
	public void close() {
		a.closeConnection();
		return;
	}
}