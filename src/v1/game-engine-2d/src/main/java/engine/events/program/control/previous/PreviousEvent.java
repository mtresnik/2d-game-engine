package main.java.engine.events.program.control.previous;

import main.java.engine.events.program.control.ControlEvent;

public class PreviousEvent extends ControlEvent<PreviousEventData> {

    public PreviousEvent(Object source, PreviousEventData data) {
        super(source, data);
    }

}
