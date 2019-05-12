package main.java.engine.events.program.control.stop;

import main.java.engine.events.program.control.ControlEvent;

public class StopEvent extends ControlEvent<StopEventData> {

    public StopEvent(Object source, StopEventData data) {
        super(source, data);
    }


}
