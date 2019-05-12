package game.data;

import game.objects.Background;
import game.objects.Entity;
import game.Level;
import game.data.intuitive.BackgroundCollection;
import game.data.intuitive.EntityCollection;
import game.objects.Structure;
import game.data.intuitive.PlayerData;
import game.data.intuitive.StructureCollection;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class LevelData {


    public StructureCollection structure_collection;
    public BackgroundCollection background_collection;
    public PlayerData player_data;
    public EntityCollection entity_data;
    public Document doc;

    // SAVING THE FILE
    public static LevelData generate(Level level) {
        System.out.println("GENERATE");
        LevelData retData = new LevelData();
        // List<Structure> -----> structure_data
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document doc = dBuilder.newDocument();
        retData.doc = doc;

        // <editor-fold desc="structures">
        for (int i = 0; i < level.structureList.size(); i++) {
            Structure curr = level.structureList.get(i);
            if (curr.name == null) {
                curr.name = "structure_" + i;
            }
        }
        retData.structure_collection = new StructureCollection(level.structureList, doc);
        // </editor-fold>

        // <editor-fold desc="entities">
        for (int i = 0; i < level.entityList.size(); i++) {
            Entity curr = level.entityList.get(i);
            if (curr.name == null) {
                curr.name = "entity_" + i;
            }
        }
        retData.entity_data = new EntityCollection(level.entityList, doc);
        // </editor-fold>

        // <editor-fold desc="backrgounds">
        for (int i = 0; i < level.backgroundList.size(); i++) {
            Background curr = level.backgroundList.get(i);
            if (curr.name == null) {
                curr.name = "background_" + i;
            }
        }
        retData.background_collection = new BackgroundCollection(level.backgroundList, doc);
        // </editor-fold>

        // <editor-fold desc="player">
        if (level.player != null) {
            level.player.name = (level.player.name == null ? "NULL_NAME" : level.player.name);
            retData.player_data = new PlayerData(doc, level.player);
        }
        // </editor-fold>

        return retData;
    }

    public void save(String fileLocation) {
        System.out.println("SAVE");
        try {
            Element root = doc.createElement(globals.TAGS.ROOT);
            doc.appendChild(root);
            if (player_data != null) {
                root.appendChild(player_data.root);
            }
            if (entity_data != null) {
                root.appendChild(entity_data.root);
            }
            if (structure_collection != null) {
                root.appendChild(structure_collection.root);
            }
            if (background_collection != null) {
                root.appendChild(background_collection.root);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileLocation));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public static LevelData load(String fileLocation) {
        LevelData retData = new LevelData();
        try {
            File fXmlFile = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            retData.doc = doc;

            Element player_data = (Element) doc.getElementsByTagName(globals.TAGS.PLAYER).item(0);
            if (player_data != null) {
                retData.player_data = new PlayerData(doc, player_data);
            }

            Element entity_node_list = (Element) doc.getElementsByTagName(globals.TAGS.ENTITY_PL).item(0);
            retData.entity_data = new EntityCollection(doc, entity_node_list);
            System.out.println("entity_data loaded:" + retData.entity_data);

            Element structure_node_list = (Element) doc.getElementsByTagName(globals.TAGS.STRUCTURE_PL).item(0);
            retData.structure_collection = new StructureCollection(doc, structure_node_list);

            Element background_node_list = (Element) doc.getElementsByTagName(globals.TAGS.BACKGROUND_PL).item(0);
            retData.background_collection = new BackgroundCollection(doc, background_node_list);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retData;
    }

    @Override
    public String toString() {
        return "LevelData{" + "structure_data=" + structure_collection + ", background_data=" + background_collection + ", player_data=" + player_data + ", entity_data=" + entity_data + '}';
    }

}
