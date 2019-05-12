package game.data.intuitive;

import game.objects.Player;
import game.data.globals;
import jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PlayerData extends MovableGameObjectData<Player> {

    public PlayerData(Document doc, Element root) {
        super(doc, root);
    }

    public PlayerData(Document doc, String name, Vec2 movement_speed, RenderObjectData renderObjectData, PhysicsObjectData physicsObjectData, LogicObjectData logicObjectData) {
        super(doc, name, movement_speed, renderObjectData, physicsObjectData, logicObjectData);
    }

    public PlayerData(Document doc, Player player) {
        super(doc, player);
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
    }

    @Override
    public String nodeName() {
        return globals.TAGS.PLAYER;
    }

    @Override
    public Player generate() {
        return new Player(this.name, this.physicsObjectData.generate(), this.renderObjectData.file_location);

    }

}
