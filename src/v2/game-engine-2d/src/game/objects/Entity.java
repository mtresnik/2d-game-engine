package game.objects;

import game.globals;
import static game.objects.Player.PLAYER_SPEED;
import game.physics.Hitbox;
import jbox2d.common.Vec2;
import game.physics.PhysicsObject;
import game.render.DrawSprite;

public class Entity extends MovableGameObject {

    public Entity(String name, PhysicsObject physicsObject, Vec2 movementSpeed, String fileLocation) {
        super(name, physicsObject, movementSpeed, fileLocation);
    }
    
    private Entity(String name){
        super(name);
    }

    @Override
    public int mov_y() {
        return 0;
    }

    @Override
    public int mov_x() {
        return 0;
    }

    public static Entity generateBlank(String name){
        return new Entity(name);
    }
    
    public static Entity generateLegacy(String name, float[] location, float[] dimensions, String fileLoc){
        Entity retEntity = generateBlank(name);
        PhysicsObject physicsObject = PhysicsObject.dynamicPhysicsObject(globals.MASK_ENTITY, Hitbox.DEFAULT, location, dimensions);
        retEntity.movementSpeed = PLAYER_SPEED;
        retEntity.physicsObject = physicsObject;
        retEntity.renderObject = DrawSprite.generate2d(new double[]{0, 0}, new double[]{0, 0}, fileLoc);
        return retEntity;
    }
    
    
}
