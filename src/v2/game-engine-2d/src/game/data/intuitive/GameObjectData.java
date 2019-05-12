package game.data.intuitive;

import game.objects.GameObject;
import game.data.globals.TAGS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class GameObjectData<T extends GameObject> {

    public final Document doc;
    public final Element root;

    public String name, replacement;
    public RenderObjectData renderObjectData;
    public PhysicsObjectData physicsObjectData;
    public LogicObjectData logicObjectData;

    protected T internalObject;

    public GameObjectData(Document doc, Element root) {
        this.doc = doc;
        this.root = root;
        this.initLoad();
    }

    public GameObjectData(Document doc, String name, RenderObjectData renderObjectData, PhysicsObjectData physicsObjectData, LogicObjectData logicObjectData) {
        this.doc = doc;
        this.root = doc.createElement(this.nodeName());
        this.name = name;
        this.renderObjectData = renderObjectData;
        this.physicsObjectData = physicsObjectData;
        this.logicObjectData = logicObjectData;
        this.initSave();
    }

    public GameObjectData(Document doc, T gameObject) {
        this.doc = doc;
        this.root = doc.createElement(this.nodeName());
        this.name = gameObject.name;
        this.renderObjectData = RenderObjectData.convertFromGame(gameObject.renderObject);
        this.physicsObjectData = PhysicsObjectData.convertFromGame(gameObject.physicsObject);
        this.logicObjectData = LogicObjectData.convertFromGame(gameObject.logicObject);
        this.internalObject = gameObject;
        this.initSave();
    }

    public void initLoad() {
        initName();
        this.initRenderObjectData();
        this.initPhysicsObjectData();
        this.initLogicObjectData();
        customLoad();
    }

    public void customLoad() {
    }

    public void initSave() {
        Element name_element = doc.createElement(TAGS.name);
        name_element.appendChild(doc.createTextNode(this.name));
        this.root.appendChild(name_element);

        Element render_root = this.renderObjectData.save(doc);
        this.root.appendChild(render_root);
        Element physics_root = this.physicsObjectData.save(doc);
        this.root.appendChild(physics_root);
        Element logic_root = this.logicObjectData.save(doc);
        this.root.appendChild(logic_root);
        customSave();
    }

    public void customSave() {
    }

    public void initName() {
        Element name_elem = (Element) root.getElementsByTagName(TAGS.name).item(0);
        if (name_elem == null) {
            this.name = null;
            return;
        }
        this.name = name_elem.getTextContent();
    }

    public void initRenderObjectData() {
        Element render_element = (Element) root.getElementsByTagName(RenderObjectData.tagName).item(0);
        if (render_element == null) {
            this.renderObjectData = null;
            return;
        }
        this.renderObjectData = new RenderObjectData(render_element);
    }

    public void initPhysicsObjectData() {
        Element physics_element = (Element) root.getElementsByTagName(PhysicsObjectData.tagName).item(0);
        if (physics_element == null) {
            this.physicsObjectData = null;
            return;
        }
        this.physicsObjectData = new PhysicsObjectData(physics_element);
    }

    public void initLogicObjectData() {
        Element logic_element = (Element) root.getElementsByTagName(LogicObjectData.tagName).item(0);
        if (logic_element == null) {
            this.logicObjectData = null;
            return;
        }
        this.logicObjectData = new LogicObjectData(logic_element);
    }

    public abstract String nodeName();

    public static float[] parseFloatValues(Element root, String... tags) {
        float[] retArray = new float[tags.length];
        for (int i = 0; i < tags.length; i++) {
            String temp_element = root.getElementsByTagName(tags[i]).item(0).getTextContent();
            retArray[i] = Float.parseFloat(temp_element);
        }
        return retArray;
    }

    public static Element parseFloatWithTags(Document doc, Element root, String[] tags, float[] values) {
        for (int i = 0; i < tags.length; i++) {
            Element temp_element = doc.createElement(tags[i]);
            temp_element.appendChild(doc.createTextNode("" + values[i]));
            root.appendChild(temp_element);
        }
        return root;
    }

    public static double[] parseDoubleValues(Element root, String... tags) {
        double[] retArray = new double[tags.length];
        for (int i = 0; i < tags.length; i++) {
            String temp_element = root.getElementsByTagName(tags[i]).item(0).getTextContent();
            retArray[i] = Double.parseDouble(temp_element);
        }
        return retArray;
    }

    public static Element parseDoubleWithTags(Document doc, Element root, String[] tags, double[] values) {
        for (int i = 0; i < tags.length; i++) {
            Element temp_element = doc.createElement(tags[i]);
            temp_element.appendChild(doc.createTextNode("" + values[i]));
            root.appendChild(temp_element);
        }
        return root;
    }

    public abstract T generate();

}
