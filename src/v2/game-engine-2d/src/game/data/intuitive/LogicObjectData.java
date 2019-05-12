package game.data.intuitive;

import game.logic.LogicObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogicObjectData {

    public static String tagName = "logic_object";

    public LogicObjectData() {

    }

    public LogicObjectData(Element logic_element) {
        // Not implemented
    }

    public Element save(Document doc) {
        Element root = doc.createElement(tagName);

        return root;
    }

    public static LogicObjectData convertFromGame(LogicObject logicObject) {
        return new LogicObjectData();
    }

}
