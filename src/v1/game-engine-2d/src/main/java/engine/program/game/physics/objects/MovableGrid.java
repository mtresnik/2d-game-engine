package main.java.engine.program.game.physics.objects;

import main.java.engine.program.game.physics.interfaces.Movable;
import main.java.utilities.interfaces.Grid;
import main.java.utilities.math.vector.MathVector3D;

public abstract class MovableGrid<T extends Movable> extends Grid<T> implements Movable {

    @Override
    public abstract MathVector3D<Double> getVelocity();


    @Override
    public abstract MathVector3D<Double> getDirection();


    @Override
    public abstract void move();


}
