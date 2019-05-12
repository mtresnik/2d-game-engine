package game.render;

import org.lwjgl.opengl.GL13;
import util.Updatable;
import util.Advanceable;
import util.Initializable;

public abstract class RenderObject implements Renderable, Advanceable, Updatable, Initializable {

    public static int numTextures = 0;

    public abstract void setPosition(float... pos);

    public abstract void setDimensions(float... pos);

    public long delta() {
        return 0;
    }

    public static int textureUnit() {
        int ret = GL13.GL_TEXTURE0 + numTextures;
        numTextures++;
        return ret;
    }

    public static RenderObject generate2d(double[] location_relative, double[] dimensions_relative, String fileLocation) {
        RenderObject retObject;
        if (location_relative.length != 2 || dimensions_relative.length != 2) {
            throw new IllegalArgumentException("location and dimensions must be of size 2");
        }
        float x = (float) location_relative[0], y = (float) location_relative[1];
        float w = (float) dimensions_relative[0], h = (float) dimensions_relative[1];
        float[] vertices = new float[]{
            -w, h, 0.0f,
            -w, -h, 0.0f,
            w, -h, 0.0f,
            w, h, 0.0f
        };
        int[] indices = new int[]{0, 1, 2, 3, 0, 2};
        float[] text_coord = new float[]{
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
        };
        retObject = new DrawSprite(vertices, indices, text_coord, fileLocation);
        retObject.setPosition(x, y);
        retObject.setDimensions(w, h);
        return retObject;
    }

    public abstract String file_location();

}
