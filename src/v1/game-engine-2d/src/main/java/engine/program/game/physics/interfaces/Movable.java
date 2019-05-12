package main.java.engine.program.game.physics.interfaces;

import main.java.utilities.math.vector.MathVector3D;

public interface Movable extends Collidable {

    public void move();


    public MathVector3D<Double> getVelocity();


    public MathVector3D<Double> getDirection();


    public boolean isMoving();


    public void setMoving(boolean enabled);


}
