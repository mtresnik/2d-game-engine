package main.java.engine.events.output.audio;

import main.java.engine.events.program.ProgramEvent;
import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;

public class AudioEvent<T extends AudioEventData> extends ProgramEvent<T> {

    public AudioEvent(Object source, T data) {
        super(source, data);
    }


    @Override
    public Class<? extends EventFireInterface> getFireClass() {
        return AudioEventFireObject.class;
    }


    @Override
    public Class<? extends EventListenerInterface> getListenerClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
