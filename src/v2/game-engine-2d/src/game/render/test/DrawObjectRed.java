package game.render.test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import game.render.DrawObject;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class DrawObjectRed extends DrawObject {

    public DrawObjectRed(float[] positions, int[] indices) {
        super(positions, indices);
    }

    @Override
    protected void initBuffers() {
        vertexCount = indices.length;
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // <editor-fold desc="Vertices">
        final int POSITION_LOCATION = 0;
        glEnableVertexAttribArray(POSITION_LOCATION);
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
        verticesBuffer.put(positions).flip();

        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        final int FLOAT_SIZE = 4;
        glVertexAttribPointer(POSITION_LOCATION, 3, GL_FLOAT, false, 3 * FLOAT_SIZE, 0);
        this.positionBuffer = new Buffer(vboID, positions, positions.length);
        this.positionBuffer.bufferRep = verticesBuffer;
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
    protected String getShaderSource() {
        return DrawObject.SHADER_SOURCE + "red/";
    }

    @Override
    protected void initUniforms() {

    }

}
