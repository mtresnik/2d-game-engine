package main.java.engine.events.program;

import main.java.engine.events.EventData;
import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;
import main.java.engine.events.EventObject;

public abstract class ProgramEvent<T extends EventData> extends EventObject<T> {

    protected Class<? extends EventListenerInterface> listenerClass;
    protected Class<? extends EventFireInterface> fireClass;


    public ProgramEvent(Object source, T data) {
        super(source, data);
    }


    public abstract Class<? extends EventListenerInterface> getListenerClass();


    public abstract Class<? extends EventFireInterface> getFireClass();


}
