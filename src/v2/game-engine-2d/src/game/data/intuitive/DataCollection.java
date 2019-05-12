package game.data.intuitive;

import game.objects.GameObject;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class DataCollection<K extends GameObject, T extends GameObjectData<K>> {

    public List<T> elements;
    public Document doc;
    public Element root;

    public DataCollection(Document doc, Element root) {
        this.elements = new ArrayList();
        this.doc = doc;
        this.root = root;
        this.initLoad();
    }

    public DataCollection(Document doc, List<T> elements) {
        this.doc = doc;
        this.elements = elements;
        this.initSave();
    }

    public DataCollection(List<K> gameObjects, Document doc) {
        this.doc = doc;
        this.elements = new ArrayList();
        for (K struct : gameObjects) {
            T toAdd = this.generate(struct);
            elements.add(toAdd);
        }
        this.initSave();
    }

    private void initSave() {
        this.root = doc.createElement(this.nodeName());
        for (T element : elements) {
            this.root.appendChild(element.root);
        }
    }

    public abstract String nodeName();

    public abstract String childNodeName();

    public abstract T generate(Node node);

    public abstract T generate(K gameObject);

    private void initLoad() {
        NodeList node_list = this.root.getElementsByTagName(this.childNodeName());
        for (int i = 0; i < node_list.getLength(); i++) {
            Node tempNode = node_list.item(i);
            T curr = this.generate(tempNode);
            elements.add(curr);
        }
    }

    @Override
    public String toString() {
        return "DataCollection{" + "elements=" + elements + ", doc=" + doc + ", root=" + root + '}';
    }
    
    

}
