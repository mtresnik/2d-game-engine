package main.java.engine.output.render.objects._2d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects.RelativeDrawObject;
import main.java.utilities.math.vector.MathVector2D;

/**
 * An object responsible for only rendering one, non-changing DrawFrame.
 *
 * @author Mike Resnik
 * @see engine.output.render.objects.DrawObject
 * @see output.render.draw_objects.DrawFrame
 * @since 2017-08-06
 */
public class DrawTexture extends RelativeDrawObject {

    /**
     * A structure that contains a unique textureID and a ByteBuffer,
     *
     * @see output.render.draw_objects.DrawFrame
     */
    protected DrawFrame frame;


    /**
     * A constructor for making a GameTexture based on screen
 dimension percentages.
     *
     *
     *
     * @param name A string identifier.
     * @param fileLocation Where to find the texture.
     * @param percCoordinates Origin of the texture.
     * @param percDimensions Dimensions of the texture.
     *
     * @see #name
     * @see #fileLocation
     * @see #absolutePercentCoordinates
     * @see #absolutePercentDimensions
     * @see DrawObject#DrawObject(java.lang.String, java.lang.String, double[],
     * double[])
     */
    public DrawTexture(String name, String fileLocation,
            MathVector2D<Double> percentCoordinates, Double[] percentDimensions, DrawObject... relativeArray) {
        super(name, fileLocation, percentCoordinates, percentDimensions, relativeArray);
    }


    public DrawTexture(String name, DrawFrame frame,
            MathVector2D<Double> percentCoordinates, Double[] percentDimensions, DrawObject... relativeArray) {
        super(name, frame, percentCoordinates, percentDimensions, relativeArray);
        this.frame = frame;
    }


    /**
     *
     * A constructor for making a GameTexture based on pixels.
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
    public DrawTexture(String name, String fileLocation, MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        super(name, fileLocation, pixelCoordinates, pixelDimensions);
    }


    /**
     *
     * @param name
     * @param fileLocation
     * @param pixelCoordinates
     * @param pixelDimensions
     * @deprecated
     */
    public DrawTexture(String name, DrawFrame frame, MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        super(name, frame, pixelCoordinates, pixelDimensions);
        this.frame = frame;
    }


    @Override
    public DrawFrame getCurrentFrame() {
        return this.frame;
    }


    @Override
    public void initFrames() {
        try {
            File png = new File(fileLocation);
            if(png.exists() == false){
                System.out.println("File Doesn't Exist:" + fileLocation );
            }
            BufferedImage bi = ImageIO.read(png);
            this.frame = TextureLoader.loadByBufferedImage(bi);
        } catch (IOException e) {
            System.out.println("Can't read file at location:" + fileLocation);
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return this.name;
    }


}
