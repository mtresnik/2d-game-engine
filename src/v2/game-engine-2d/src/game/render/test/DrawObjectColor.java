package game.render.test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import game.render.DrawObject;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class DrawObjectColor extends DrawObject {

    public float[] colors;
    public Buffer<float[], FloatBuffer> colorBuffer;

    public DrawObjectColor(float[] positions, int[] indices, float[] colors) {
        super(positions, indices);
        this.colors = colors;
    }

    @Override
    protected void initBuffers() {
        vertexCount = indices.length;
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // TODO : change to get location in shader of keyword
        final int POSITION_LOCATION = 0, COLOR_LOCATION = 1;
        glEnableVertexAttribArray(POSITION_LOCATION);
        //Vertices
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
        verticesBuffer.put(positions).flip();

        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        final int FLOAT_SIZE = 4;
        glVertexAttribPointer(POSITION_LOCATION, 3, GL_FLOAT, false, 3 * FLOAT_SIZE, 0);
        this.positionBuffer = new Buffer(vboID, positions, positions.length);
        this.positionBuffer.bufferRep = verticesBuffer;

        //Colours
        glEnableVertexAttribArray(COLOR_LOCATION);
        FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorsBuffer.put(colors).flip();

        int colVboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colVboID);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(COLOR_LOCATION, 3, GL_FLOAT, false, 3 * 4, 0);
        this.colorBuffer = new Buffer(colVboID, colors, colors.length);
        this.colorBuffer.bufferRep = colorsBuffer;

        //Indices
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        int idxVboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        this.indexBuffer = new Buffer(idxVboID, indices, indices.length);
        this.indexBuffer.bufferRep = indicesBuffer;

        //Unbind
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    @Override
    protected String getShaderSource() {
        return DrawObject.SHADER_SOURCE + "colors/";
    }

    @Override
    protected void initUniforms() {

    }

}
