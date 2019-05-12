package main.java.engine.program.game.physics.handlers;

import java.util.List;
import main.java.engine.program.game.objects.entities.Entity;
import main.java.engine.program.game.physics.interfaces.Structure;

public abstract class PhysicsHandler {

    public abstract void handlePhysics(List<Entity> firstList, List<Structure> secondList);


    protected abstract void handleMovements();


    protected abstract void handleCollisions();


}
