package JavaArduino;

// see https://sourceforge.net/projects/javaarduinolibrary/files/?source=navbar

import java.util.Scanner;

import arduino.*;

public class JATest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Arduino a = new Arduino ("COM7", 9600);
		a.openConnection();
		
		Scanner s = new Scanner (System.in);
		System.out.println("Enter string to send to the Arduino - q to quit");
		String str="";
		while (str.equals("q") == false) {
			str = s.next();
			a.serialWrite(str+'\0');
			System.out.println("Sent: " + str);
			String r = "";
			while (r.equals("")) {
				System.out.println("trying");
				r = a.serialRead();
			}
			System.out.println("Received: " + r);				
		}		
		a.closeConnection();
		s.close();
	}

}