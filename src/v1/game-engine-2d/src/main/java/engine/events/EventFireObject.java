package main.java.engine.events;

import main.java.engine.events.EventObject;
import java.util.ArrayList;
import java.util.List;

public abstract class EventFireObject<T extends EventObject> implements EventFireInterface<T> {

    protected List<EventListenerInterface<T>> listeners;


    protected EventFireObject() {
        this.listeners = new ArrayList();
    }


    @Override
    public final void addListener(EventListenerInterface<T> listener) {
        this.listeners.add(listener);
    }


    @Override
    public void addListeners(List<EventListenerInterface<T>> listeners) {
        for (EventListenerInterface<T> L : listeners) {
            addListener(L);
        }
    }


    @Override
    public abstract void fireEvent(T event);


    @Override
    public void removeListener(EventListenerInterface<T> listener) {
        this.listeners.remove(listener);
    }


    @Override
    public void removeListeners(List<EventListenerInterface<T>> listeners) {
        for (EventListenerInterface<T> L : listeners) {
            removeListener(L);
        }
    }


}
