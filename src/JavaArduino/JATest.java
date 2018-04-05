package JavaArduino;

// see https://sourceforge.net/projects/javaarduinolibrary/files/?source=navbar

import java.util.Scanner;

import arduino.*;

public class JATest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Arduino a = new Arduino ("COM5", 9600);
		a.openConnection();
		
		Scanner s = new Scanner (System.in);
		System.out.println("Enter string to send to the Arduino - q to quit");
		String str="";
		while (str.equals("q") == false) {
			str = s.next();
			a.serialWrite(str + "*", str.length()+1, 0);
			System.out.println("Sent: " + str);
			String r = a.serialRead();
			System.out.println("Received: " + r);				
		}		
		a.closeConnection();
		s.close();
	}

}