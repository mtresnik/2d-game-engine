package game.render;

import util.Temporal;
import util.Updatable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import util.Advanceable;
import util.files;
import util.images;

public class DrawSprite extends DrawObject implements Advanceable, Updatable {

    public String fileLocation;
    public int currentTexture;
    // DrawFrames of the Sprite.
    public int[] texID;
    public int[] texUnits;
    public float[] texture_coords;
    public Buffer<float[], FloatBuffer> textureBuffer;
    public Float width, height;
    public Temporal temporal;
    public static final long ANIMATION_LENGTH = 100; // 500ms

    public DrawSprite(float[] positions, int[] indices, float[] uv, String textureLocation) {
        super(positions, indices);
        this.fileLocation = textureLocation;
        this.texture_coords = uv;
        temporal = new Temporal(this.delta());
        this.init();
    }

    public long delta() {
        return ANIMATION_LENGTH;
    }

    @Override
    protected void initBuffers() {
        vertexCount = indices.length;
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        final int POSITION_LOCATION = 0;
        final int TEXTURE_COORD_LOCATION = 1;
        final int FLOAT_SIZE = 4;

        // <editor-fold desc="Vertices">
        glEnableVertexAttribArray(POSITION_LOCATION);
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
        verticesBuffer.put(positions).flip();

        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(POSITION_LOCATION, 3, GL_FLOAT, false, 3 * FLOAT_SIZE, 0);
        this.positionBuffer = new Buffer(vboID, positions, positions.length);
        this.positionBuffer.bufferRep = verticesBuffer;
        // </editor-fold>

        // <editor-fold desc="Texture Coordinates">
        texID = this.loadTextures(fileLocation);
        System.out.println("Frames:" + Arrays.toString(texID));
        System.out.println("Texture Units:" + Arrays.toString(texUnits));

        glEnableVertexAttribArray(TEXTURE_COORD_LOCATION);
        FloatBuffer textBuffer = BufferUtils.createFloatBuffer(texture_coords.length);
        textBuffer.put(texture_coords).flip();

        int texboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texboID);
        glBufferData(GL_ARRAY_BUFFER, textBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(TEXTURE_COORD_LOCATION, 2, GL_FLOAT, false, 2 * FLOAT_SIZE, 0);
        this.textureBuffer = new Buffer(texboID, texture_coords, texture_coords.length);
        this.textureBuffer.bufferRep = textBuffer;
        // </editor-fold>

        // <editor-fold desc="Indices">
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        int idxVboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        this.indexBuffer = new Buffer(idxVboID, indices, indices.length);
        this.indexBuffer.bufferRep = indicesBuffer;
        // </editor-fold>

        //Unbind
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    @Override
    protected void initUniforms() {
        Matrix4f generator = new Matrix4f();

        int scalingMatrixLocation = GL20.glGetUniformLocation(this.program, "scalingMatrix");
        this.scalingMatrix = new GLMATRIX4f(scalingMatrixLocation, generator.identity().get(new float[16]));

        int rotationMatrixLocation = GL20.glGetUniformLocation(this.program, "rotationMatrix");
        this.rotationMatrix = new GLMATRIX4f(rotationMatrixLocation, generator.identity().get(new float[16]));

        int translationMatrixLocation = GL20.glGetUniformLocation(this.program, "translationMatrix");
        this.translationMatrix = new GLMATRIX4f(translationMatrixLocation, generator.identity().get(new float[16]));
        updateUniforms();

    }

    protected void updateUniforms() {
        glUseProgram(program);
        GL20.glUniformMatrix4fv(this.scalingMatrix.location, false, scalingMatrix.mat.get(matrixBuffer));
        GL20.glUniformMatrix4fv(this.rotationMatrix.location, false, rotationMatrix.mat.get(matrixBuffer));
        GL20.glUniformMatrix4fv(this.translationMatrix.location, false, translationMatrix.mat.get(matrixBuffer));
        glUseProgram(0);
    }

    public void scale(float x, float y) {
        this.scalingMatrix.mat.scale(x, y, 1.0f);
        this.updateUniforms();
    }

    public void rotate2d(float theta) {
        this.rotationMatrix.mat.rotateLocalZ(theta);
        this.updateUniforms();
    }

    public void setAngle(float theta) {
        this.rotationMatrix.mat.identity();
        this.rotate2d(theta);
    }

    public void translate(float x, float y) {
        this.translationMatrix.mat.translate(x, y, 0);
        this.updateUniforms();
    }

    @Override
    public void setPosition(float x, float y) {
        this.translationMatrix.mat.identity();
        this.translate(x, y);
    }

    public void setDimensions(float w, float h) {
        if (this.width != null || this.height != null) {
            throw new RuntimeException("Dimensions cannot be edited at runtime. See DrawObjectTexture#scale(x::float, y::float)");
        }
        this.width = w;
        this.height = h;
    }

    @Override
    protected void preRender() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    protected void renderInsertion() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, texID[currentTexture]);
    }

    @Override
    protected void postRender() {
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        glDisable(GL_BLEND);
    }

    @Override
    protected String getShaderSource() {
        return DrawObject.SHADER_SOURCE + "textures/";
    }

    private ByteBuffer imageToByteBuffer(String fileName) {
        ByteBuffer buf = null;
        try {
            byte[][][] image = images.loadImageBytes(fileName);
            buf = BufferUtils.createByteBuffer(image.length * image[0].length * image[0][0].length);
            for (int ROW = 0; ROW < image.length; ROW++) {
                for (int COL = 0; COL < image[0].length; COL++) {
                    buf.put(image[ROW][COL]);
                }
            }
            buf.flip();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return buf;
    }

    private int loadImage(BufferedImage image, int textureUnit) {
        ByteBuffer buffer = null;
        int tWidth = 0;
        int tHeight = 0;
        buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        tWidth = image.getWidth();
        tHeight = image.getHeight();
        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                int pixel = pixels[h * image.getWidth() + w];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();
        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, tWidth, tHeight, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_NEAREST);

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_LINEAR_MIPMAP_LINEAR);
        return texId;
    }

    private int[] loadTextures(String pathName) {
        String extension = files.getFileExtension(new File(pathName));
        switch (extension) {
            case "bmp":
            case "png":
            case "jpg":
            case "jpeg":
                return new int[]{loadPNGTexture(pathName)};
            case "gif":
                int[] retArray = loadGifSprite(pathName);
                System.out.println("gif loaded:" + Arrays.toString(retArray));
                return retArray;
            default:
                System.out.printf("PATHNAME:%s \t EXTENSION:%s", pathName, extension);
                throw new IllegalArgumentException("Invalid path, must be of type BMP, PNG, JPG, or GIF");
        }
    }

    private int[] loadGifSprite(String pathName) {
        BufferedImage[] frames = {};
        try {
            frames = images.loadGifBuffered(pathName);
        } catch (IOException ex) {
            Logger.getLogger(DrawSprite.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] retArray = new int[frames.length];
        this.texUnits = new int[frames.length];
        for (int i = 0; i < frames.length; i++) {
            this.texUnits[i] = RenderObject.textureUnit();
            retArray[i] = loadImage(frames[i], this.texUnits[i]);
        }
        return retArray;
    }

    private int loadPNGTexture(String fileName) {
        BufferedImage image = null;
        try {
            image = images.loadImageBuffered(fileName);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        int textureUnit = RenderObject.textureUnit();
        this.texUnits = new int[]{textureUnit};
        return loadImage(image, textureUnit);
    }

    @Override
    public void advance() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentTexture = (currentTexture + 1) % texID.length;
            }

        };
        temporal.runIf(runnable);

    }

    @Override
    public void update() {

    }

    @Override
    public String file_location() {
        return this.fileLocation;
    }

}
