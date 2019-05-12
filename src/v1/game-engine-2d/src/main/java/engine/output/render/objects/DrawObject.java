package main.java.engine.output.render.objects;

import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.program.game.physics.interfaces.Collidable;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.Render;
import main.java.engine.output.render.objects._2d.DrawTexture;
import main.java.utilities.math.vector.MathVector2D;

/**
 * An abstract 2d LWJGL Object that can be initialized and drawn.
 *
 * @author Mike Resnik
 * @since 2017-08-06
 */
public abstract class DrawObject implements Drawable, Collidable {

    public static final DrawObject screen = new DrawTexture("screen", DrawFrame.EMPTYFRAME, new MathVector2D<Double>(0.0, 0.0), new Double[]{100.0, 100.0}, new DrawObject[]{null});

    /**
     * String identifier to delineate between objects.
     */
    protected String name;
    /**
     * Where to find the texture to draw. Where the root is "res/" .
     */
    protected String fileLocation;
    /**
     * Coordinates are percentages in reference to the origin of the reference
     * texture.
     */
    protected final MathVector2D<Double> absolutePercentCoordinates;
    // TODO : Use matricies / numericalmatrix class for dimensions
    /**
     * Dimensions are percentages in reference to the dimensions of the
     * reference texture, the Display by default.
     */
    protected final Double[] absolutePercentDimensions;


    /**
     * A constructor for making a DrawObject based on screen dimension
     * percentages.
     *
     * @param name A string identifier.
     * @param fileLocation Where to find the texture.
     * @param percentCoordinates Origin of the texture in percents.
     * @param percentDimensions Dimensions of the texture in percents.
     * @see #name
     * @see #fileLocation
     * @see #absolutePercentCoordinates
     * @see #absolutePercentDimensions
     */
    public DrawObject(String name, String fileLocation, MathVector2D<Double> percentCoordinates, Double[] percentDimensions) {
        this.name = name;
        this.fileLocation = fileLocation;
        this.absolutePercentCoordinates = percentCoordinates;
        this.absolutePercentDimensions = percentDimensions;
        this.initFrames();
    }


    public DrawObject(String name, DrawFrame frame, MathVector2D<Double> percentCoordinates, Double[] percentDimensions) {
        this.name = name;
        this.fileLocation = null;
        this.absolutePercentCoordinates = percentCoordinates;
        this.absolutePercentDimensions = percentDimensions;
    }


    // TODO : Replace pixelDimensions with NumericalMatrix<Integer>.
    /**
     * A constructor for making a DrawObject based on pixels.
     *
     * @param name A string identifier.
     * @param location Where to find the texture.
     * @param pixelCoordinates Origin of the texture in pixels.
     * @param pixelDimensions Dimensions of the texture in pixels.
     * @see #name
     * @see #fileLocation
     * @see render.Render#getPercentages(int[])
     */
    public DrawObject(String name, String location, MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        this(
                name,
                location,
                Render.getPercentages(pixelCoordinates),
                Render.getPercentages(
                        new MathVector2D<Integer>(
                                pixelDimensions[0],
                                pixelDimensions[1]
                        )
                ).toArray()
        );
    }


    public DrawObject(String name, DrawFrame frame, MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        this(
                name,
                frame,
                Render.getPercentages(pixelCoordinates),
                Render.getPercentages(
                        new MathVector2D<Integer>(
                                pixelDimensions[0],
                                pixelDimensions[1]
                        )
                ).toArray()
        );
    }


    @Override
    public final void draw() {
        if (absolutePercentCoordinates.getX() >= 100
                || absolutePercentCoordinates.getX() + absolutePercentDimensions[0] <= 0
                || absolutePercentCoordinates.getY() > 100
                || absolutePercentCoordinates.getY() + absolutePercentDimensions[1] < 0) {
            // System.out.println("Not in range to draw.");
            return;
        }
        MathVector2D<Integer> position = new MathVector2D(0, 0).generate(System.currentTimeMillis(), this.getCoordinatesPixels());
        Integer[] dimensions = this.getDimensionsPixels();
        Render.drawQuad(this.getCurrentFrame(), position, dimensions);
    }


    /**
     * Returns the percentage representation of the object's coordinates.
     *
     * @return The value of absolutePercentCoordinates.
     * @see #absolutePercentCoordinates
     */
    public MathVector2D<Double> getAbsolutePercentCoordinates() {
        return this.absolutePercentCoordinates;
    }


    public void setAbsolutePercentCoordinates(MathVector2D<Double> absolutePercentCoordinates) {
        this.absolutePercentCoordinates.setX(absolutePercentCoordinates.getX());
        this.absolutePercentCoordinates.setY(absolutePercentCoordinates.getY());
        this.updateChildren();
    }


