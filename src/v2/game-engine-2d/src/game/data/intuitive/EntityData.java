package game.data.intuitive;

import game.data.LevelData;
import game.data.globals;
import game.objects.Entity;
import game.objects.Player;
import jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EntityData extends MovableGameObjectData<Entity> {

    public EntityData(Document doc, Element root) {
        super(doc, root);
    }

    public EntityData(Document doc, String name, Vec2 movement_speed, RenderObjectData renderObjectData, PhysicsObjectData physicsObjectData, LogicObjectData logicObjectData) {
        super(doc, name, movement_speed, renderObjectData, physicsObjectData, logicObjectData);
    }

    public EntityData(Document doc, Entity movableGameObject) {
        super(doc, movableGameObject);
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
    }

    @Override
    public String nodeName() {
        return globals.TAGS.ENTITY;
    }

    @Override
    public Entity generate() {
        if (this.movement_speed == null) {
            return new Entity(this.name, this.physicsObjectData.generate(), Player.PLAYER_SPEED, this.renderObjectData.file_location);
        } else {
            return new Entity(this.name, this.physicsObjectData.generate(), this.movement_speed, this.renderObjectData.file_location);
        }
    }

}
