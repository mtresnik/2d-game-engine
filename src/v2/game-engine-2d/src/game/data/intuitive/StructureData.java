package game.data.intuitive;

import game.objects.Structure;
import game.data.LevelData;
import game.data.globals;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StructureData extends GameObjectData<Structure> {

    public StructureData(Document doc, Element root) {
        super(doc, root);
    }

    public StructureData(Document doc, String name, RenderObjectData renderObjectData, PhysicsObjectData physicsObjectData, LogicObjectData logicObjectData) {
        super(doc, name, renderObjectData, physicsObjectData, logicObjectData);
    }

    public StructureData(Document doc, Structure gameObject) {
        super(doc, gameObject);
    }

    @Override
    public String nodeName() {
        return globals.TAGS.STRUCTURE;
    }

    @Override
    public Structure generate() {
        if (this.physicsObjectData == null) {
            System.out.println(this.name + " has a null physicsObjectData");
            return null;
        }
        return new Structure(
                this.name,
                this.physicsObjectData.generate(),
                this.renderObjectData.file_location);
    }

}
