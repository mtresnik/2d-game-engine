package game.data.intuitive;

import game.objects.MovableGameObject;
import game.data.globals.TAGS;
import jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class MovableGameObjectData<T extends MovableGameObject> extends GameObjectData<T> {

    public Vec2 movement_speed;

    public MovableGameObjectData(Document doc, Element root) {
        super(doc, root);
    }

    public MovableGameObjectData(Document doc, String name, Vec2 movement_speed, RenderObjectData renderObjectData, PhysicsObjectData physicsObjectData, LogicObjectData logicObjectData) {
        super(doc, name, renderObjectData, physicsObjectData, logicObjectData);
        this.movement_speed = movement_speed;
        this.customSave();
    }

    public MovableGameObjectData(Document doc, T movableGameObject) {
        super(doc, movableGameObject);
    }

    @Override
    public void customLoad() {
        Element movement_element = (Element) this.root.getElementsByTagName(TAGS.movement_speed).item(0);
        float[] movement_floats = GameObjectData.parseFloatValues(movement_element, TAGS.xy);
        this.movement_speed = new Vec2(movement_floats[0], movement_floats[1]);
        this.load();
    }

    public abstract void load();

    @Override
    public void customSave() {
        if (this.movement_speed == null && this.internalObject == null) {
            return;
        }
        if (this.movement_speed == null) {
            this.movement_speed = this.internalObject.movementSpeed;
        }
        Element movement_element = this.doc.createElement(TAGS.movement_speed);
        movement_element = GameObjectData.parseFloatWithTags(doc, movement_element, TAGS.xy, new float[]{this.movement_speed.x, this.movement_speed.y});
        this.root.appendChild(movement_element);
        this.save();
    }

    public abstract void save();

}
