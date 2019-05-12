package game.data;

import game.data.intuitive.StructureCollection;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLTest {

    public static void main(String[] args) throws Exception {
        File fXmlFile = new File("test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        Document outputDoc = dBuilder.newDocument();

        doc.getDocumentElement().normalize();

        System.out.println("Root element:" + doc.getDocumentElement().getNodeName());
//
//        NodeList player_data = doc.getElementsByTagName("player_data");
//        printNode(player_data);
        NodeList structureList = ((Element) doc.getElementsByTagName("structures").item(0)).getElementsByTagName("structure");
        StructureCollection sd = new StructureCollection(outputDoc, (Element) structureList);
        System.out.println(sd.toString());

//        StructureData.StructureElement elem = new StructureData.StructureElement(outputDoc, new float[]{100, 5}, new float[]{2, 2}, "blue.png");
//        StructureData.StructureElement temp_test = new StructureData.StructureElement(outputDoc, elem.root);
//        System.out.println(temp_test.toString());
//
//        Element structuresElement = outputDoc.createElement("structures");
//        structuresElement.appendChild(elem.root);
//        outputDoc.appendChild(structuresElement);
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//        DOMSource source = new DOMSource(outputDoc);
//        StreamResult result = new StreamResult(new File("output.xml"));
//        transformer.transform(source, result);
//        NodeList backgroundList = doc.getElementsByTagName("backgrounds");
//        printNode(backgroundList);
//        NodeList entityList = doc.getElementsByTagName("entities");
//        printNode(entityList);
    }

    private static void printNode(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value =" + tempNode.getTextContent());

                if (tempNode.hasAttributes()) {

                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();

                    for (int i = 0; i < nodeMap.getLength(); i++) {

                        Node node = nodeMap.item(i);
                        System.out.println("attr name : " + node.getNodeName());
                        System.out.println("attr value : " + node.getNodeValue());

                    }

                }

                if (tempNode.hasChildNodes()) {

                    // loop again if has child nodes
                    printNode(tempNode.getChildNodes());

                }

                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

            }
        }
    }
}
