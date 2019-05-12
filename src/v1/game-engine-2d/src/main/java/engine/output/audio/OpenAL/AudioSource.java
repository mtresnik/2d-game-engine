package main.java.engine.output.audio.OpenAL;

import static org.lwjgl.openal.AL10.*;
import main.java.utilities.math.vector.MathVector4D;

public class AudioSource {

    private final int sourceID;
    private MathVector4D<Float> position;
    private MathVector4D<Float> velocity;


    /**
     * Default constructor for a non-looping, relative AudioSource.
     */
    public AudioSource() {
        this(false, true);
    }


    /**
     * Creates an AudioSource that can be associated to a game object.
     *
     * @param loop If true then the AudioSource will loop the sound.
     * @param relative Sets the AudioSource to be location specific.
     */
    protected AudioSource(boolean loop, boolean relative) {
        this.sourceID = alGenSources();
        if (loop == true) {
            alSourcei(sourceID, AL_LOOPING, AL_TRUE);
        }
        if (relative == true) {
            alSourcei(sourceID, AL_SOURCE_RELATIVE, AL_TRUE);
        }
        this.setPosition(new MathVector4D<Float>(0f, 0f, 0f, 0f));
        this.setVelocity(new MathVector4D<Float>(0f, 0f, 0f, 0f));
    }


    public MathVector4D<Float> getPosition() {
        return position;
    }


    public MathVector4D<Float> getVelocity() {
        return velocity;
    }


    /**
     * Set the position of the AudioSource.
     *
     * @param position
     */
    public void setPosition(MathVector4D<Float> position) {
        this.position = position;
        alSource3f(sourceID, AL_POSITION, position.getX(), position.getY(), position.getZ());
    }


    /**
     * Sets the velocity of the AudioSource by passing in a vector.
     *
     * @param velocity
     */
    public void setVelocity(MathVector4D<Float> velocity) {
        this.velocity = velocity;
        alSource3f(sourceID, AL_VELOCITY, velocity.getX(), velocity.getY(), velocity.getZ());
    }


    /**
     * Sets the AudioSource gain to the passed in value.
     *
     * @param gain
     */
    public void setGain(float gain) {
        alSourcef(sourceID, AL_GAIN, gain);
    }


    /**
     * Set the float value of the source given the enumerated parameter.
     *
     * @param parameter
     * @param value
     * @see org.lwjgl.openal.AL10
     */
    public void setProperty(int parameter, float value) {
        alSourcef(sourceID, parameter, value);
    }


    /**
     * Plays the current AudioSource by the given bufferID.
     *
     * @param bufferID
     */
    public void play(int bufferID) {
        alSourcei(sourceID, AL_BUFFER, bufferID);
        alSourcePlay(sourceID);
    }


    /**
     * Pauses the current AudioSource. Can be played later.
     *
     * @see audio.AudioSource.play()
     */
    public void pause() {
        alSourcePause(sourceID);
    }


    /**
     * Stops playing the current AudioSource.
     */
    public void stop() {
        alSourceStop(sourceID);
    }


    /**
     * This method checks to see if an AudioSource object is playing and audio
     * file.
     *
     * @return a boolean to see if an AudioSource is currently playing.
     */
    public boolean isPlaying() {
        return alGetSourcei(sourceID, AL_SOURCE_STATE) == AL_PLAYING;
    }


    /**
     * Cleans up the audio files and deletes sources attached to the sourceID.
     */
    public void cleanup() {
        stop();
        alDeleteSources(sourceID);
    }


}
