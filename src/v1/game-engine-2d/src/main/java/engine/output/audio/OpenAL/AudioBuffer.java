package main.java.engine.output.audio.OpenAL;

import java.io.File;
import java.net.MalformedURLException;
import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;

public class AudioBuffer {

    private final int bufferID;
    private String fileName;


    /**
     * Creates an AudioBuffer object by the given file path on the physical
     * disk/project.
     *
     * @param path Source file path for an AudioFile to be loaded.
     * @throws MalformedURLException
     */
    public AudioBuffer(String path) throws MalformedURLException {
        // Set the bufferId
        this.bufferID = alGenBuffers();
        WaveData waveFile = WaveData.create(path);
        AL10.alBufferData(bufferID, waveFile.format, waveFile.data, waveFile.sampleRate);
        this.fileName = new File(waveFile.fileName).getName();
        this.fileName = this.fileName.substring(0, this.fileName.indexOf("."));
        waveFile.dispose();
        // Load the buffer data using WaveFile as a temporary file
    }


    /**
     * A getter for the AudioBuffer buffer ID.
     *
     * @return The bufferID tied to the AudioBuffer.
     */
    public int getBufferID() {
        return bufferID;
    }


    public String getFileName() {
        return fileName;
    }


    /**
     * Deletes the current AudioBuffer.
     */
    public void cleanup() {
        alDeleteBuffers(this.bufferID);
    }


}
