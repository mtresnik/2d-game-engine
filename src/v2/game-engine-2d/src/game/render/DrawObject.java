package game.render;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class DrawObject extends RenderObject {

    public int program;
    public int vertShader, fragShader;
    public int vertexCount, vaoID;
    public Buffer<float[], FloatBuffer> positionBuffer;
    protected float[] positions;
    public Buffer<int[], IntBuffer> indexBuffer;
    protected int[] indices;
    public GLMATRIX4f scalingMatrix, rotationMatrix, translationMatrix;
    public FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private boolean initialized;

    protected static final String SHADER_SOURCE = "res/shaders/";
    // Maps class defined shader sources to their vertex and fragment shader codes
    protected static final HashMap<String, String[]> SHADER_MAP = new HashMap();
    // Key of a class to be mapped
    protected String key;

    protected DrawObject(float[] positions, int[] indices) {
        this.positions = positions;
        this.indices = indices;
        this.key = this.getClass().getName();
        if (SHADER_MAP.containsKey(this.key) == false) {
            SHADER_MAP.put(this.key, new String[]{loadShaderFromFile(this.getVertCodeLocation()), loadShaderFromFile(this.getFragCodeLocation())});
        }
        this.initialized = false;
    }

    public final void overrideKeyCode(String _key, String vertCode, String fragCode) {
        this.key = _key;
        SHADER_MAP.put(key, new String[]{vertCode, fragCode});
        this.initialized = false;
    }

    public final void overrideKeyLocations(String _key, String vertCodeLocation, String fragCodeLocation) {
        overrideKeyCode(_key, loadShaderFromFile(vertCodeLocation), loadShaderFromFile(fragCodeLocation));
    }

    @Override
    public final void init() {
        this.initBuffers();
        this.initProgram();
        this.initUniforms();
        this.initialized = true;
    }

    protected abstract void initBuffers();

    private final void initProgram() {
        program = glCreateProgram();
        String[] shaders = SHADER_MAP.get(this.key);
        vertShader = createShader(shaders[0], GL_VERTEX_SHADER);
        fragShader = createShader(shaders[1], GL_FRAGMENT_SHADER);
        glAttachShader(program, vertShader);
        glAttachShader(program, fragShader);
        glLinkProgram(program);
        int linked = glGetProgrami(program, GL_LINK_STATUS);
        String programLog = glGetProgramInfoLog(program);
        if (programLog.trim().length() > 0) {
            System.err.println(programLog);
        }
        if (linked == 0) {
            throw new AssertionError("Could not link program");
        }
        glUseProgram(program);
        glUseProgram(0);
    }

    protected abstract void initUniforms();

    protected static int createShader(String shaderCode, final int TYPE) {
        int shader = glCreateShader(TYPE);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        String shaderLog = glGetShaderInfoLog(shader);
        if (shaderLog.trim().length() > 0) {
            System.err.println(shaderLog);
        }
        if (compiled == 0) {
            throw new AssertionError("Could not compile shader");
        }
        return shader;
    }

    protected abstract String getShaderSource();

    protected String getVertCodeLocation() {
        return this.getShaderSource() + "vs.glsl";
    }

    protected String getFragCodeLocation() {
        return this.getShaderSource() + "fs.glsl";
    }

    protected static String loadShaderFromFile(String location) {
        String retString = "";
        try {
            File file = new File(location);
            Scanner fileScan = new Scanner(file);
            while (fileScan.hasNext()) {
                retString += fileScan.nextLine() + "\n";
            }
        } catch (FileNotFoundException fnfe) {
            throw new IllegalStateException("Shader cannot be read. Location:" + location);
        }
        return retString;
    }

    @Override
    public final void render() {
        if (this.initialized == false) {
            this.init();
        }
        preRender();
        glUseProgram(program);
        glBindVertexArray(vaoID);
        renderInsertion();
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
        glUseProgram(0);
        postRender();
    }

    protected void preRender() {
    }

    protected void renderInsertion() {

    }

    protected void postRender() {
    }

    public void setVertexPositions(float[] positions) {
        this.positions = positions;
        this.initBuffers();
    }

    @Override
    public void setPosition(float... positions) {
        this.setPosition(positions[0], positions[1]);
    }

    @Override
    public void setDimensions(float... dimensions) {
        this.setDimensions(dimensions[0], dimensions[1]);
    }

    public abstract void setPosition(float x, float y);

    public abstract void setDimensions(float w, float h);

    public void setIndices(int[] indices) {
        this.indices = indices;
        this.initBuffers();
    }

    public void translate2dRelative(double r_x_t, double r_y_t) {
        float[] newPositions = new float[positions.length];
        System.out.printf("translateByRel:\tr_x:%s\tr_y:%s\n", r_x_t, r_y_t);
        for (int vertex = 0; vertex < newPositions.length / 3; vertex += 3) {
            newPositions[vertex + 0] = (float) (this.positions[vertex + 0] + r_x_t);
            newPositions[vertex + 1] = this.positions[vertex + 1];
            newPositions[vertex + 2] = this.positions[vertex + 2];
        }
        this.setVertexPositions(newPositions);
    }

    public void translate2dPixels(int p_x_t, int p_y_t) {
        double[] translateBy = Display.pixelsToRelativeDelta(p_x_t, p_y_t);
        System.out.println("translateBy:" + Arrays.toString(translateBy));
        this.translate2dRelative(translateBy[0], translateBy[1]);
    }

    protected static class Buffer<T, K> {

        public int id;
        public T data;
        public K bufferRep;
        public int length;

        public Buffer(int id, T data, int length) {
            this.id = id;
            this.data = data;
            this.length = length;
        }

    }

    public static class GLMATRIX4f {

        final int location;
        Matrix4f mat;

        public GLMATRIX4f(int location, float[] vals) {
            this.location = location;
            this.mat = new Matrix4f();
            this.mat.set(vals);
        }
    }
}
