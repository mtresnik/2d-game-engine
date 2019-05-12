package game.render;

public class DrawTexture extends DrawSprite {

    public DrawTexture(float[] positions, int[] indices, float[] uv, String textureLocation) {
        super(positions, indices, uv, textureLocation);
    }

    public static DrawTexture rect(double[] location, double[] dimensions, String fileLocation) {
        if (location.length != 2 || dimensions.length != 2) {
            throw new IllegalArgumentException("location and dimensions must be of size 2");
        }
        float x = (float) location[0], y = (float) location[1];
        float w = (float) dimensions[0], h = (float) dimensions[1];
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
        DrawTexture toRet = new DrawTexture(vertices, indices, text_coord, fileLocation);
        toRet.setPosition(x, y);
        toRet.setDimensions(w, h);
        return toRet;
    }

    @Override
    public void advance() {
        // Advancing disabled for DrawTexture
    }

}
