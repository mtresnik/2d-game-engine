package main.java.engine.events.program.control;

import main.java.engine.events.EventListenerInterface;

public interface ControlEventListenerInterface<T extends ControlEvent> extends EventListenerInterface<T> {

    public void messageRecieved(T controlEvent);


}
