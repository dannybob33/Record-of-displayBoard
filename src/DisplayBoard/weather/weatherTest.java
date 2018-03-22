
package DisplayBoard.weather;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

public class weatherTest {
	public static void main(String[] args) {
		System.out.println("Fetching weather data from Weather Underground...");
		try {
			URL weatherURL = new URL("http://api.wunderground.com/api/bddabec27c1b4548/conditions/q/CO/Lafayette.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(weatherURL.openStream());
			// normalize text representation
			doc.getDocumentElement().normalize();
			NodeList listOfData = doc.getElementsByTagName("current_observation");
			
			for (int s = 0; s < listOfData.getLength(); s++) {
				Node firstDataNode = listOfData.item(s);
				if (firstDataNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstDataElement = (Element) firstDataNode;
					// -------
					NodeList fullCityList = firstDataElement.getElementsByTagName("full");
					Element fullCityElement = (Element) fullCityList.item(1);
					NodeList textFCList = fullCityElement.getChildNodes();
					System.out.println("Full city name : " + ((Node) textFCList.item(0)).getNodeValue().trim());
					// -------
					NodeList countryList = firstDataElement.getElementsByTagName("country");
					Element countryElement = (Element) countryList.item(0);
					NodeList textCountryList = countryElement.getChildNodes();
					System.out.println("Country : " + ((Node) textCountryList.item(0)).getNodeValue().trim());
					// ----
					NodeList zipList = firstDataElement.getElementsByTagName("zip");
					Element zipElement = (Element) zipList.item(0);
					NodeList textZipList = zipElement.getChildNodes();
					System.out.println("Zip : " + ((Node) textZipList.item(0)).getNodeValue().trim());
					// ------
					NodeList tempList = firstDataElement.getElementsByTagName("temperature_string");
					Element tempElement = (Element) tempList.item(0);
					NodeList textTempList = tempElement.getChildNodes();
					System.out.println("Current Temperature : " + ((Node) textTempList.item(0)).getNodeValue().trim());
					// ------
					NodeList obvList = firstDataElement.getElementsByTagName("observation_time");
					Element obvElement = (Element) obvList.item(0);
					NodeList textObvList = obvElement.getChildNodes();
					System.out.println(((Node) textObvList.item(0)).getNodeValue().trim());
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
