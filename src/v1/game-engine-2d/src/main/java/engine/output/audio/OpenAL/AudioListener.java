package main.java.engine.output.audio.OpenAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.lwjgl.openal.AL10.*;
import main.java.utilities.math.vector.MathVector4D;

public class AudioListener {

    private MathVector4D<Float> position;
    private MathVector4D<Float> velocity;


    /**
     * Default constructor for a basic AudioListener.
     */
    public AudioListener() {
        this.setPosition(new MathVector4D(0,0,0,0));
        this.setVelocity(new MathVector4D(0,0,0,0));
    }


    /**
     * Creates a new AudioListener and sets the position as specified by the
     * parameter.
     *
     * @param position The position of the listener.
     */
    public AudioListener(MathVector4D<Float> position) {
        this.setPosition(position);
        this.setVelocity(new MathVector4D(0,0,0,0));
    }


    public MathVector4D<Float> getPosition() {
        return position;
    }


    public MathVector4D<Float> getVelocity() {
        return velocity;
    }


    /**
     * Sets the position of the AudioListener.
     *
     * @param position The position of the AudioListener in pixels.
     */
    public void setPosition(MathVector4D<Float> position) {
        this.position = position;
        alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
    }


    /**
     * Sets the velocity of the listener.
     *
     * @param velocity The velocity of the listener.
     */
    public void setVelocity(MathVector4D<Float> velocity) {
        this.velocity = velocity;
        alListener3f(AL_VELOCITY, velocity.getX(), velocity.getY(), velocity.getZ());
    }


    /**
     * Sets the orientation of the AudioListener based on all parameters.
     *
     */
    public void setOrientation(MathVector4D<Float> at, MathVector4D<Float> up) {
        float[] data = new float[6];
        Float[] at_xyzt = at.toArray();
        Float[] up_xyzt = up.toArray();
        for (int i = 0; i < 3; i++) {
            data[i] = at_xyzt[i];
            data[i + 3] = up_xyzt[i];
        }
        alListenerfv(AL_ORIENTATION, data);
    }


}
