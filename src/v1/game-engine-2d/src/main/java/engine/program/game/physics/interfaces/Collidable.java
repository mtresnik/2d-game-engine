package main.java.engine.program.game.physics.interfaces;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.utilities.math.vector.MathVector4D;

public interface Collidable extends Drawable {

    public default boolean collidesWith(Collidable other) {
        return false;
    }


    public default MathVector4D<Double> getVerticies() {
        return new MathVector4D<Double>(0.0, 0.0, 0.0, 0.0);
    }



}
