package main.java.engine.events;

import java.util.List;

public interface EventFireInterface<EVENT_TYPE extends EventObject> {

    public void fireEvent(EVENT_TYPE event);


    public void addListener(EventListenerInterface<EVENT_TYPE> listener);


    public void addListeners(List<EventListenerInterface<EVENT_TYPE>> listeners);


    public void removeListener(EventListenerInterface<EVENT_TYPE> listener);


    public void removeListeners(List<EventListenerInterface<EVENT_TYPE>> listeners);


}
