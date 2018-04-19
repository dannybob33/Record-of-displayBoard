package JavaArduino;

// see https://sourceforge.net/projects/javaarduinolibrary/files/?source=navbar

import java.util.Scanner;

import arduino.*;

public class JATest {

	public static void main(String[] args) {

		// create connection to Arduino
		Arduino a = new Arduino ("COM7", 9600);
		a.openConnection();
		
		// sleep for 5 seconds to allow Arduino to restart
		
		System.out.println("Waiting for Arduino to reset...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// test loop-back
		
		System.out.println("Testing simple loop-back");
		System.out.println("Sending: Testing");
		a.serialWrite("Testing"+'\0');
		String r = "";
		while (r.equals("")) {
			r = a.serialRead();
		}
		System.out.println("Received: " + r);				
		
		
		Scanner s = new Scanner (System.in);
		System.out.println("Enter string to send to the Arduino - q to quit");
		String str="";
		while (str.equals("q") == false) {
			str = s.next();
			a.serialWrite(str+'\0');
			System.out.println("Sent: " + str);
			r="";
			while (r.equals("")) {
				r = a.serialRead();
			}
			System.out.println("Received: " + r);				
		}		
		a.closeConnection();
		s.close();
	}

}