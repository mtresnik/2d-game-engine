package main.java.engine.input.mouse;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.utilities.math.vector.MathVector2D;

public interface Hoverable extends Drawable {

    public void hover();


    public boolean inBounds(MathVector2D<Double> percentCoordinates);


}
