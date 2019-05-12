package game.data.intuitive;

import game.data.LevelData;
import game.data.globals;
import game.objects.Background;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BackgroundData extends GameObjectData<Background> {

    public BackgroundData(Document doc, Element root) {
        super(doc, root);
    }

    public BackgroundData(Document doc, String name, RenderObjectData renderObjectData, PhysicsObjectData physicsObjectData, LogicObjectData logicObjectData) {
        super(doc, name, renderObjectData, physicsObjectData, logicObjectData);
    }

    public BackgroundData(Document doc, Background gameObject) {
        super(doc, gameObject);
    }

    @Override
    public String nodeName() {
        return globals.TAGS.BACKGROUND;
    }

    @Override
    public Background generate() {
        if (this.physicsObjectData == null) {
            System.out.println(this.name + " has a null physicsObjectData");
            return null;
        }
        return new Background(
                this.name,
                this.physicsObjectData.generate(),
                this.renderObjectData.file_location);
    }

}
