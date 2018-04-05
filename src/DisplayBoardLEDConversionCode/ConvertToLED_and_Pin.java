package DisplayBoardLEDConversionCode;

import java.util.Scanner;

public class ConvertToLED_and_Pin {
	static int xmax = 73;
	static int pinOne = 6; //Static Number to indicate which pin it is
	public static int toLEDNum(int x, int y){ //The original LED num method. This is still vital.
	    int output = 0;
	    if(y%2 == 0) {
	    	output = (y*xmax) + (y+x);
	    }
	    else {
	    	output = (y*xmax) + ((xmax - x) + y);
	    }
	    return output;
	}
	
	
	public static int getPin(int x, int y) {
		int pin = pinOne;
		
		//every 16 rows of 74, start over again
	    if(toLEDNum(x, y) >= 2368) { //determines pin number starting from third pin used
	    	pin += 2;
	    }
		else if(toLEDNum(x, y) >= 1184) { //determines pin number starting from second pin used
	    	pin += 1;
	    }
		return pin;
	}
	public static int getLED(int x, int y) {//New LED method getting LED num based on pin used.
		int led = toLEDNum(x, y);
		 //every 16 rows of 74, start over again
		if(toLEDNum(x, y) >= 2368) { //determines LED number starting from third pin used
	    	led = toLEDNum(x, y) - 2368;
	    }
	    else if(toLEDNum(x, y) >= 1184) { //determines LED number starting from second pin used
	    	led = toLEDNum(x, y) - 1184;
	    }
	    
		return led;
	}
	/*
	
	FINISH WORKING ON THIS THING
	
	*/
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		
		while(true) {
			int x = reader.nextInt();
			int y = reader.nextInt();
			
			if(x < 0 || y < 0) {
				System.out.println("|!|!|!| -- Operation Aborted -- |!|!|!|");
				break;
			}
			
			System.out.println("The first method: " + toLEDNum(x,y));
			System.out.println("Get LED accounting for bridging pins: " + getLED(x,y));
			System.out.println("Get pin for given coordinate: " + getPin(x,y));
			
		}
		
		
	}

}
