/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.engine.output.audio.objects;

import main.java.engine.output.audio.OpenAL.AudioSource;
import main.java.engine.output.audio.OpenAL.AudioBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AudioObject {

    private Map<String, AudioBuffer> buffers;
    private AudioSource source;


    protected AudioObject(AudioBuffer[] buffers) {
        this.buffers = new HashMap<String, AudioBuffer>();
        for (AudioBuffer buffer : buffers) {
            this.buffers.put(buffer.getFileName(), buffer);
        }
        this.source = new AudioSource();
    }


    public AudioSource getAudioSource() {
        return this.source;
    }


    public final void play(String name){
       AudioBuffer buffer = this.buffers.get(name);
       for(Entry<String, AudioBuffer> entry : buffers.entrySet()){
           System.out.println(entry.toString());
       }
       this.source.play(buffer.getBufferID());
    }


    public final void pause(){
        this.source.pause();
    }


}
