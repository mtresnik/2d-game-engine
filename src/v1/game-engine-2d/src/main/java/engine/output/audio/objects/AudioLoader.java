package main.java.engine.output.audio.objects;

import main.java.engine.output.audio.OpenAL.AudioSource;
import main.java.engine.output.audio.OpenAL.AudioBuffer;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import static org.lwjgl.openal.ALC10.*;
import org.lwjgl.openal.ALCCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioLoader {

    private long device;
    private long context;
    private final List<AudioBuffer> buffers;
    private final List<AudioSource> sources;


    /**
     * Creates a new AudioLoader device that can play audio files.
     *
     * @param path Physical location of audio files on disk/project.
     */
    public AudioLoader() {
        this.buffers = new ArrayList();
        this.sources = new ArrayList();
        this.init();
    }


    public void cleanup() {
        for (AudioBuffer buffer : buffers) {
            buffer.cleanup();
        }
        for (AudioSource source : sources) {
            source.cleanup();
        }
        alcCloseDevice(this.device);
    }


    public List<AudioSource> getPlayingSources() {
        return null;
    }


    public void addBuffer(AudioBuffer buffer) {
        this.buffers.add(buffer);
    }


    private AudioBuffer[] loadAudio(String[] filePaths) throws FileNotFoundException {
        AudioBuffer[] buffers = new AudioBuffer[filePaths.length];
        for (int index = 0; index < filePaths.length; index++) {
            try {
                buffers[index] = new AudioBuffer(filePaths[index]);
            } catch (MalformedURLException murle) {
                throw new FileNotFoundException("Cannot find specified file :" + filePaths[index]);
            }
            this.addBuffer(buffers[index]);
        }
        return buffers;
    }


    public SoundFXObject loadFX(String... filePaths) throws FileNotFoundException {
        AudioBuffer[] buffers = loadAudio(filePaths);
        return new SoundFXObject(buffers);
    }


    public MusicObject loadMusic(String... filePaths) throws FileNotFoundException {
        AudioBuffer[] buffers = loadAudio(filePaths);
        return new MusicObject(buffers);
    }


    /**
     * Initializes the AudioLoader by calling the OpenAL C libraries.
     *
     * @throws IllegalStateException
     */
    public void init() throws IllegalStateException {
        this.device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        this.context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

/*
    //TODO: Remove when testing is no longer needed.
    public static void main(String[] args) {
        String path1 = "res/test1.wav";
        String path2 = "res/test2.wav";
        AudioLoader al = new AudioLoader();
        try {
            SoundFXObject sfo = al.loadFX(path1, path2);
            sfo.play("test1");
            sfo.play("test2");
            while (sfo.getAudioSource().isPlaying()) {
                // Proper test
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
        al.cleanup();

    }
*/

}
