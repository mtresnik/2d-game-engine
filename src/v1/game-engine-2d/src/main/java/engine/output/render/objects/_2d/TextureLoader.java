package main.java.engine.output.render.objects._2d;

import main.java.engine.output.render.objects._2d.DrawFrame;
import com.sun.imageio.plugins.gif.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.imageio.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import static org.lwjgl.opengl.GL11.*;

/**
 * A class responsible for loading textures in an OpenGL context.
 *
 * @author Mike Resnik
 * @since 2017-08-06
 */
public abstract class TextureLoader {

    public static final int BYTES_FOR_RGB = 3;
    public static final int BYTES_FOR_RGBA = 4;


    /**
     * TextureLoader() is private because it cannot be initialized.
     *
     * @deprecated
     */
    private TextureLoader() {

    }


    /**
     * Converts a GIF file into an array of BufferedImages based on frames of
     * the picture.
     *
     * @deprecated Because of GIFImageReaders.
     *
     * @param gifToBeConverted The GIF file to be converted.
     * @return An array of BufferedImage frames.
     * @throws IOException If there is no such file.
     */
    public static BufferedImage[] getFramesFromGif(File gifToBeConverted) throws IOException {
        ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
        ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
        ir.setInput(ImageIO.createImageInputStream(gifToBeConverted));
        int width = ir.getWidth(0);
        int height = ir.getHeight(0);
        for (int i = 0; i < ir.getNumImages(true); i++) {
            BufferedImage master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            master.getGraphics().drawImage(ir.read(i), 0, 0, null);
            frames.add(master);
        }
        BufferedImage[] retArray = new BufferedImage[frames.size()];
        int index = 0;
        for (BufferedImage bi : frames) {
            retArray[index] = bi;
            index++;
        }
        return retArray;
    }


    /**
     * Converts the ImageIO BufferedImage into a DrawFrame in an OpenGL context.
     *
     * @param bufferedImage A BufferedImage representation of pixels.
     * @return A DrawFrame that contains a unique integer and a ByteBuffer of
     * pixels.
     * @see output.render.draw_objects.DrawFrame
     * @see org.lwjgl.opengl.GL11#glGenTextures()
     * @see java.nio.ByteBuffer
     */
    public static DrawFrame loadByBufferedImage(BufferedImage bufferedImage) {

        // Create a new ByteBuffer of the image's size.
        ByteBuffer image = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * BYTES_FOR_RGBA); //4 for RGBA, 3 for RGB
        byte[][][] pixels = new byte[bufferedImage.getWidth()][bufferedImage.getHeight()][4];
        // Iterate through the BufferedImage and place every byte in the ByteBuffer.
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int pixel = bufferedImage.getRGB(x, y);
                byte red, green, blue, alpha;
                image.put(red = (byte) ((pixel >> 16) & 0xFF));     // Red component
                image.put(green = (byte) ((pixel >> 8) & 0xFF));      // Green component
                image.put(blue = (byte) (pixel & 0xFF));               // Blue component
                image.put(alpha = (byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                pixels[x][y] = new byte[]{red, green, blue, alpha};
            }
        }

        // Flip the buffer. Data is stored backwards and then needs to be printed forwards.
        image.flip();

        return loadByByteBuffer(bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, image);
    }


    private static DrawFrame loadByByteBuffer(int width, int height, byte[][][] pixels, ByteBuffer image) {
        int textureID = glGenTextures(); //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        DrawFrame returnFrame = new DrawFrame(textureID, image, pixels);
        //Return the texture ID so we can bind it later again
        return returnFrame;
    }


    public static DrawFrame loadByPixels(byte[][][] pixels) {

        int width = pixels.length;
        int height = pixels[0].length;
        ByteBuffer image = BufferUtils.createByteBuffer(width * height * BYTES_FOR_RGBA); //4 for RGBA, 3 for RGB
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int pixel_index = 0; pixel_index < BYTES_FOR_RGBA; pixel_index++) {
                    image.put(pixels[x][y][pixel_index]);
                }
            }
        }
        image.flip();

        return loadByByteBuffer(width, height, pixels, image);
    }


    public static DrawFrame loadByString(String fileLocation) {
        try {
            File png = new File(fileLocation);
            BufferedImage bi = ImageIO.read(png);
            return loadByBufferedImage(bi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DrawFrame.EMPTYFRAME;

    }


}
