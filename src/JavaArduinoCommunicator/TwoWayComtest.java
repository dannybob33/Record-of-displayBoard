package JavaArduinoCommunicator;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class TwoWayComtest {
    public TwoWayComtest() {
        super();
    }
    
    public Serial connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier=CommPortIdentifier.getPortIdentifier(portName);
        if(portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
            return null;
        }
        CommPort commPort=portIdentifier.open(this.getClass().getName(), 500);
        SerialPort serialPort=(SerialPort)commPort;
        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        InputStream in=serialPort.getInputStream();
        OutputStream out=serialPort.getOutputStream();
        Serial thing=new Serial(in, out);
        Thread outThread=(new Thread(thing));
        outThread.start();
        return thing;
    }
    
    public static class Serial implements Runnable {
        private OutputStream out;
        private InputStream in;
        
        public Serial(InputStream i, OutputStream o) {
            out=o;
            in=i;
        }
        
        public InputStream getInputStream() {
        	return in;
        }
        
        public OutputStream getOutputStream() {
        	return out;
        }
        /*
        public void SetLed(LED l) {
        	out.write("0".getBytes());
        	out.write(l.getStrip());
        	out.write(l.getID());
        	out.write(l.getRGB());
        }*/
        
        public void showBoard() {
        	try {
				out.write("1".getBytes());
			}
			catch(IOException e) {
				e.printStackTrace();
			}
        }
        
        public void run() {
            try {
                out.write("Hellow Worldz!".getBytes());
            }
            catch(IOException e) {
                e.printStackTrace();
            }            
        }
    }
    
    public static void main(String[] args) {
    	Scanner ss=new Scanner(System.in);
        try {
        	final String port="COM3";
        	TwoWayComtest comm=new TwoWayComtest();
        	Serial s=comm.connect(port);
        	if(s==null) {
        		System.out.println("ERROR!");
        		System.exit(0);
        	}
        	if(ss.nextLine().equals("1")) {
        		s.getOutputStream().write("1".getBytes());
        	}
        	else {
        		s.getOutputStream().write("0".getBytes());
        	}
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
