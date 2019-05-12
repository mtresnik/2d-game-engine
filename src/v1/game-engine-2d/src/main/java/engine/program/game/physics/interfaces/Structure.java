package main.java.engine.program.game.physics.interfaces;

import main.java.engine.program.game.physics.objects.CollisionMask;
import main.java.engine.program.game.physics.objects.PhysicsObject;

public abstract class Structure extends PhysicsObject {

    public Structure() { 
    }

    @Override
    public CollisionMask getCurrentCollisionMask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}