package main.java.engine.events.output.audio;

import main.java.engine.events.EventListenerInterface;

public interface AudioEventListenerInterface<T extends AudioEvent> extends EventListenerInterface<T> {

    @Override
    public void messageRecieved(T event);


}
