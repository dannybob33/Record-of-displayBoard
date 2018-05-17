package JavaArduino;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

import arduino.Arduino;

public class com {
	private Arduino a;
	int rowMax; //DEclusive
	int colMax; //DEclusive
	
	public com(int port, int row, int max) { //DEclusive
		a=new Arduino("COM"+port, 250000); // Supported baud rates are 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 31250, 38400, 57600, and 115200.
		a.openConnection();
		try {
			Thread.sleep(2500);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Initialized");
	}
	
	public boolean drawString(int n, int col, int row, int r, int g, int b, char[] c) {
		send("S"+(char)row+(char)col+(char)+r+(char)g+(char)b+(char)n+new String(c));
		/*
		String s="";
		while(s.equals("")) {
			s=a.serialRead();
		}
		System.out.println("S: "+s);
		*/
		return true;
	}
	
	public boolean drawString(int row, int col, int r, int g, int b, int n, String c) {
		send("S"+(char)row+(char)col+(char)r+(char)g+(char)b+(char)n+c);
		/*
		String s="";
		while(s.equals("")) {
			s=a.serialRead();
		}
		System.out.println("S: "+s);
		*/
		return true;
	}
	
	public boolean drawLine(int row1, int col1, int row2, int col2, int r, int g, int b) {
		send("L"+(char)row1+(char)col1+(char)row2+(char)col2+(char)r+(char)g+(char)b);
		return true;
	}
	
	/*
	public boolean brightness(int i) {
		send("I "+i);
		return true;
	}
	*/
	
	//CIRC row col radius red green blue isFull
	//C 10 10 4 192 64 128 1
	//C 10 10 4 192 64 128 0
	public boolean drawCircle(int row, int col, int radius, int r, int g, int b, boolean isFull) {
		int i=isFull?1:0;
		send("C"+(char)row+(char)col+(char)radius+(char)r+(char)g+(char)b+(char)i);
		return true;
		
	}

	//R 10 20 5 10 128 255 0 1
	//R 10 20 5 10 128 255 0 0
	public boolean drawRect(int row, int col, int w, int h, int r, int g, int b, boolean isFull) {
		int i=isFull?1:0;
		send("R"+(char)row+(char)col+(char)w+(char)h+(char)r+(char)g+(char)b+(char)i);
		return true;
	}

	//P 20 16 0 128 255
	public boolean setPixel(int row, int col, int r, int g, int b) {
		send("P"+(char)row+(char)col+(char)r+(char)g+(char)b);
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
	
	public boolean drawImage (int rowP, int colP, BufferedImage image) {
		// data is a sequence of r g b values 
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    final int width = image.getWidth();
	    final int height = image.getHeight();		
	    send("X"+(char)rowP+(char)colP+(char)width+(char)height+new String(pixels));
		return true;
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