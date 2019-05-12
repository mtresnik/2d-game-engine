package main.java.engine.events.output.audio.music;

import main.java.engine.events.output.audio.AudioEventListenerInterface;

public class MusicEventListener<T extends MusicEvent> implements AudioEventListenerInterface<T> {

    @Override
    public Class<T> getEventType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageRecieved(T event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
