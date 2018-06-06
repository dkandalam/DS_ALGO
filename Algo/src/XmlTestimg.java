import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Message {

	final static String ID = "id", COMPANY = "company", CONTENT = "content";
	java.util.UUID id;
	String company;
	String content;

	public void setId(java.util.UUID id) {
		this.id = id;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void set(String propertyName, String value) {
		// System.out.println("Tag Name:"+propertyName+" ,value"+value);
		switch (propertyName) {
		case ID: {
			java.util.UUID name = java.util.UUID.fromString(value);
			setId(name);
			break;
		}
		case COMPANY: {
			setCompany(value);
			break;
		}
		case CONTENT: {
			setContent(value);
			break;
		}
		}
	}

	public String toString() {
		return "Message[ " + id.toString() + "," + company + "," + content + " ]";
	}
}

public class XmlTestimg {
	public static void main(String[] args) {
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(new FileInputStream(new File("/Users/dkandalam/test.xml")));
			Element docEle = dom.getDocumentElement();
			dom.getDocumentElement().normalize();

			System.out.println("Root element :" + dom.getDocumentElement().getNodeName());

			/* Iterate over companies */
			NodeList messageListNodes = docEle.getElementsByTagName("*");
			System.out.println(messageListNodes.getLength());
			if (messageListNodes != null) {
				int length = messageListNodes.getLength();
				Message message = new Message();
				for (int i = 0; i < length; i++) {

					Node nNode = messageListNodes.item(i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element el = (Element) nNode;
						message.set(el.getTagName(), el.getTextContent());
						// messagelist.add(message);
					}
				}
				System.out.println(message.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
