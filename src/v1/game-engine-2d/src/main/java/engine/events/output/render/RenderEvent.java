package main.java.engine.events.output.render;

import main.java.engine.events.output.OutputEvent;
import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;

public class RenderEvent<T extends RenderEventData> extends OutputEvent<T> {

    public RenderEvent(Object source, T data) {
        super(source, data);
    }

    @Override
    public Class<? extends EventFireInterface> getFireClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Class<? extends EventListenerInterface> getListenerClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
