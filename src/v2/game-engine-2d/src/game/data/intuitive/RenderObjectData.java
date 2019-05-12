package game.data.intuitive;

import game.data.globals;
import game.data.globals.TAGS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import game.render.RenderObject;

public class RenderObjectData {

    public String file_location;

    public final static String tagName = "render_object";

    public RenderObjectData(String file_location) {
        this.file_location = file_location;
    }

    public RenderObjectData(Element render_element) {
        Element file_element = (Element) render_element.getElementsByTagName(TAGS.file_location).item(0);
        String file_location_T = file_element.getTextContent();
        this.file_location = file_location_T;
    }

    public Element save(Document doc) {
        Element root = doc.createElement(tagName);

        Element file_element = doc.createElement(globals.TAGS.file_location);
        file_element.appendChild(doc.createTextNode(this.file_location));
        root.appendChild(file_element);

        return root;
    }

    public static RenderObjectData convertFromGame(RenderObject renderObject) {
        String file_location = renderObject.file_location();
        return new RenderObjectData(file_location);
    }
}
