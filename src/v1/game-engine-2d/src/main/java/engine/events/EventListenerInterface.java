package main.java.engine.events;

public interface EventListenerInterface<T extends EventObject> {

    public void messageRecieved(T event);


    public Class<T> getEventType();


}
