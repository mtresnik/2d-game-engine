package main.java.engine.events.input;

import main.java.engine.events.EventListenerInterface;

public interface InputEventListenerInterface<T extends InputEvent> extends EventListenerInterface<T> {

    public void messageRecieved(T event);


}
