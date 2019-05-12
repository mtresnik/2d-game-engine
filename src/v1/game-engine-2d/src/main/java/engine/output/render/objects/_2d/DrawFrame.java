package main.java.engine.output.render.objects._2d;

import java.nio.ByteBuffer;
import main.java.utilities.io.output.structures.rgba;
import main.java.utilities.math.vector.MathVector2D;

public class DrawFrame {

    /**
     * A unique ID that is bound to a LWJGL context.
     */
    public final int textureID;

    /**
     * Pixels of the frame stored in a 4 channel format.
     * <p>
     * <font color = "red"> Channel 1 : Red </font>
     * <p>
     * <font color = "green"> Channel 2 : Green </font>
     * <p>
     * <font color = "blue"> Channel 3 : Blue </font>
     * <p>
     * <font color = "gray"> Channel 4 : Alpha (Luminescence / Transparency)
     * </font>
     * <p>
     * Eight bits = one byte. Four bytes = one signed integer.
     */
    public final ByteBuffer image;

    /**
     * Pixels of the frame stored in a 3d array.
     * @deprecated
     */
    public final byte[/*x_index*/][/*y_index*/][/*rgba_index*/] pixels;

    public static final DrawFrame EMPTYFRAME = TextureLoader.loadByPixels(new byte[][][]{{{(byte) 0, (byte) 0, (byte) 0, (byte) 0}}});


    /**
     * The default constructor for a structure that combines an identifying
     * integer with a buffer.
     *
     *
     * @param textureID A unique LWJGL ID.
     * @param image Pixels of the frame or size of the ByteBuffer.
     * @see org.lwjgl.opengl.GL11#glGenTextures()
     * @see #image
     * @see DrawFrame
     */
    public DrawFrame(int textureID, ByteBuffer image, byte[][][] pixels) {
        this.textureID = textureID;
        this.image = image;
        this.pixels = pixels;
    }


    public int getWidth() {
        return pixels.length;
    }


    public int getHeight() {
        return pixels[0].length;
    }

    public boolean pixelEquals(int[] xy, rgba value){
        if(xy == null || xy.length < 2 || value == null){
            throw new IllegalArgumentException();
        }
        return value.equals(this.pixels[xy[0]][xy[1]]);
    }

    public DrawFrame subFrame(MathVector2D<Integer> coordinate, int width, int height) {
        if (coordinate.getX() < 0 || coordinate.getY() < 0 || (width <= 0 && height <= 0)) {
            return DrawFrame.EMPTYFRAME;
        }
        byte[][][] newTexturePixels = new byte[width][height][TextureLoader.BYTES_FOR_RGBA];
        for (int y_index = 0; y_index < height; y_index++) {
            for (int x_index = 0; x_index < width; x_index++) {
                for (int pixel_index = 0; pixel_index < newTexturePixels[x_index][y_index].length; pixel_index++) {
                    newTexturePixels[x_index][y_index][pixel_index]
                            = this.pixels[coordinate.getX() + x_index][coordinate.getY() + y_index][pixel_index];
                }
            }
        }
        return TextureLoader.loadByPixels(newTexturePixels);
    }

    public DrawFrame flip(boolean horizontal, boolean vertical) {
        byte[][][] new_pixels = new byte[this.pixels.length][this.pixels[0].length][this.pixels[0][0].length];

        // <editor-fold desc="Horizontal Flip Logic">
        for (int x_index = 0; x_index < this.pixels.length; x_index++) {
            int x_location;
            if (horizontal == true) {
                x_location = this.pixels.length - x_index - 1;
            } else {
                x_location = x_index;
            }
            // </editor-fold>

        // <editor-fold desc="Vertical Flip Logic">
            for (int y_index = 0; y_index < this.pixels[x_index].length; y_index++) {
                int y_location;
                if (vertical == true) {
                    y_location = this.pixels[x_index].length - y_index - 1;
                } else {
                    y_location = y_index;
                }
                // </editor-fold>

         //<editor-fold desc="Pixel Flip Logic (Future Proofing)">
                for (int c_index = 0; c_index < this.pixels[x_index][y_index].length; c_index++) {
                    int c_location = c_index;
                    byte tempByte = this.pixels[x_location][y_location][c_location];
                    new_pixels[x_index][y_index][c_index] = tempByte;
                }
            }
        }
        //</editor-fold>

        return TextureLoader.loadByPixels(new_pixels);
    }


}
