
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
		try {
			URL weatherURL = new URL("http://api.wunderground.com/api/bddabec27c1b4548/conditions/q/CO/Lafayette.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(weatherURL.openStream());
			System.out.println("Test");
			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
			NodeList listOfPersons = doc.getElementsByTagName("current_observation");
			int totalPersons = listOfPersons.getLength();
			
			for (int s = 0; s < listOfPersons.getLength(); s++) {
				Node firstPersonNode = listOfPersons.item(s);
				if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstPersonElement = (Element) firstPersonNode;
					// -------
					NodeList firstNameList = firstPersonElement.getElementsByTagName("full");
					Element firstNameElement = (Element) firstNameList.item(1);
					NodeList textFNList = firstNameElement.getChildNodes();
					System.out.println("Full city name : " + ((Node) textFNList.item(0)).getNodeValue().trim());
					// -------
					NodeList lastNameList = firstPersonElement.getElementsByTagName("country");
					Element lastNameElement = (Element) lastNameList.item(0);
					NodeList textLNList = lastNameElement.getChildNodes();
					System.out.println("Country : " + ((Node) textLNList.item(0)).getNodeValue().trim());
					// ----
					NodeList zipList = firstPersonElement.getElementsByTagName("zip");
					Element zipElement = (Element) zipList.item(0);
					NodeList textZipList = zipElement.getChildNodes();
					System.out.println("Zip : " + ((Node) textZipList.item(0)).getNodeValue().trim());
					// ------
					NodeList tempList = firstPersonElement.getElementsByTagName("temperature_string");
					Element tempElement = (Element) tempList.item(0);
					NodeList textTempList = tempElement.getChildNodes();
					System.out.println("Current Temperature : " + ((Node) textTempList.item(0)).getNodeValue().trim());
					// ------
					NodeList obvList = firstPersonElement.getElementsByTagName("observation_time");
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
