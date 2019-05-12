package main.java.engine.output.render.objects._2d;

import java.nio.ByteBuffer;
import main.java.utilities.math.vector.MathVector2D;

public class TextureFrame extends DrawFrame {

    protected int x_offset, y_offset;
    protected int x_advance;


    public TextureFrame(int x_offset, int y_offset, int x_advance, int textureID, ByteBuffer image, byte[][][] pixels) {
        super(textureID, image, pixels);
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        this.x_advance = x_advance;
    }


    public TextureFrame(int x_offset, int y_offset, int x_advance, DrawFrame frame) {
        this(x_offset, y_offset, x_advance, frame.textureID, frame.image, frame.pixels);
    }


    public TextureFrame(int x_offset, int y_offset, int x_advance, int x_pos, int y_pos, int width, int height, DrawFrame parent) {
        this(x_offset, y_offset, x_advance, parent.subFrame(new MathVector2D<Integer>(x_pos, y_pos), width, height));
    }


    public int getX_offset() {
        return x_offset;
    }


    public int getY_offset() {
        return y_offset;
    }


    public int getX_advance() {
        return x_advance;
    }


}
