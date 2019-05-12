package main.java.engine.events.output;

import main.java.engine.events.EventListenerInterface;

public interface OutputEventListenerInterface<T extends OutputEvent> extends EventListenerInterface<T> {

    public void messageRecieved(T event);


}
