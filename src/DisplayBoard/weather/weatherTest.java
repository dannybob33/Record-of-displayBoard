
package DisplayBoard.weather;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import DisplayBoardEmulation.emulator.DisplayBoard;
import org.w3c.dom.*;

public class weatherTest {
	public static void main(String[] args) {
		System.out.println("Fetching weather data from Weather Underground...");
		DisplayBoard board = new DisplayBoard();
		board.show();
		try {
			URL weatherURL = new URL("http://api.wunderground.com/api/bddabec27c1b4548/conditions/q/CO/Lafayette.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(weatherURL.openStream());
			// normalize text representation
			doc.getDocumentElement().normalize();
			NodeList listOfData = doc.getElementsByTagName("current_observation");
			String output = "";
			for (int s = 0; s < listOfData.getLength(); s++) {
				Node firstDataNode = listOfData.item(s);
				if (firstDataNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstDataElement = (Element) firstDataNode;
					// -------
					NodeList fullCityList = firstDataElement.getElementsByTagName("city");
					Element fullCityElement = (Element) fullCityList.item(0);
					NodeList textFCList = fullCityElement.getChildNodes();
					output = "" + ((Node) textFCList.item(0)).getNodeValue().trim();
					board.drawString(0, -1, 144, 144, 144, output);
					board.drawString(0, 52, 144, 144, 144, ", CO");
					output = "";
					// ------
					NodeList tempList = firstDataElement.getElementsByTagName("temp_f");
					Element tempElement = (Element) tempList.item(0);
					NodeList textTempList = tempElement.getChildNodes();
					output = "" + ((Node) textTempList.item(0)).getNodeValue().trim();
					board.drawString(8, -1, 144, 144, 144, output + " F");
					output = "";
					// ------
					NodeList statList = firstDataElement.getElementsByTagName("weather");
					Element statElement = (Element) statList.item(0);
					NodeList textStatList = statElement.getChildNodes();
					output = ((Node) textStatList.item(0)).getNodeValue().trim();
					if(output.equals("Clear")) {
						board.drawCircle(25, 50, 10, 255, 255, 0, true);
					}
					board.drawString(16, -1, 144, 144, 144, output);
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
}
