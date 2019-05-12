package main.java.engine.output.audio.OpenAL;

import com.sun.media.sound.WaveFileReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.lwjgl.openal.AL10;

public class WaveData {

    public final ByteBuffer data;
    public final int format;
    public final int sampleRate;
    public final String fileName;


    /**
     * Creates a WaveData object that contains the .wav file that can be loaded.
     *
     * @param data
     * @param format
     * @param sampleRate
     */
    private WaveData(ByteBuffer data, int format, int sampleRate, String fileName) {
        this.data = data;
        this.format = format;
        this.sampleRate = sampleRate;
        this.fileName = fileName;
    }


    /**
     * Disposes the current data in WaveData buffer.
     */
    public void dispose() {
        data.clear();
    }


    /**
     * A secondary create() method that takes a String for the path.
     *
     * @param path Physical location of the audio file.
     * @return
     * @throws MalformedURLException
     */
    public static WaveData create(String path) throws MalformedURLException {
        File file = new File(path);
        file.toURI().toURL();
        URL child = file.toURI().toURL();
        return create(child);
    }


    /**
     * Create a WaveData object as defined by the specified file path.
     *
     *
     * @param path Physical location of the audio file.
     * @return WaveData object.
     */
    public static WaveData create(URL path) {
        try {
            // due to an issue with AudioSystem.getAudioInputStream
            // and mixing unsigned and signed code
            // we will use the reader directly
            WaveFileReader wfr = new WaveFileReader();
            return create(wfr.getAudioInputStream(new BufferedInputStream(path.openStream())), path.getPath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Creates a WaveData object by using an AudioInputStream.
     *
     * @param ais AudioInputStream
     * @return
     */
    public static WaveData create(AudioInputStream ais, String fileName) {
        //get format of data
        AudioFormat audioformat = ais.getFormat();
        // get channels
        int channels = 0;
        if (audioformat.getChannels() == 1) {
            if (audioformat.getSampleSizeInBits() == 8) {
                channels = AL10.AL_FORMAT_MONO8;
            } else if (audioformat.getSampleSizeInBits() == 16) {
                channels = AL10.AL_FORMAT_MONO16;
            } else {
                assert false : "Illegal sample size";
            }
        } else if (audioformat.getChannels() == 2) {
            if (audioformat.getSampleSizeInBits() == 8) {
                channels = AL10.AL_FORMAT_STEREO8;
            } else if (audioformat.getSampleSizeInBits() == 16) {
                channels = AL10.AL_FORMAT_STEREO16;
            } else {
                assert false : "Illegal sample size";
            }
        } else {
            assert false : "Only mono or stereo is supported";
        }

        //read data into buffer
        ByteBuffer buffer = null;
        try {
            int available = ais.available();
            if (available <= 0) {
                available = ais.getFormat().getChannels() * (int) ais.getFrameLength() * ais.getFormat().getSampleSizeInBits() / 8;
            }
            byte[] buf = new byte[ais.available()];
            int read = 0, total = 0;
            while ((read = ais.read(buf, total, buf.length - total)) != -1
                    && total < buf.length) {
                total += read;
            }
            buffer = convertAudioBytes(buf, audioformat.getSampleSizeInBits() == 16, audioformat.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return null;
        }

        //create our result
        WaveData wavedata = new WaveData(buffer, channels, (int) audioformat.getSampleRate(), fileName);

        //close stream
        try {
            ais.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return wavedata;
    }


    /**
     * Creates a WaveData container from the specified inputstream
     *
     * @param is InputStream to read from
     * @return WaveData containing data, or null if a failure occured
     */
    public static WaveData create(InputStream is, String fileName) {
        try {
            return create(
                    AudioSystem.getAudioInputStream(is), fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Creates a WaveData container from the specified bytes
     *
     * @param byteArray array of bytes containing the complete wave file
     * @return WaveData containing data, or null if a failure occured
     */
    public static WaveData create(byte[] byteArray, String fileName) {
        try {
            return create(
                    AudioSystem.getAudioInputStream(
                            new BufferedInputStream(new ByteArrayInputStream(byteArray))), fileName);
        } catch (Exception e) {
            System.out.println("byte[] error");
            return null;
        }
    }


    /**
     * Creates a WaveData container from the specified ByetBuffer. If the buffer
     * is backed by an array, it will be used directly, else the contents of the
     * buffer will be copied using get(byte[]).
     *
     * @param buffer ByteBuffer containing sound file
     * @return WaveData containing data, or null if a failure occured
     */
    public static WaveData create(ByteBuffer buffer, String fileName) {
        try {
            byte[] bytes = null;

            if (buffer.hasArray()) {
                bytes = buffer.array();
            } else {
                bytes = new byte[buffer.capacity()];
                buffer.get(bytes);
            }
            return create(bytes, fileName);
        } catch (Exception e) {
            System.out.println("buffer error");
            return null;
        }
    }


    /**
     * Converts the audio bytes based on the specified parameters.
     *
     * @param audio_bytes
     * @param two_bytes_data
     * @param order
     * @return
     */
    private static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data, ByteOrder order) {
        ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
        dest.order(ByteOrder.nativeOrder());
        ByteBuffer src = ByteBuffer.wrap(audio_bytes);
        src.order(order);
        if (two_bytes_data) {
            ShortBuffer dest_short = dest.asShortBuffer();
            ShortBuffer src_short = src.asShortBuffer();
            while (src_short.hasRemaining()) {
                dest_short.put(src_short.get());
            }
        } else {
            while (src.hasRemaining()) {
                dest.put(src.get());
            }
        }
        dest.rewind();
        return dest;
    }


    /**
     * Used to obtain the specified OpenAL format.
     *
     * @param channels
     * @param bitsPerSample
     * @return The bits per sample.
     */
    private static int getOpenAlFormat(int channels, int bitsPerSample) {
        if (channels == 1) {
            return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
        } else {
            return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
        }
    }


}
