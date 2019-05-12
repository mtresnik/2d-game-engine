package main.java.engine.output.render.objects.font;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.Render;
import main.java.engine.output.render.objects._2d.DrawTexture;
import main.java.utilities.math.vector.MathVector2D;

public class DrawCharacter implements Drawable {

    public final CharacterFrame frame;
    protected Double percentToPixelRatio;
    protected final DrawTexture texture;


    public DrawCharacter(CharacterFrame frame, MathVector2D<Double> percentCoordinates, Double percentToPixelRatio) {
        this.frame = frame;
        this.percentToPixelRatio = percentToPixelRatio;
        Double width = frame.getWidth() * this.percentToPixelRatio;
        Double height = frame.getHeight() * this.percentToPixelRatio;
        this.texture = new DrawTexture(frame.representation + "DrawCharacter", frame, percentCoordinates, new Double[]{width, height});
    }

    // TODO : put in Render class.
    public static MathVector2D<Double> addPixelOffsets(MathVector2D<Double> percentCoordinates, MathVector2D<Integer> pixelOffsets){
        // Convert to pixels
       MathVector2D<Integer> pixelCoordinates = Render.getPixels(percentCoordinates);
        // Add offsets
        pixelCoordinates = pixelCoordinates.add(pixelOffsets);
        // Convert to percents
        return Render.getPercentages(pixelCoordinates);
    }

    @Override
    public void draw() {
        this.texture.draw();
    }

    @Override
    public boolean inBounds(MathVector2D<Double> percentCoordinates) {
        return this.texture.inBounds(percentCoordinates);
    }


}
