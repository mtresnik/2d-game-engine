package main.java.engine.events.output.audio.sound;

import main.java.engine.events.output.audio.AudioEventListenerInterface;

public class SoundEventListener<T extends SoundEvent> implements AudioEventListenerInterface<T> {

    @Override
    public Class<T> getEventType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageRecieved(T event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
