package main.java.engine.events.program.serial;

import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;
import main.java.engine.events.program.ProgramEvent;

public abstract class SerialEvent<T extends SerialEventData> extends ProgramEvent<T>{

    public SerialEvent(Object source, T data) {
        super(source, data);
    }


    @Override
    public Class<? extends EventFireInterface> getFireClass() {
        return SerialEventFireInterface.class;
    }


    @Override
    public Class<? extends EventListenerInterface> getListenerClass() {
        return SerialEventListenerInterface.class;
    }

}