    public void setAbsolutePercentDimensions(Double[] absolutePercentDimensions) {
        this.absolutePercentDimensions[0] = absolutePercentDimensions[0];
        this.absolutePercentDimensions[1] = absolutePercentDimensions[1];
        // this.updateAbsoluteCoordinatesDimensions();
    }


    /**
     * Returns the pixel representation of the object's coordinates.
     *
     * @return The value of absolutePercentCoordinates in pixels.
     * @see Render#getPixels(double[])
     */
    public Integer[] getCoordinatesPixels() {
        return Render.getPixels(absolutePercentCoordinates).toArray();
    }


    /**
     * Returns the percentage representation of the object's dimensions.
     *
     * @return The value of absolutePercentDimensions.
     */
    public Double[] getAbsolutePercentDimensions() {
        return this.absolutePercentDimensions;
    }


    /**
     * Returns the pixel representation of the object's dimensions.
     *
     * @return The value of absolutePercentDimensions in pixels.
     * @see Render#getPixels(double[])
     */
    public Integer[] getDimensionsPixels() {
        MathVector2D<Double> dimensionsVector
                = new MathVector2D<Double>(
                        this.getAbsolutePercentDimensions()[0],
                        this.getAbsolutePercentDimensions()[1]
                );
        Integer[] pixelsArray = Render.getPixels(dimensionsVector).toArray();
        return pixelsArray;
    }


    /**
     * Returns the current frame to be drawn.
     *
     * @return The current frame of the DrawObject.
     * @see DrawFrame
     */
    public abstract DrawFrame getCurrentFrame();


    /**
     * Randomly assigns new coordinates to the screen.
     *
     * @deprecated
     */
    public void pollRandomLocation() {
        this.absolutePercentCoordinates.setX(Math.random() * (100.0 - this.absolutePercentDimensions[0]));
        this.absolutePercentCoordinates.setY(Math.random() * (100.0 - this.absolutePercentDimensions[1]));
    }


    @Override
    public abstract String toString();


    public abstract void initFrames();


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {

        // RELATIVE TO DISPLAY
        // Get Coordinates of texture in pixels
        // <x1, y1>
        MathVector2D<Integer> pixelCoordinates = Render.getPixels(absolutePercentCoordinates);
        // Get Dimensions of texture in pixels
        // <w1, h1>
        MathVector2D<Integer> pixelDimensions = Render.getPixels(new MathVector2D<Double>(absolutePercentDimensions[0], absolutePercentDimensions[1]));
        // Get coordinates of detection point in pixels
        // <x2, y2>
        MathVector2D<Integer> detectionPointPixels = Render.getPixels(detectionPoint);

        // RELATIVE TO TEXTURE (scaled)
        // <x2 - x1, y2 - y1>
        MathVector2D<Integer> detectionPointPixelsRelative = detectionPointPixels.subtract(pixelCoordinates);

        // Detect if inside bounds of display image.
        // If in x bounds
        if ((pixelCoordinates.getX() < detectionPointPixels.getX()
                && pixelCoordinates.getX() + pixelDimensions.getX() > detectionPointPixels.getX()) == false) {
            return false;
        }
        // If in y bounds;
        if ((pixelCoordinates.getY() < detectionPointPixels.getY()
                && pixelCoordinates.getY() + pixelDimensions.getY() > detectionPointPixels.getY()) == false) {
            return false;
        }

        // RELATIVE TO FILE (absolute)
        // <(x2 - x1)/w1, (y2 - y1)/h1>
        MathVector2D<Double> detectionPointPercentRelativeFile
                = new MathVector2D(
                        detectionPointPixelsRelative.getX() / ((double) pixelDimensions.getX()),
                        detectionPointPixelsRelative.getY() / ((double) pixelDimensions.getY())
                );
        // INTO FILE PIXELS
        int fileWidth = this.getCurrentFrame().getWidth();
        int fileHeight = this.getCurrentFrame().getHeight();
        MathVector2D<Integer> pixelsRelativeToFile
                = new MathVector2D<Integer>(
                        (int) (detectionPointPercentRelativeFile.getX() * fileWidth),
                        (int) (detectionPointPercentRelativeFile.getY() * fileHeight)
                );
        // Detects if the current pixel is transparent.
        //System.out.println(pixelsRelativeToFile.toString());
        if (this.getCurrentFrame().pixels[pixelsRelativeToFile.getX()][pixelsRelativeToFile.getY()][3] == 0) {
            return false;
        }
        // System.out.println(Arrays.toString(this.getCurrentFrame().pixels[pixelsRelativeToFile.getX()][pixelsRelativeToFile.getY()]));
        return true;
    }


    public abstract void updateAbsoluteCoordinatesDimensions();


    public abstract void updateChildren();


    protected abstract void addChild(RelativeDrawObject childObject);


}
