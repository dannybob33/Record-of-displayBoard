
package DisplayBoard.weather;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import DisplayBoardEmulation.emulator.DisplayBoard;
import DisplayBoardEmulation.nativeApp.Application;
import org.w3c.dom.*;

public class weatherTest extends Application {
	private DisplayBoard board;
		public void start(DisplayBoard board) {
			this.board = board;
		try {
			URL weatherURL = new URL("http://api.wunderground.com/api/bddabec27c1b4548/conditions/q/CO/Lafayette.xml");
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(weatherURL.openStream());
			// normalize text representation
			doc.getDocumentElement().normalize();
			NodeList listOfData = doc.getElementsByTagName("current_observation");
			String output = "";
			String output1 = "";
			String output2 = "";
			for (int s = 0; s < listOfData.getLength(); s++) {
				Node firstDataNode = listOfData.item(s);
				if (firstDataNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstDataElement = (Element) firstDataNode;
					// -------
					NodeList fullCityList = firstDataElement.getElementsByTagName("city");
					Element fullCityElement = (Element) fullCityList.item(0);
					NodeList textFCList = fullCityElement.getChildNodes();
					output = "" + ((Node) textFCList.item(0)).getNodeValue().trim();
					board.drawString(0, 0, 200, 200, 200, output);
					board.drawString(0, 52, 200, 200, 200, ", CO");
					output = "";
					// ------
					NodeList tempList = firstDataElement.getElementsByTagName("temp_f");
					Element tempElement = (Element) tempList.item(0);
					NodeList textTempList = tempElement.getChildNodes();
					output = "" + ((Node) textTempList.item(0)).getNodeValue().trim();
					board.drawString(8, 0, 200, 200, 200, output + " F");
					output = "";
					// ------
					NodeList statList = firstDataElement.getElementsByTagName("weather");
					Element statElement = (Element) statList.item(0);
					NodeList textStatList = statElement.getChildNodes();
					output = ((Node) textStatList.item(0)).getNodeValue().trim();
					
					
					if(output.length() > 6) {   //checks the length of the name and enters a new line if there are two words
						int atSpace = output.indexOf(" ");
						output1 = output.substring(0,atSpace);
						output2 = output.substring(atSpace+1);
					}
					BufferedImage img = null;
					
					
					
					//   board.drawImage(img, row, col, width, height);
					
					if(output.equals("Clear")) {
						img = ImageIO.read(new File("weather/crouton.png"));
						board.drawImage(img, 15, 40, 25, 25);
					}
					else if(output.equals("Partly Cloudy")) {
						img = ImageIO.read(new File("weather/partlyCloudy.png"));
						board.drawImage(img, 15, 40, 30, 24);
					}
					else if(output.equals("Mostly Cloudy")) {
						img = ImageIO.read(new File("weather/mostlyCloudy.png"));
						board.drawImage(img, 15, 40, 25, 25);
					}
					else if(output.equals("Scattered Clouds")) {
						img = ImageIO.read(new File("weather/scatteredClouds.png"));
						board.drawImage(img, 20, 40, 30, 30);
					}
					else if(output.equals("Overcast")) {
						img = ImageIO.read(new File("weather/overcast.png"));
						board.drawImage(img, 20, 45, 25, 25);
					}
					else if(output.equals("Light Rain")) {
						img = ImageIO.read(new File("weather/lightRain.png"));
						board.drawImage(img, 13, 40, 25, 25);
					}
					else if(output.equals("Heavy Rain")) {
						img = ImageIO.read(new File("weather/heavyRain.png"));
						board.drawImage(img, 15, 40, 23, 23);
					}
					else if(output.equals("Heavy Drizzle")) {
						img = ImageIO.read(new File("weather/heavyDrizzle.png"));
						board.drawImage(img, 15, 43, 25, 25);
					}
					else if(output.equals("Light Drizzle")) {
						img = ImageIO.read(new File("weather/lightDrizzle.png"));
						board.drawImage(img, 15, 43, 25, 25);
					}
					else if(output.equals("Light Snow")) {
						img = ImageIO.read(new File("weather/lightSnow.png"));
						board.drawImage(img, 15, 43, 25, 25);
					}
					else if(output.equals("Heavy Snow")) {
						img = ImageIO.read(new File("weather/heavySnow.png"));
						board.drawImage(img, 13, 40, 30, 30);
					}
					
					
					else if(output.equals("Light Volcanic Ash") || output.equals("Heavy Volcanic Ash")) {
						img = ImageIO.read(new File("weather/thisIsFine.png"));
						board.drawImage(img, 0, 40, 44, 44);
					}
					if(output.length() > 6) {
						board.drawString(16, 0, 200, 200, 200, output1);
						board.drawString(24, 0, 200, 200, 200, output2);
					}
					else {
						board.drawString(16, 0, 200, 200, 200, output);
					}
					//board.drawString(row, col, red, green, blue, chars);
					board.repaintBoard();
					output = "";
				} // end of if clause
			} // end of for loop with s var
		}
		catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());
		}
		catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		// System.exit (0);
	}

	public void terminate() {
		return;
	}
	
	public String getName() {
		return "Weather";
	}
}
