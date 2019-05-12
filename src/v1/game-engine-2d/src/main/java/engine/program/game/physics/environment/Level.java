package main.java.engine.program.game.physics.environment;

import java.util.List;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.program.game.objects.entities.Entity;
import main.java.engine.program.output.screens.Layer;
import main.java.engine.program.game.physics.handlers.PhysicsHandler;
import main.java.engine.program.game.physics.interfaces.Structure;
import main.java.utilities.interfaces.Serializable;
import main.java.utilities.math.vector.MathVector2D;

public class Level implements Drawable, Serializable {

    protected List<Entity> entities;
    protected List<Structure> structures;
    protected Layer[] layers;


    @Override
    public void draw() {
        for(Layer layer : this.layers){
            layer.draw();
        }
    }


    public void handlePhysics(PhysicsHandler physicsHandler) {
        physicsHandler.handlePhysics(entities, structures);
    }


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
