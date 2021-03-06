package Development;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import BackEnd_Misc.CommandExecutor;

public class networkXML {
	private String filePath;
	private CommandExecutor linuxCommandExecutor = new CommandExecutor();
	private Document doc;
	public String readResult = "";

	public networkXML(String pathToFile) {
		filePath = pathToFile;
	}

	public networkXML() {

	}

	public void WriteGeneralValue(String name, String attribute, String value) {
		/*
		 * Useful to modify values like network name and other meta-data. This applies
		 * to part of the xml file where there is no heirachy, just a single tag The
		 * Writing of the more specific part of the Vm should be defined somewhere else
		 * -----------------------------------------------------------------------------
		 * ----- name: name of the tag ----- attribute: attribute name defined within a
		 * tag, ----- value: the specified value for the attribute (a.k.a attribute
		 * value xD ) leave an empty String for empty attribute name. value: value to be
		 * overwritten
		 */
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(true);
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList targetedPart = root.getElementsByTagName(name);

			Node addr = targetedPart.item(0);

			System.out.println("----------------------------");

			if (!attribute.isEmpty()) {

				NamedNodeMap adr = addr.getAttributes();
				Node attr = adr.getNamedItem(attribute);

				attr.setTextContent(value);
			} else {
				addr.setTextContent(value);
			}

			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addMetaData(String elementName, String attributename, String attributeValue) {
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(true);
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList metaDataEle = root.getElementsByTagName("metadata");
			Node metaDataNode = metaDataEle.item(0);

			Element attr = doc.createElement(elementName);
			attr.setAttribute(attributename, attributeValue);

			metaDataNode.appendChild(attr);
			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNatMode(String IpStart, String IpStop, String PortStart, String PortEnd)
	{
		try {
		File inputFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(true);
		dbFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(inputFile);
		// root element------------------------------------------
		Element root = doc.getDocumentElement();
		NodeList forwardList=root.getElementsByTagName("forward");
		for(int i=0; i < forwardList.getLength();++i)
		{
			root.removeChild(forwardList.item(i));
		}
		Element forward = doc.createElement("forward");
		forward.setAttribute("mode", "nat");
		
		
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
