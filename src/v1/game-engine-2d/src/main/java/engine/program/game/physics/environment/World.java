package main.java.engine.program.game.physics.environment;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.program.game.physics.handlers.PhysicsHandler;
import main.java.utilities.interfaces.Serializable;
import main.java.utilities.math.vector.MathVector2D;

public class World implements Drawable, Serializable{

    private Level currentLevel;


    @Override
    public void draw() {
        this.currentLevel.draw();
    }


    public void handlePhysics(PhysicsHandler physicsHandler) {
        this.currentLevel.handlePhysics(physicsHandler);
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
