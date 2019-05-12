package main.java.engine.output.audio;

import main.java.engine.output.audio.objects.AudioLoader;
import main.java.engine.events.output.audio.AudioEvent;
import main.java.engine.events.output.audio.AudioEventListenerInterface;
import main.java.engine.output.Output;
import main.java.utilities.interfaces.Serializable;

public class Audio implements AudioEventListenerInterface<AudioEvent>, Serializable {

    private AudioLoader loader;
    private Output output;


    public Audio(Output output) {
        this.output = output;
    }


    @Override
    public Class<AudioEvent> getEventType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void load() {

    }


    @Override
    public void messageRecieved(AudioEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void save() {

    }


}
