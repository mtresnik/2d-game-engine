package game.data.intuitive;

import game.data.LevelData;
import game.data.globals;
import game.objects.Entity;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EntityCollection extends DataCollection<Entity, EntityData> {

    public EntityCollection(Document doc, Element root) {
        super(doc, root);
    }

    public EntityCollection(Document doc, List<EntityData> elements) {
        super(doc, elements);
    }

    public EntityCollection(List<Entity> gameObjects, Document doc) {
        super(gameObjects, doc);
    }

    @Override
    public String nodeName() {
        return globals.TAGS.ENTITY_PL;
    }

    @Override
    public String childNodeName() {
        return globals.TAGS.ENTITY;
    }

    @Override
    public EntityData generate(Node node) {
        return new EntityData(this.doc, (Element) node);
    }

    @Override
    public EntityData generate(Entity gameObject) {
        return new EntityData(this.doc, gameObject);
    }

}
