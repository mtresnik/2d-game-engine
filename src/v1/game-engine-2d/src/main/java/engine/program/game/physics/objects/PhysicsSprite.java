package main.java.engine.program.game.physics.objects;

import main.java.engine.output.render.objects._2d.DrawSprite;
import main.java.engine.program.game.physics.handlers.CollisionHandler;

public class PhysicsSprite extends PhysicsObject<DrawSprite> {

    private CollisionMask[] collision_masks;

    public PhysicsSprite(DrawSprite drawSprite) {
        this.drawObject = drawSprite;
        this.collision_masks = CollisionHandler.generateCollisionMaskArray(this.drawObject.getFrames());
    }


    @Override
    public CollisionMask getCurrentCollisionMask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void initDrawObject() {
        
    }


}
