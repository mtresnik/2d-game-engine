package main.java.engine.program.game.objects.entities;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.program.game.physics.interfaces.Collidable;
import main.java.engine.program.game.physics.interfaces.Interactable;
import main.java.engine.program.game.physics.interfaces.Movable;
import main.java.engine.output.render.objects.RelativeDrawObject;
import main.java.engine.program.game.physics.objects.PhysicsObject;
import main.java.utilities.math.vector.MathVector3D;
import main.java.utilities.math.vector.MathVector4D;

public abstract class Entity<T extends RelativeDrawObject> extends PhysicsObject<T> implements Movable, Interactable, Drawable {

    protected double health;
    protected MathVector3D<Double> direction;
    protected boolean isMoving;

    protected Entity(double health) {
        this.health = health;
        this.direction = new MathVector3D<Double>(0.0, 0.0, 0.0);
    }



    @Override
    public boolean collidesWith(Collidable other) {
        return false;
    }


    @Override
    public void draw() {
        this.drawObject.draw();
    }


    @Override
    public MathVector3D<Double> getVelocity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public MathVector4D<Double> getVerticies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void interact(Interactable other) {
        return;
    }


    public boolean isAlive() {
        return false;
    }


    public Number kill() {
        return 0;
    }


    public abstract void move();


    @Override
    public final boolean isMoving() {
        return this.isMoving;
    }


    @Override
    public final void setMoving(boolean enabled) {
        this.isMoving = enabled;
    }


    public MathVector3D<Double> getDirection() {
        return direction;
    }


    public void setDirection(MathVector3D<Double> direction) {
        this.direction = direction;
    }


}
