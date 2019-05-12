package main.java.engine.events.program.control.pause;

import main.java.engine.events.program.control.ControlEvent;

public class PauseEvent extends ControlEvent<PauseEventData> {

    public PauseEvent(Object source, PauseEventData data) {
        super(source, data);
    }

}
