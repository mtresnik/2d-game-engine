package main.java.engine.output.render.objects._2d;

import java.awt.image.BufferedImage;
import java.io.*;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects.RelativeDrawObject;
import main.java.utilities.interfaces.Advanceable;
import main.java.utilities.io.output;
import main.java.utilities.math.vector.MathVector2D;

/**
 * An object responsible for rendering multiple frames that can be advanced or
 * cycled.
 *
 * @author Mike Resnik
 * @since 2017-08-06
 */
public class DrawSprite extends RelativeDrawObject implements Advanceable {

    /**
     * A Queue that is in charge of handling the current frame to draw, where
     * the head is the current frame.
     *
     * @see #advance()
     */
    protected DrawFrame[] frame_array;
    protected int array_index;


    /**
     * A constructor for making a GameSprite based on screen dimension
     * percentages.
     *
     *
     *
     * @param name A string identifier.
     * @param fileLocation Where to find the texture.
     * @param percentCoordinates Origin of the texture.
     * @param percentDimensions Dimensions of the texture.
     *
     * @see #name
     * @see #fileLocation
     * @see #absolutePercentCoordinates
     * @see #absolutePercentDimensions
     * @see DrawObject#DrawObject(java.lang.String, java.lang.String, double[],
     * double[])
     */
    public DrawSprite(String name, String fileLocation, MathVector2D<Double> percentCoordinates, Double[] percentDimensions) {
        super(name, fileLocation, percentCoordinates, percentDimensions);
    }


    /**
     *
     * A constructor for making a GameSprite based on pixels.
     *
     * @deprecated
     * @param name A string identifier.
     * @param fileLocation Where to find the texture.
     * @param pixelCoordinates Origin of the texture in pixels.
     * @param pixelDimensions Dimensions of the texture in pixels.
     * @see #name
     * @see #fileLocation
     * @see output.render.Render#getPercentages(int[])
     * @see DrawObject#DrawObject(java.lang.String, java.lang.String, int[],
     * int[])
     */
    public DrawSprite(String name, String fileLocation, MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        super(name, fileLocation, pixelCoordinates, pixelDimensions);
    }


    /**
     * Advances the current DrawFrame in the frames by one by taking from the
     * front and pushing to the back.
     *
     * @see #frames
     */
    @Override
    public void advance() {
        this.array_index = (this.array_index + 1) % this.frame_array.length;
        // Optional
        this.pollRandomLocation();
    }


    @Override
    public DrawFrame getCurrentFrame() {
        return this.frame_array[this.array_index];
    }


    @Override
    public void initFrames() {
        try {
            File gif = new File(fileLocation);
            BufferedImage[] frameArray = TextureLoader.getFramesFromGif(gif);
            this.frame_array = new DrawFrame[frameArray.length];
            for (int i = 0; i < frameArray.length; i++) {
                this.frame_array[i] = TextureLoader.loadByBufferedImage(frameArray[i]);
            }
        } catch (Exception e) {
            System.out.println("Gif could not be loaded.");
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "sprite";
    }


    public DrawFrame[] getFrames() {
        return this.frame_array;
    }


}
