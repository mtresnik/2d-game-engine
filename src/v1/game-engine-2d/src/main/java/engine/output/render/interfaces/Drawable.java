package main.java.engine.output.render.interfaces;

import main.java.utilities.math.vector.MathVector2D;

/**
 * A class for anything that can be drawn to the screen.
 *
 * @author Mike Resnik
 * @since 2017-06-13
 */
public interface Drawable {

    /**
     * A function that draws some object to the screen.
     */
    public void draw();


    public boolean inBounds(MathVector2D<Double> detectionPoint);


}
