package main.java.engine.events.program.control;

import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;
import main.java.engine.events.program.ProgramEvent;

public abstract class ControlEvent<T extends ControlEventData> extends ProgramEvent<T> {

    public ControlEvent(Object source, T data) {
        super(source, data);
    }


    @Override
    public Class<? extends EventFireInterface> getFireClass() {
        return ControlEventFireInterface.class;
    }


    @Override
    public Class<? extends EventListenerInterface> getListenerClass() {
        return ControlEventListenerInterface.class;
    }

}
