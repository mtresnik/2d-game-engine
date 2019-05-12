package main.java.engine.program.game.physics.objects;

import main.java.engine.output.render.objects._2d.DrawTexture;
import main.java.engine.program.game.physics.handlers.CollisionHandler;

public class PhysicsTexture extends PhysicsObject<DrawTexture> {

    private CollisionMask collision_mask;


    public PhysicsTexture(DrawTexture drawTexture) {
        this.drawObject = drawTexture;
        this.collision_mask = CollisionHandler.generateCollisionMask(drawTexture.getCurrentFrame());
    }


    @Override
    public CollisionMask getCurrentCollisionMask() {
        return this.collision_mask;
    }


    @Override
    public void initDrawObject() {
        
    }


}
