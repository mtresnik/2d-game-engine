package main.java.engine.program.game.physics.objects;

import main.java.engine.output.render.objects.RelativeDrawObject;
import main.java.engine.program.game.physics.interfaces.Collidable;
import main.java.utilities.math.vector.MathVector2D;

public abstract class PhysicsObject<T extends RelativeDrawObject> implements Collidable {

    protected T drawObject;
    protected MathVector2D<Double> position;
    protected double[] dimensions;


    public PhysicsObject() {
        this.initDrawObject();
    }


    @Override
    public void draw() {
        this.drawObject.draw();
    }


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {
        return false;
    }


    public abstract CollisionMask getCurrentCollisionMask();


    public T getDrawObject() {
        return drawObject;
    }

    public abstract void initDrawObject();

}
