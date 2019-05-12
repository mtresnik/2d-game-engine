package game.objects;

import game.Movable;
import game.objects.GameObject;
import jbox2d.common.Vec2;
import game.physics.PhysicsObject;

public abstract class MovableGameObject extends GameObject implements Movable {

    public Vec2 movementSpeed;
    public int mov_x = 0, mov_y = 0;

    public MovableGameObject(String name, PhysicsObject physicsObject, Vec2 movementSpeed, String fileLocation) {
        super(name, physicsObject, fileLocation);
        this.movementSpeed = movementSpeed;
    }

    protected MovableGameObject(String name) {
        super(name);
    }

    @Override
    public void update() {
        super.update();
        updateMovements();
    }

    public void setInternalVelocity(Vec2 vel) {
        this.physicsObject.hitboxBody.setLinearVelocity(new Vec2(vel.x, -1 * vel.y));
    }

    public Vec2 updateSpeed() {
        return this.movementSpeed;
    }

    public Vec2 internalVelocity() {
        return this.physicsObject.hitboxBody.getLinearVelocity();
    }

}
