package JavaArduino;

import java.io.IOException;

import arduino.Arduino;

public class com {
	private Arduino a;
	
	public com(int port) throws Exception {
		a=new Arduino("COM"+port, 9600); // Supported baud rates are 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 31250, 38400, 57600, and 115200.
		a.openConnection();
		try {
			Thread.sleep(2500);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		send("O");
		if(!get("O1")) {
			throw new IOException();
		}
	}
	
	public boolean setPixel(int row, int col, int r, int g, int b) {
		send("P "+row+" "+col+" "+r+" "+g+" "+b);
		if(get("P1")) {
			return true;
		}
		return false;
	}
	
	public boolean show() {
		send("E");
		return get("E");
	}
	
	public boolean clear() {
		send("D");
		return get("D");
	}
	
	private boolean get(String str) {
		String s="";
		while(s.equals("")) {
			s=a.serialRead();
		}
		return s.equals(str);
	}
	
	private void send(String s) {
		a.serialWrite(s+"\0");
		return;
	}
	
	public void close() {
		a.closeConnection();
	}
}